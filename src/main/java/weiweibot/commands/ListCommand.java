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
    public boolean execute(TaskList tasks, Storage storage) {
        System.out.println("\t" + LINE);
        if (tasks.isEmpty()) {
            System.out.println("\t Your list is empty.");
        } else {
            // indent TaskList's toString by one tab for each line
            System.out.print("\t");
            System.out.println(tasks.toString().replace("\n", "\n\t"));
        }
        System.out.println("\t" + LINE);
        return false;
    }
}
