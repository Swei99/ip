package weiweibot;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;
import weiweibot.ui.Ui;

public class WeiWeiBot {
    public static void main(String[] args) {
        Storage storage = new Storage("data", "tasks.txt");

        TaskList tasks = storage.load();

        Ui ui = new Ui(storage, tasks);
        ui.run();
    }
}
