public class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() { return from; }
    public String getTo()   { return to; }

    @Override
    public String toString() {
        // [E][ ] description (from: <from> to: <to>)
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
