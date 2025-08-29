package weiweibot.commands;

import java.util.List;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;

public class FindCommand extends Command {
    private final String needle;

    public FindCommand(String needle) {
        this.needle = needle;
    }

    @Override
    public boolean execute(TaskList tasks, Storage storage) {
        List<Integer> indices = tasks.findIndicesByDescription(needle);
        System.out.println("\t" + LINE);
        if (indices.isEmpty()) {
            System.out.println("\t No matching tasks found.");
        } else {
            System.out.println("\t Here are the matching tasks in your list:");
            for (Integer i : indices) {
                System.out.printf("\t %d.%s%n", i + 1, tasks.getTask(i));
            }
        }
        System.out.println("\t" + LINE);
        return false;
    }
}
