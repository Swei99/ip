package weiweibot.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import weiweibot.exceptions.WeiExceptions;

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
        String objectDescription;
        if (query == null) {
            objectDescription = "";
        } else {
            String trimmed = query.trim();
            objectDescription = trimmed.toLowerCase();
        }
        List<Integer> indices = new ArrayList<>();
        if (objectDescription.isEmpty()) {
            return indices;
        }
        for (int i = 0; i < tasks.size(); i++) {
            String desc = tasks.get(i).getDescription();
            if (desc != null && desc.toLowerCase().contains(objectDescription)) {
                indices.add(i);
            }
        }
        return indices;
    }

    /** Read-only snapshot for serializers/printing. */
    public List<Task> asUnmodifiableList() {
        return Collections.unmodifiableList(tasks);
    }

    public boolean isDuplicate(Task incomingTask) {
        if (incomingTask == null) {
            return false;
        }
        String incomingTaskDesc = incomingTask.getDescription() == null
                ? ""
                : incomingTask.getDescription().trim().toLowerCase();

        for (Task existingTask : asUnmodifiableList()) {
            if (!existingTask.getClass().equals(incomingTask.getClass())) {
                continue;
            }

            String taskDesc = existingTask.getDescription() == null
                    ? ""
                    : existingTask.getDescription().trim().toLowerCase();

            if (!taskDesc.equals(incomingTaskDesc)) {
                continue;
            }

            if (existingTask instanceof Todo) {
                return true;
            } else if (existingTask instanceof Deadline && incomingTask instanceof Deadline) {
                return ((Deadline) existingTask).getBy().isEqual(((Deadline) incomingTask).getBy());
            } else if (existingTask instanceof Event && incomingTask instanceof Event) {
                Event te = (Event) existingTask;
                Event ce = (Event) incomingTask;
                return te.getFrom().isEqual(ce.getFrom()) && te.getTo().isEqual(ce.getTo());
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "Your list is empty.";
        }
        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            returnString.append(String.format(" %d.%s%n", i + 1, tasks.get(i)));
        }
        // Trim trailing newline for neat printing.
        if (returnString.length() > 0 && returnString.charAt(returnString.length() - 1) == '\n') {
            returnString.setLength(returnString.length() - 1);
        }
        return returnString.toString();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new WeiExceptions("Invalid index: " + (index + 1));
        }
    }
}
