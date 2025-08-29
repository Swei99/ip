package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;
import weiweibot.tasks.TaskList;


/**
 * Deletes the task at a given zero-based index and saves the updated list.
 *
 * <p>Side effects: removes an item from {@link TaskList}, calls
 * {@link Storage#save(TaskList)}, and prints a short confirmation message.</p>
 *
 * <p>Errors from {@link TaskList#deleteTask(int)} (e.g., out-of-bounds) are
 * propagated to the caller.</p>
 */
public class DeleteCommand extends Command {
    private final int indexZeroBased;

    public DeleteCommand(int indexZeroBased) {
        this.indexZeroBased = indexZeroBased;
    }

    @Override
    public boolean execute(TaskList tasks, Storage storage) {
        Task removed = tasks.deleteTask(indexZeroBased);
        storage.save(tasks);
        System.out.println("\t" + LINE);
        System.out.println("\t Noted. I've removed this task:");
        System.out.println("\t   " + removed);
        int n = tasks.getNumberOfTasks();
        System.out.printf("\t Now you have %d %s in the list.%n", n, n == 1 ? "task" : "tasks");
        System.out.println("\t" + LINE);
        return false;
    }
}
