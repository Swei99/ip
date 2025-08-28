package weiweibot.tasks;

import weiweibot.exceptions.WeiExceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A container for {@link Task} objects that provides basic list operations.
 * (Does not implement Iterable to keep the API explicit.)
 */
public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial != null ? initial : List.of());
    }

    /** Returns the number of tasks stored. */
    public int getNumberOfTasks() {
        return tasks.size();
    }

    /** True if there are no tasks. */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /** Adds a task to the end of the list. */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes and returns the task at {@code index} (0-based).
     * @throws WeiExceptions if index is out of range.
     */
    public Task deleteTask(int index) {
        checkIndex(index);
        return tasks.remove(index);
    }

    /**
     * Returns the task at {@code index} (0-based) without removing it.
     * @throws WeiExceptions if index is out of range.
     */
    public Task getTask(int index) {
        checkIndex(index);
        return tasks.get(index);
    }

    /**
     * Finds the first occurrence of {@code task}.
     * @return the 0-based index, or -1 if not found.
     */
    public int findTask(Task task) {
        return tasks.indexOf(task);
    }

    /**
     * Returns the indices (0-based) of tasks whose descriptions contain {@code query}, case-insensitive.
     */
    public List<Integer> findIndicesByDescription(String query) {
        String needle = query == null ? "" : query.trim().toLowerCase();
        List<Integer> indices = new ArrayList<>();
        if (needle.isEmpty()) {
            return indices;
        }
        for (int i = 0; i < tasks.size(); i++) {
            String desc = tasks.get(i).getDescription();
            if (desc != null && desc.toLowerCase().contains(needle)) {
                indices.add(i);
            }
        }
        return indices;
    }

    /** Read-only snapshot for serializers/printing. */
    public List<Task> asUnmodifiableList() {
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "Your list is empty.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(String.format(" %d.%s%n", i + 1, tasks.get(i)));
        }
        // Trim trailing newline for neat printing.
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\n') {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new WeiExceptions("Invalid index: " + (index + 1));
        }
    }
}
