package weiweibot.parsers;

import weiweibot.exceptions.WeiExceptions;

public final class CommandValidator {
    private CommandValidator() {}

    public static void requireNonEmpty(String value, String usage) {
        if (value == null || value.trim().isEmpty()) {
            throw new WeiExceptions(usage);
        }
    }

    /** Parse user 1-based index to 0-based. */
    public static int parseIndex(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            throw new WeiExceptions("Please provide a number. Example: mark 2");
        }
        try {
            int oneBased = Integer.parseInt(raw.trim());
            if (oneBased <= 0) throw new WeiExceptions("Index must be a positive integer.");
            return oneBased - 1;
        } catch (NumberFormatException ex) {
            throw new WeiExceptions("Please provide a valid number. Example: mark 2");
        }
    }
}
