package weiweibot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Task with a single due date/time.
 *
 * <p>Rendered as {@code [D]<base> (by: <formatted>)} where the due date/time is
 * formatted with pattern {@code "MMM d yyyy h:mma"} (e.g., {@code Jan 3 2025 9:05PM}).</p>
 */
public class Deadline extends Task {
    private final LocalDateTime by;
    private static final DateTimeFormatter OUT_FMT =
            DateTimeFormatter.ofPattern("MMM d yyyy h:mma", Locale.ENGLISH);

    /**
     * Creates a deadline task.
     *
     * @param description brief description of the task
     * @param by due date/time for the task
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public LocalDateTime getBy() { return by; }

    @Override
    public String toString() {
        String pretty = by.format(OUT_FMT);
        return "[D]" + super.toString() + " (by: " + pretty + ")";
    }
}
