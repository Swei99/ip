package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;

import java.util.List;

public class AddCommand extends Command {
    private final Task taskToAdd;

    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    @Override
    public boolean execute(List<Task> tasks, Storage storage) {
        tasks.add(taskToAdd);
        storage.save(tasks);
        System.out.println("\t" + LINE);
        System.out.println("\t Got it. I've added this task:");
        System.out.println("\t   " + taskToAdd);
        System.out.printf("\t Now you have %d %s in the list.%n",
                tasks.size(), tasks.size() == 1 ? "task" : "tasks");
        System.out.println("\t" + LINE);
        return false;
    }
}
