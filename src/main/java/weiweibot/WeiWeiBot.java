package weiweibot;

import weiweibot.storage.Storage;
import weiweibot.ui.Ui;
import weiweibot.tasks.TaskList;

public class WeiWeiBot {
    public static void main(String[] args) {
        Storage storage = new Storage("data", "tasks.txt");

        TaskList tasks = storage.load();

        Ui ui = new Ui(storage, tasks);
        ui.run();
    }
}
