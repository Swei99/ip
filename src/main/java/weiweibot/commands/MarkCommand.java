package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;
import weiweibot.tasks.TaskList;

public class MarkCommand extends Command {
    private final int indexZeroBased;
    private final boolean markValue;

    public MarkCommand(int indexZeroBased, boolean markValue) {
        this.indexZeroBased = indexZeroBased;
        this.markValue = markValue;
    }

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
