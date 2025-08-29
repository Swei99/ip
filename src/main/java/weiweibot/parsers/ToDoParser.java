package weiweibot.parsers;

import weiweibot.commands.AddCommand;
import weiweibot.commands.Command;
import weiweibot.exceptions.WeiExceptions;
import weiweibot.tasks.Todo;

/**
 * Parses user input for the {@code todo} command.
 *
 * <p>Expected format: {@code todo <description>}. Creates an {@link AddCommand}
 * that adds a {@link Todo} with the provided description.</p>
 *
 * <p>Validation: throws {@link WeiExceptions} if the description is missing or blank.</p>
 */
public class TodoParser extends Parser {
    @Override
    public Command parse(String args) {
        String desc = args == null ? "" : args.trim();
        if (desc.isEmpty()) {
            throw new WeiExceptions("Usage: todo <description>");
        }
        return new AddCommand(new Todo(desc));
    }
}
