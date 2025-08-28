package weiweibot.tasks;

import weiweibot.exceptions.WeiExceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/** Thin wrapper around the underlying tasks with safe helpers. */
public class TaskList implements Iterable<Task> {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial != null ? initial : List.of());
    }

    public int size() { return tasks.size(); }
    public boolean isEmpty() { return tasks.isEmpty(); }

    public void add(Task t) { tasks.add(t); }

    public Task get(int index) {
        checkIndex(index);
        return tasks.get(index);
    }

    public Task remove(int index) {
        checkIndex(index);
        return tasks.remove(index);
    }

    /** Read-only view for serializers etc. */
    public List<Task> asList() {
        return Collections.unmodifiableList(tasks);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new WeiExceptions("Invalid index: " + (index + 1));
        }
    }

    @Override
    public Iterator<Task> iterator() {
        return asList().iterator();
    }
}
