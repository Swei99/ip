package weiweibot.parsers;

import java.time.LocalDateTime;
import java.util.Locale;

import weiweibot.commands.AddCommand;
import weiweibot.commands.Command;
import weiweibot.exceptions.WeiExceptions;
import weiweibot.tasks.Deadline;

/**
 * Parses user input for the {@code deadline} command.
 *
 * <p>Expected format: {@code deadline <desc> /by <d-M-yyyy HHmm | d-M-yyyy>}.
 * Splits on {@code /by}, parses the date/time using
 * {@link Parser#parseFlexibleDateTime(String)}, and returns an
 * {@link AddCommand} that adds a {@link Deadline}.</p>
 *
 * <p>Validation: throws {@link WeiExceptions} if the syntax is missing or invalid.</p>
 */
public class DeadlineParser extends Parser {
    @Override
    public Command parse(String args) {
        if (args == null || args.trim().isEmpty()) {
            throw new WeiExceptions("Usage: deadline <desc> /by <d-M-yyyy HHmm | d-M-yyyy>");
        }
        String lower = args.toLowerCase(Locale.ROOT);
        int byIdx = lower.indexOf("/by");
        if (byIdx == -1) {
            throw new WeiExceptions("Usage: deadline <desc> /by <d-M-yyyy HHmm | d-M-yyyy>");
        }
        String desc = args.substring(0, byIdx).trim();
        String byRaw = args.substring(byIdx + 3).trim();
        if (desc.isEmpty() || byRaw.isEmpty()) {
            throw new WeiExceptions("Usage: deadline <desc> /by <d-M-yyyy HHmm | d-M-yyyy>");
        }
        LocalDateTime by = parseFlexibleDateTime(byRaw);
        return new AddCommand(new Deadline(desc, by));
    }
}
