package weiweibot.tasks;

/**
 * A simple to-do task with only a description (no date/time).
 *
 * <p>Rendered as {@code [T]} followed by the base task string from {@link Task}.</p>
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
