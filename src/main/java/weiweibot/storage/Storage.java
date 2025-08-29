package weiweibot.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import weiweibot.exceptions.WeiExceptions;
import weiweibot.tasks.Deadline;
import weiweibot.tasks.Event;
import weiweibot.tasks.Task;
import weiweibot.tasks.TaskList;
import weiweibot.tasks.Todo;


public class Storage {
    private static final DateTimeFormatter FILE_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private final Path file;

    public Storage(String... pathSegments) {
        this.file = Paths.get("", pathSegments); // relative + OS-independent
    }

    public TaskList load() {
        List<Task> list = new ArrayList<>();
        if (!Files.exists(file)) {
            return new TaskList(list);
        }
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                try {
                    list.add(parseLine(trimmed));
                } catch (RuntimeException ex) {
                    throw new WeiExceptions("Corrupted line " + lineNo + ": " + trimmed);
                }
            }
        } catch (IOException e) {
            throw new WeiExceptions("Failed to load tasks: " + e.getMessage());
        }
        return new TaskList(list);
    }

    public void save(TaskList tasks) {
        try {
            Files.createDirectories(file.getParent() != null ? file.getParent() : Paths.get("."));
            try (BufferedWriter bw = Files.newBufferedWriter(file)) {
                for (Task t : tasks.asUnmodifiableList()) {
                    bw.write(serialize(t));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new WeiExceptions("Failed to save tasks: " + e.getMessage());
        }
    }

    private String serialize(Task t) {
        String type;
        String marked = t.isMarked() ? "1" : "0";
        if (t instanceof Todo todo) {
            type = "T";
            return String.join(" | ", type, marked, todo.getDescription());
        } else if (t instanceof Deadline d) {
            type = "D";
            String by = d.getBy().format(FILE_DT);
            return String.join(" | ", type, marked, d.getDescription(), by);
        } else if (t instanceof Event e) {
            type = "E";
            String from = e.getFrom().format(FILE_DT);
            String to = e.getTo().format(FILE_DT);
            return String.join(" | ", type, marked, e.getDescription(), from, to);
        } else {
            throw new WeiExceptions("Unknown task type for serialization: " + t.getClass());
        }
    }

    private Task parseLine(String line) {
        String[] parts = line.split("\\|");
        List<String> tokens = new ArrayList<>();
        for (String p : parts) {
            tokens.add(p.trim());
        }
        if (tokens.size() < 3) {
            throw new WeiExceptions("Invalid record: " + line);
        }

        String type = tokens.get(0);
        boolean marked = switch (tokens.get(1)) {
        case "1", "x", "X", "true", "True" -> true;
        case "0", "", "false", "False", " " -> false;
        default -> throw new WeiExceptions("Invalid mark flag: " + tokens.get(1));
        };
        String desc = tokens.get(2);

        Task t;
        switch (type) {
        case "T":
            t = new Todo(desc);
            break;
        case "D":
            if (tokens.size() < 4) {
                throw new WeiExceptions("Missing deadline datetime");
            }
            LocalDateTime by = LocalDateTime.parse(tokens.get(3), FILE_DT);
            t = new Deadline(desc, by);
            break;
        case "E":
            if (tokens.size() < 5) {
                throw new WeiExceptions("Missing event datetime range");
            }
            LocalDateTime from = LocalDateTime.parse(tokens.get(3), FILE_DT);
            LocalDateTime to = LocalDateTime.parse(tokens.get(4), FILE_DT);
            t = new Event(desc, from, to);
            break;
        default:
            throw new WeiExceptions("Unknown record type: " + type);
        }
        if (marked) {
            t.mark();
        } else {
            t.unmark();
        }
        return t;
    }
}
