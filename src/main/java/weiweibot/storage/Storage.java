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

/**
 * File-backed storage for tasks using a simple line-based format.
 *
 * <p>The path is constructed from the provided segments (relative and
 * OS-independent). Each task is stored on one line and reconstructed on load.</p>
 */
public class Storage {
    private static final DateTimeFormatter FILE_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private final Path file;

    public Storage(String... pathSegments) {
        assert pathSegments != null : "pathSegments must not be null";
        assert pathSegments.length > 0 : "pathSegments must be non-empty";
        this.file = Paths.get("", pathSegments); // relative + OS-independent
    }

    /**
     * Loads tasks from the backing file if it exists.
     *
     * <p>Blank lines are ignored. If a line cannot be parsed, a {@link WeiExceptions}
     * is thrown indicating the corrupted line number.</p>
     *
     * @return a {@link TaskList} containing the parsed tasks (empty if file absent)
     * @throws WeiExceptions on I/O errors or malformed records
     */
    public TaskList load() {
        assert file != null : "backing file path must not be null";
        List<Task> storageList = new ArrayList<>();
        if (!Files.exists(file)) {
            return new TaskList(storageList);
        }
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                try {
                    storageList.add(parseLine(trimmed));
                } catch (RuntimeException ex) {
                    throw new WeiExceptions("Corrupted line " + lineNum + ": " + trimmed);
                }
            }
        } catch (IOException e) {
            throw new WeiExceptions("Failed to load tasks: " + e.getMessage());
        }
        return new TaskList(storageList);
    }

    /**
     * Saves all tasks to the backing file, creating parent directories if needed.
     *
     * @param tasks the tasks to persist
     * @throws WeiExceptions on I/O errors
     */
    public void save(TaskList tasks) {
        assert tasks != null : "TaskList must not be null";
        assert tasks.getNumberOfTasks() == tasks.asUnmodifiableList().size()
            : "TaskList count and underlying list size mismatch";
        try {
            Files.createDirectories(file.getParent() != null ? file.getParent() : Paths.get("."));
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file)) {
                for (Task t : tasks.asUnmodifiableList()) {
                    bufferedWriter.write(serialize(t));
                    bufferedWriter.newLine();
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
        assert "T".equals(type) || "D".equals(type) || "E".equals(type)
            : "Unknown record type: " + type;
        boolean marked = switch (tokens.get(1)) {
            case "1" -> true;
            case "x" -> true;
            case "X" -> true;
            case "true" -> true;
            case "True" -> true;

            case "0" -> false;
            case "" -> false;
            case "false" -> false;
            case "False" -> false;
            case " " -> false;

            default -> throw new WeiExceptions("Invalid mark flag: " + tokens.get(1));
        };
        String desc = tokens.get(2);

        Task currentTask;
        switch (type) {
        case "T":
            currentTask = new Todo(desc);
            break;

        case "D":
            if (tokens.size() < 4) {
                throw new WeiExceptions("Missing deadline datetime");
            }
            LocalDateTime by = LocalDateTime.parse(tokens.get(3), FILE_DT);
            currentTask = new Deadline(desc, by);
            break;

        case "E":
            if (tokens.size() < 5) {
                throw new WeiExceptions("Missing event datetime range");
            }
            LocalDateTime from = LocalDateTime.parse(tokens.get(3), FILE_DT);
            LocalDateTime to = LocalDateTime.parse(tokens.get(4), FILE_DT);
            currentTask = new Event(desc, from, to);
            break;

        default:
            throw new WeiExceptions("Unknown record type: " + type);
        }
        if (marked) {
            currentTask.mark();
        } else {
            currentTask.unmark();
        }
        return currentTask;
    }
}
