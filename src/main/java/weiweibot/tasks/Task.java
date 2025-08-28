package weiweibot.tasks;

public class Task {
    private String description;
    private boolean marked;

    public Task(String description) {
        this.description = description;
        this.marked = false;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isMarked() { return marked; }
    public void mark() { this.marked = true; }
    public void unmark() { this.marked = false; }

    @Override
    public String toString() {
        return String.format("[%s] %s", marked ? "x" : " ", description);
    }
}
