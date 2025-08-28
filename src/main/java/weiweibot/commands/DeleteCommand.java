package weiweibot.commands;

import weiweibot.exceptions.WeiExceptions;
import weiweibot.storage.Storage;
import weiweibot.tasks.Task;

import java.util.List;

public class DeleteCommand extends Command {
    private final int indexZeroBased;

    public DeleteCommand(int indexZeroBased) {
        this.indexZeroBased = indexZeroBased;
    }

    @Override
    public boolean execute(List<Task> tasks, Storage storage) {
        if (indexZeroBased < 0 || indexZeroBased >= tasks.size()) {
            throw new WeiExceptions("Invalid index: " + (indexZeroBased + 1));
        }
        Task removed = tasks.remove(indexZeroBased);
        storage.save(tasks);
        System.out.println("\t" + LINE);
        System.out.println("\t Noted. I've removed this task:");
        System.out.println("\t   " + removed);
        System.out.printf("\t Now you have %d %s in the list.%n",
                tasks.size(), tasks.size() == 1 ? "task" : "tasks");
        System.out.println("\t" + LINE);
        return false;
    }
}
