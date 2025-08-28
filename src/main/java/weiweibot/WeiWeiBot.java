package weiweibot;

import weiweibot.storage.Storage;
import weiweibot.ui.Ui;
import weiweibot.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class WeiWeiBot {
    public static void main(String[] args) {
        Storage storage = new Storage("data", "tasks.txt");
        List<Task> tasks = new ArrayList<>();
        try {
            tasks.addAll(storage.load());
        } catch (RuntimeException ex) {
            System.err.println("Warning: " + ex.getMessage());
        }

        Ui ui = new Ui(storage, tasks);
        ui.run();
    }
}
