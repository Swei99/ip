package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;

/**
 * Represents an executable user command.
 */
public abstract class Command {
    protected static final String LINE = "____________________________________________________________";

    /**
     * Executes this command.
     *
     * @return true if the app should exit after this command, otherwise false.
     */
    public abstract boolean execute(TaskList tasks, Storage storage);
}
