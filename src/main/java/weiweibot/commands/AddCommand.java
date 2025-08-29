package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;
import weiweibot.tasks.TaskList;


/**
 * Adds a single {@link Task} to the list and saves the updated state.
 *
 * <p>Side effects: mutates the provided {@link TaskList}, calls
 * {@link Storage#save(TaskList)}, and prints a short confirmation message.</p>
 *
 * <p>This command never requests program termination; {@link #execute(TaskList, Storage)}
 * always returns {@code false}.</p>
 */
public class AddCommand extends Command {
    private final Task taskToAdd;

    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    @Override
    public boolean execute(TaskList tasks, Storage storage) {
        tasks.addTask(taskToAdd);
        storage.save(tasks);
        System.out.println("\t" + LINE);
        System.out.println("\t Got it. I've added this task:");
        System.out.println("\t   " + taskToAdd);
        int n = tasks.getNumberOfTasks();
        System.out.printf("\t Now you have %d %s in the list.%n", n, n == 1 ? "task" : "tasks");
        System.out.println("\t" + LINE);
        return false;
    }
}
