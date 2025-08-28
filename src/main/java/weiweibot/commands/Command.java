package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;

import java.util.List;

public abstract class Command {
    protected static final String LINE = "____________________________________________________________";

    /**
     * Execute command on the provided task list and storage.
     * @return true if the app should exit after this command, false otherwise.
     */
    public abstract boolean execute(List<Task> tasks, Storage storage);
}
