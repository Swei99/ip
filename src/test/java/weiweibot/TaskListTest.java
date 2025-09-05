package weiweibot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import weiweibot.tasks.Deadline;
import weiweibot.tasks.Event;
import weiweibot.tasks.Task;
import weiweibot.tasks.TaskList;
import weiweibot.tasks.Todo;

class TaskListTest {

    @Test
    void findIndicesByDescription_caseInsensitive_multipleHits() {
        TaskList list = new TaskList();
        list.addTask(new Todo("Buy milk"));
        list.addTask(new Deadline("Submit report", LocalDateTime.of(2025, 12, 31, 18, 0)));
        list.addTask(new Event("Team meeting", LocalDateTime.of(2026, 1, 1, 9, 0),
                LocalDateTime.of(2026, 1, 1, 10, 30)));
        list.addTask(new Todo("buy groceries"));

        List<Integer> hits = list.findIndicesByDescription("BUY");
        assertEquals(List.of(0, 3), hits);
    }

    @Test
    void toString_isNumberedFromOne_andReflectsDeletes() {
        TaskList list = new TaskList();
        list.addTask(new Todo("Alpha"));
        list.addTask(new Todo("Beta"));
        list.addTask(new Todo("Gamma"));

        String rendered = list.toString();
        assertTrue(rendered.contains(" 1.[T]"));
        assertTrue(rendered.contains(" 2.[T]"));
        assertTrue(rendered.contains(" 3.[T]"));

        Task removed = list.deleteTask(1);
        assertEquals("Beta", removed.getDescription());
        assertEquals("Gamma", list.getTask(1).getDescription());
    }
}
