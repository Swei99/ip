package weiweibot.parsers;

import java.time.LocalDateTime;
import java.util.Locale;

import weiweibot.commands.AddCommand;
import weiweibot.commands.Command;
import weiweibot.exceptions.WeiExceptions;
import weiweibot.tasks.Event;

public class EventParser extends Parser {
    @Override
    public Command parse(String args) {
        if (args == null || args.trim().isEmpty()) {
            throw new WeiExceptions("Usage: event <desc> /from <d-M-yyyy HHmm> /to <d-M-yyyy HHmm>");
        }
        String lower = args.toLowerCase(Locale.ROOT);
        int fromIdx = lower.indexOf("/from");
        int toIdx = lower.indexOf("/to", Math.max(0, fromIdx) + 5);

        if (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx + 5) {
            throw new WeiExceptions("Usage: event <desc> /from <d-M-yyyy HHmm> /to <d-M-yyyy HHmm>");
        }

        String desc = args.substring(0, fromIdx).trim();
        String fromRaw = args.substring(fromIdx + 5, toIdx).trim();
        String toRaw = args.substring(toIdx + 3).trim();

        if (desc.isEmpty() || fromRaw.isEmpty() || toRaw.isEmpty()) {
            throw new WeiExceptions("Usage: event <desc> /from <d-M-yyyy HHmm> /to <d-M-yyyy HHmm>");
        }

        LocalDateTime from = parseFlexibleDateTime(fromRaw);
        LocalDateTime to = parseFlexibleDateTime(toRaw);
        if (to.isBefore(from)) {
            throw new WeiExceptions("/to must be after /from.");
        }
        return new AddCommand(new Event(desc, from, to));
    }
}
