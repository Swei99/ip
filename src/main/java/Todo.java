public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        // [T][ ] description  or  [T][x] description
        return "[T]" + super.toString();
    }
}
