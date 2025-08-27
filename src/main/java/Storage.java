import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Storage for tasks. Uses a simple, portable, relative path (e.g. new Storage("data", "duke.txt")).
 * Creates the folder/file if missing. Loads on startup and saves the whole list after changes.
 *
 * File format (pipes with spaces for readability; spaces are optional):
 *   T | 1 | read book
 *   D | 0 | return book | June 6th
 *   E | 0 | project meeting | Mon 2pm to 4pm
 *
 */
public class Storage {
    
    private final Path file;
    private static final DateTimeFormatter FILE_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Storage(String... pathSegments) {
        this.file = Paths.get("", pathSegments); // relative + OS-independent
    }

    //  Load tasks from disk. Creates directories/file if they don't exist yet.
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        try {
            ensureFileExists();

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
                        Task t = parseLine(trimmed);
                        tasks.add(t);
                    } catch (Exception ex) {
                        System.err.println("Skipping corrupted line " + lineNo + ": " + trimmed);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load tasks: " + e.getMessage());
        }
        return tasks;
    }

    // Save all tasks to disk (overwrite). Call this after any change to the list.
    public void save(List<Task> tasks) {
        try {
            ensureFileExists();
            try (BufferedWriter bw = Files.newBufferedWriter(file)) {
                for (Task t : tasks) {
                    bw.write(serialize(t));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to save tasks: " + e.getMessage());
        }
    }

    private void ensureFileExists() throws IOException {
        Path parent = file.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
    }

    // Task -> "T | 1 | read book" etc.
    private String serialize(Task t) {
        String mark = t.isMarked() ? "1" : "0";
        if (t instanceof Todo) {
            return join("T", mark, t.getDescription());
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", mark, d.getDescription(), d.getBy().format(FILE_DT));
        } else if (t instanceof Event) {
            Event e = (Event) t;
            String from = e.getFrom() == null ? "" : e.getFrom().format(FILE_DT);
            String to   = e.getTo()   == null ? "" : e.getTo().format(FILE_DT);
            return join("E", mark, e.getDescription(), from, to);
        } else {
            // Fallback if new types are added later
            return join("U", mark, t.getDescription());
        }
    }

    // Parse "T | 1 | read book" / "D | 0 | desc | by" / "E | 0 | desc | from to to" into a Task.
    private Task parseLine(String line) {
        // Split by '|' without regex; trims tokens.
        List<String> parts = splitByPipe(line);
        if (parts.size() < 3) {
            throw new IllegalArgumentException("Too few fields");
        }

        String type = parts.get(0).trim();
        boolean done = "1".equals(parts.get(1).trim());
        String desc = parts.get(2).trim();

        Task t;
        switch (type) {
        case "T": {
            t = new Todo(desc);
            break;
        }
        case "D": {
            if (parts.size() < 4) throw new IllegalArgumentException("Deadline missing /by");
            LocalDateTime dt = LocalDateTime.parse(parts.get(3).trim(), FILE_DT);
            t = new Deadline(desc, dt);
            break;
        }
        case "E": {
            String fromStr = parts.size() > 3 ? parts.get(3).trim() : "";
            String toStr   = parts.size() > 4 ? parts.get(4).trim() : "";

            LocalDateTime from = null;
            LocalDateTime to   = null;

            try { if (!fromStr.isEmpty()) from = LocalDateTime.parse(fromStr, FILE_DT); } catch (Exception ignore) {}
            try { if (!toStr.isEmpty())   to   = LocalDateTime.parse(toStr,   FILE_DT); } catch (Exception ignore) {}

            t = new Event(desc, from, to);
            break;
        }
        default: {
            // Unknown type: keep loading instead of failing the whole file
            t = new Task(desc);
            break;
        }
        }

        if (done) {
            t.mark();
        } else {
            t.unmark();
        }
        return t;
    }


    // Join fields as "A | B | C" with spaces for readability.
    private static String join(String... fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) sb.append(" | ");
            sb.append(fields[i] == null ? "" : fields[i]);
        }
        return sb.toString();
    }

    // Split a line by the '|' character without using regex; trims outer spaces of each token.
    private static List<String> splitByPipe(String s) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '|') {
                out.add(cur.toString().trim());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        out.add(cur.toString().trim());
        return out;
    }
}
