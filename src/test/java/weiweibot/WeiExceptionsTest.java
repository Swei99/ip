package weiweibot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import weiweibot.exceptions.WeiExceptions;
import weiweibot.parsers.CommandValidator;
import weiweibot.tasks.TaskList;
import weiweibot.tasks.Todo;

class WeiExceptionsTest {

    @Test
    void parseIndex_invalidValues_throwWeiExceptions() {
        assertThrows(WeiExceptions.class, () -> CommandValidator.parseIndex("0"));
        assertThrows(WeiExceptions.class, () -> CommandValidator.parseIndex("-3"));
        assertThrows(WeiExceptions.class, () -> CommandValidator.parseIndex("abc"));
        assertThrows(WeiExceptions.class, () -> CommandValidator.parseIndex("  "));
    }

    @Test
    void taskList_outOfBoundsOperations_throwWeiExceptions() {
        TaskList list = new TaskList();
        list.addTask(new Todo("Only one"));
        assertThrows(WeiExceptions.class, () -> list.getTask(-1));
        assertThrows(WeiExceptions.class, () -> list.deleteTask(5));
    }
}
