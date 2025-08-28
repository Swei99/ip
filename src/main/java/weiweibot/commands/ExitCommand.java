package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;

import java.util.List;

public class ExitCommand extends Command {
    @Override
    public boolean execute(List<Task> tasks, Storage storage) {
        System.out.println("\t" + LINE);
        System.out.println("\t Bye. Hope to see you again soon!");
        System.out.println("\t" + LINE);
        return true;
    }
}
