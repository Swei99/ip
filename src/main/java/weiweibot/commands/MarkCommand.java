package weiweibot.commands;

import weiweibot.exceptions.WeiExceptions;
import weiweibot.storage.Storage;
import weiweibot.tasks.Task;

import java.util.List;

public class MarkCommand extends Command {
    private final int indexZeroBased;
    private final boolean markValue; // true = mark, false = unmark

    public MarkCommand(int indexZeroBased, boolean markValue) {
        this.indexZeroBased = indexZeroBased;
        this.markValue = markValue;
    }

    @Override
    public boolean execute(List<Task> tasks, Storage storage) {
        if (indexZeroBased < 0 || indexZeroBased >= tasks.size()) {
            throw new WeiExceptions("Invalid index: " + (indexZeroBased + 1));
        }
        Task t = tasks.get(indexZeroBased);
        if (markValue) t.mark(); else t.unmark();
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
