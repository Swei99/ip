package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;

/**
 * Prints all tasks in the list, or a friendly message if the list is empty.
 *
 * <p>Side effects: prints to standard output. Does not modify {@link TaskList} and does not save.</p>
 */
public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Storage storage) {
        StringBuilder returnString = new StringBuilder();
        if (tasks.isEmpty()) {
            returnString.append("Your list is empty.");
        } else {
            returnString.append(tasks.toString());
        }
        returnString.append("\n");
        return returnString.toString();
    }
}
