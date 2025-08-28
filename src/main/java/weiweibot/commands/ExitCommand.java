package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;

public class ExitCommand extends Command {
    @Override
    public boolean execute(TaskList tasks, Storage storage) {
        System.out.println("\t" + LINE);
        System.out.println("\t Bye. Hope to see you again soon!");
        System.out.println("\t" + LINE);
        return true;
    }
}
