package weiweibot;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;
import weiweibot.ui.Ui;

/**
 * Application entry point for WeiWeiBot.
 *
 * <p>Sets up file-backed storage, loads the task list, constructs the console UI,
 * and starts the interactive loop.</p>
 */
public class WeiWeiBot {
    public static void main(String[] args) {
        Storage storage = new Storage("data", "tasks.txt");

        TaskList tasks = storage.load();

        Ui ui = new Ui(storage, tasks);
        ui.run();
    }
}
