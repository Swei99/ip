package weiweibot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDateTime;

import weiweibot.storage.Storage;
import weiweibot.tasks.Deadline;
import weiweibot.tasks.Event;
import weiweibot.tasks.Task;
import weiweibot.tasks.TaskList;
import weiweibot.tasks.Todo;

class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    void saveThenLoad_restoresTasksAndFields() {
        Storage storage = new Storage(tempDir.toString(), "tasks.txt");

        TaskList out = new TaskList();
        Todo t1 = new Todo("Milk");
        t1.mark();
        out.addTask(t1);
        out.addTask(new Deadline("Report", LocalDateTime.of(2025, 12, 31, 18, 0)));
        out.addTask(new Event("Meeting",
                LocalDateTime.of(2026, 1, 1, 9, 0),
                LocalDateTime.of(2026, 1, 1, 10, 30)));

        storage.save(out);

        TaskList in = storage.load();
        assertEquals(3, in.getNumberOfTasks());

        Task a = in.getTask(0);
        assertTrue(a instanceof Todo);
        assertEquals("Milk", a.getDescription());
        assertTrue(a.isMarked());

        Task b = in.getTask(1);
        assertTrue(b instanceof Deadline);
        assertEquals("Report", b.getDescription());
        assertEquals(LocalDateTime.of(2025, 12, 31, 18, 0), ((Deadline) b).getBy());

        Task c = in.getTask(2);
        assertTrue(c instanceof Event);
        Event ev = (Event) c;
        assertEquals("Meeting", ev.getDescription());
        assertEquals(LocalDateTime.of(2026, 1, 1, 9, 0), ev.getFrom());
        assertEquals(LocalDateTime.of(2026, 1, 1, 10, 30), ev.getTo());
    }
}
