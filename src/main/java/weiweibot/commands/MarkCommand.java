package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;
import weiweibot.tasks.TaskList;

/**
 * Sets a task as done or not done, then saves the list.
 *
 * <p>Construct with a zero-based index and a boolean state:
 * {@code true} to mark as done, {@code false} to unmark.</p>
 *
 * <p>Side effects: updates the task in {@link TaskList}, calls
 * {@link Storage#save(TaskList)}, and prints a short message.</p>
 */
public class MarkCommand extends Command {
    private final int indexZeroBased;
    private final boolean markValue;

    /**
     * Creates a command to mark or unmark a task.
     *
     * @param indexZeroBased zero-based index of the target task
     * @param markValue {@code true} to mark as done; {@code false} to unmark
     */
    public MarkCommand(int indexZeroBased, boolean markValue) {
        this.indexZeroBased = indexZeroBased;
        this.markValue = markValue;
    }

    /**
     * Applies the requested state to the target task, saves the list, prints feedback,
     * and returns {@code false} (no exit).
     *
     * @return {@code false} - this command never terminates the application
     */
    @Override
    public boolean execute(TaskList tasks, Storage storage) {
        Task t = tasks.getTask(indexZeroBased);
        if (markValue) {
            t.mark();
        } else {
            t.unmark();
        }
        storage.save(tasks);
        System.out.println("\t" + LINE);
        System.out.println(markValue
                ? "\t Nice! I've marked this task as done:"
                : "\t OK, I've marked this task as not done yet:");
        System.out.println("\t   " + t);
        System.out.println("\t" + LINE);
        return false;
    }
}
