package weiweibot.parsers;

import weiweibot.commands.AddCommand;
import weiweibot.commands.Command;
import weiweibot.exceptions.WeiExceptions;
import weiweibot.tasks.Todo;

public class ToDoParser extends Parser {
    @Override
    public Command parse(String args) {
        String desc = args == null ? "" : args.trim();
        if (desc.isEmpty()) {
            throw new WeiExceptions("Usage: todo <description>");
        }
        return new AddCommand(new Todo(desc));
    }
}
