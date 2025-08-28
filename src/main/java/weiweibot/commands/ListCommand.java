package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;

import java.util.List;

public class ListCommand extends Command {
    @Override
    public boolean execute(List<Task> tasks, Storage storage) {
        System.out.println("\t" + LINE);
        if (tasks.isEmpty()) {
            System.out.println("\t Your list is empty.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf("\t %d.%s%n", i + 1, tasks.get(i));
            }
        }
        System.out.println("\t" + LINE);
        return false;
    }
}
