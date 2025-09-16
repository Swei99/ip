package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;
import weiweibot.tasks.TaskList;


/**
 * Adds a single {@link Task} to the list and saves the updated state.
 *
 * <p>Side effects: mutates the provided {@link TaskList}, calls
 * {@link Storage#save(TaskList)}, and prints a short confirmation message.</p>
 *
 * <p>This command never requests program termination; {@link #execute(TaskList, Storage)}
 * always returns {@code false}.</p>
 */
public class AddCommand extends Command {
    private final Task taskToAdd;

    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    @Override
    public String execute(TaskList tasks, Storage storage) {
        if (tasks.isDuplicate(taskToAdd)) {
            return "Duplicate task detected. The following task would not be added: "
                + taskToAdd.getDescription().toString();
        }
        tasks.addTask(taskToAdd);
        storage.save(tasks);
        StringBuilder returnString = new StringBuilder();
        returnString.append(" Got it. I've added this task:\n");
        returnString.append(" " + taskToAdd + "\n");
        int n = tasks.getNumberOfTasks();
        returnString.append(String.format("\t Now you have %d %s in the list.%n", n, n == 1 ? "task" : "tasks"));
        return returnString.toString();
    }
}
