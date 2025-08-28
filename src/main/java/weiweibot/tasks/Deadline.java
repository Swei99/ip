package weiweibot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    private final LocalDateTime by;
    private static final DateTimeFormatter OUT_FMT =
            DateTimeFormatter.ofPattern("MMM d yyyy h:mma", Locale.ENGLISH);

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
