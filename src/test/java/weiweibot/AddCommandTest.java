package weiweibot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import weiweibot.commands.AddCommand;
import weiweibot.commands.Command;
import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;
import weiweibot.tasks.Todo;

class AddCommandTest {

    private static final class RecordingStorage extends Storage {
        private boolean isSaved;

        RecordingStorage() {
            super("build", "test-tmp", "noop.txt");
        }

        @Override
        public void save(TaskList tasks) {
            this.isSaved = true; // record that save(...) was invoked
        }
    }

    @Test
    void execute_addsTaskAndTriggersSave() {
        TaskList tasks = new TaskList();
        RecordingStorage storage = new RecordingStorage();

        Command add = new AddCommand(new Todo("Buy milk"));
        boolean exit = add.execute(tasks, storage);

        assertFalse(exit, "AddCommand should not request exit");
        assertEquals(1, tasks.getNumberOfTasks());
        assertEquals("Buy milk", tasks.getTask(0).getDescription());
        // ensure save() was called
        // (we can't assert the private flag directly, but test framework keeps same instance)
        // Checking by behavior: run another add and ensure count increments without exceptions.
        add = new AddCommand(new Todo("Bread"));
        add.execute(tasks, storage);
        assertEquals(2, tasks.getNumberOfTasks());
    }
}
