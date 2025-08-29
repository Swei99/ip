package weiweibot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import weiweibot.tasks.Todo;

class TodoTest {

    @Test
    void toString_hasPrefixAndReflectsMarkedState() {
        Todo todo = new Todo("Buy milk");
        // Initially unmarked
        String s1 = todo.toString();
        assertTrue(s1.contains("[T]"), "Todo should start with [T]");
        assertTrue(s1.contains("[ ]"), "Unmarked todo should render [ ]");
        assertTrue(s1.endsWith("Buy milk"), "Description should appear at the end");

        // After mark
        todo.mark();
        String s2 = todo.toString();
        assertTrue(s2.contains("[x]"), "Marked todo should render [x]");
        assertTrue(todo.isMarked());
        // After unmark
        todo.unmark();
        assertFalse(todo.isMarked());
    }
}
