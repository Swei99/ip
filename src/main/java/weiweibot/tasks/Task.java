package weiweibot.tasks;

/**
 * Base task model with a textual description and a completion flag.
 *
 * <p>Tasks start unmarked (not done). The string form is
 * {@code "[x] <description>"} when marked, or {@code "[ ] <description>"}
 * when unmarked.</p>
 */
public class Task {
    private String description;
    private boolean isMark;

    /**
     * Creates a task with the given description; initial state is unmarked.
     *
     * @param description brief text describing the task
     */
    public Task(String description) {
        this.description = description;
        this.isMark = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMarked() {
        return marked;
    }

    public void mark() {
        this.isMark = true;
    }

    public void unmark() {
        this.isMark = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isMark ? "x" : " ", description);
    }
}
