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
    public String execute(TaskList tasks, Storage storage) {
        Task removed = tasks.deleteTask(indexZeroBased);
        storage.save(tasks);
        StringBuilder returnString = new StringBuilder();
        returnString.append("\t Noted. I've removed this task:\n");
        returnString.append("\t   " + removed + "\n");
        int n = tasks.getNumberOfTasks();
        returnString.append("\t Now you have " + n + " " + (n == 1 ? "task" : "tasks") + " in the list.\n");
        return returnString.toString();
    }

}
