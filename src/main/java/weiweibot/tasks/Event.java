package weiweibot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Task that spans a time range with a start and end.
 *
 * <p>Rendered as {@code [E]<base> (from: <start> to <end>)} where both timestamps are
 * formatted with {@code "MMM d yyyy h:mma"} (e.g., {@code Jan 3 2025 9:05PM}).</p>
 */
public class Event extends Task {
    private static final DateTimeFormatter OUT =
            DateTimeFormatter.ofPattern("MMM d yyyy h:mma", Locale.ENGLISH);

    private final LocalDateTime from;
    private final LocalDateTime to;
    /**
     * Creates an event task with a description and start/end date-times.
     *
     * @param description brief description of the event
     * @param from start date-time (inclusive)
     * @param to end date-time (typically after {@code from})
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }
    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        String range = from.format(OUT) + " to " + to.format(OUT);
        return "[E]" + super.toString() + " (from: " + range + ")";
    }
}
