package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;

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
