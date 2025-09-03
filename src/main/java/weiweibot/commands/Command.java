package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;

/**
 * Represents an executable user command.
 */
public abstract class Command {

    /**
     * Executes this command.
     *
     * @return true if the app should exit after this command, otherwise false.
     */
    public abstract String execute(TaskList tasks, Storage storage);
}
