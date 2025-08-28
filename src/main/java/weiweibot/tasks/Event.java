package weiweibot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    private static final DateTimeFormatter OUT =
            DateTimeFormatter.ofPattern("MMM d yyyy h:mma", Locale.ENGLISH);

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
