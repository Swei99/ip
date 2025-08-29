package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;

/**
 * Prints a farewell message and signals the application to exit.
 *
 * <p>No mutations are performed on {@link TaskList} and no storage calls are made.</p>
 */
public class ExitCommand extends Command {
    @Override
    public boolean execute(TaskList tasks, Storage storage) {
        System.out.println("\t" + LINE);
        System.out.println("\t Bye. Hope to see you again soon!");
        System.out.println("\t" + LINE);
        return true;
    }
}
