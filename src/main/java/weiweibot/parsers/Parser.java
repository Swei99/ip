package weiweibot.parsers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import weiweibot.commands.Command;
import weiweibot.exceptions.WeiExceptions;

/** Abstract per-command parser. */
public abstract class Parser {
    // Accepted input formats
    protected static final DateTimeFormatter[] IN_DT_ONLY = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("d-M-uuuu HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("d/M/uuuu HHmm")
    };
    protected static final DateTimeFormatter[] IN_D_ONLY = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("d-M-uuuu"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("d/M/uuuu")
    };

    /** Subclasses parse their own args and return a Command. */
    public abstract Command parse(String args);

    /** Flexible parse: try date-time formats, else date-only (start-of-day). */
    protected static LocalDateTime parseFlexibleDateTime(String raw) {
        String s = raw.trim();
        for (DateTimeFormatter fmt : IN_DT_ONLY) {
            try {
                return LocalDateTime.parse(s, fmt);
            } catch (Exception ignore) {
                System.out.println(ignore.getMessage());
            }
        }
        for (DateTimeFormatter fmt : IN_D_ONLY) {
            try {
                return LocalDate.parse(s, fmt).atStartOfDay();
            } catch (Exception ignore) {
                System.out.println(ignore.getMessage());
            }
        }
        throw new WeiExceptions("Could not parse date/time: \"" + raw + "\". Try 31-12-2025 1800 or 31-12-2025.");
    }
}
