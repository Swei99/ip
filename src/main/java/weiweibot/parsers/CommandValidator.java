package weiweibot.parsers;

import weiweibot.exceptions.WeiExceptions;

/**
 * Small utility for validating and parsing user-supplied command arguments.
 *
 * <p>Methods throw {@link WeiExceptions} with concise, user-facing messages
 * when inputs are missing or invalid.</p>
 */
public final class CommandValidator {
    private CommandValidator() {}

    /**
     * Ensures a string is non-null and not blank (after trimming).
     *
     * @param value the input string to check
     * @param usage message to include in the exception to guide the user
     * @throws WeiExceptions if {@code value} is {@code null} or blank
     */
    public static void requireNonEmpty(String value, String usage) {
        if (value == null || value.trim().isEmpty()) {
            throw new WeiExceptions(usage);
        }
    }

    /**
     * Parses a user-visible 1-based index (e.g., {@code "2"}) into a zero-based int.
     *
     * <p>Trims whitespace and validates that the number is a positive integer.</p>
     *
     * @param raw the raw index string provided by the user
     * @return the zero-based index ({@code parsedValue - 1})
     * @throws WeiExceptions if {@code raw} is missing, not a number, or not positive
     */
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
