package weiweibot.commands;

import java.util.List;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;

/**
 * Searches for tasks whose descriptions match a query and prints the results.
 *
 * <p>Side effects: prints a formatted list of matches (or a "no matches" message).
 * Does not modify {@link TaskList} and does not save.</p>
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList tasks, Storage storage) {
        List<Integer> indices = tasks.findIndicesByDescription(keyword);
        StringBuilder returnString = new StringBuilder();
        if (indices.isEmpty()) {
            returnString.append("\t No matching tasks found.");
        } else {
            returnString.append("\t Here are the matching tasks in your list:\n");
            for (Integer i : indices) {
                returnString.append(String.format("\t %d.%s%n", i + 1, tasks.getTask(i)));
            }
        }
        return returnString.toString();
    }
}
