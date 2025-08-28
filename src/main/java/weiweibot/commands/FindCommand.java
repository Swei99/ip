package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FindCommand extends Command {
    private final String needle;

    public FindCommand(String needle) {
        this.needle = needle.toLowerCase(Locale.ROOT);
    }

    @Override
    public boolean execute(List<Task> tasks, Storage storage) {
        List<String> hits = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getDescription().toLowerCase(Locale.ROOT).contains(needle)) {
                hits.add(String.format("\t %d.%s", i + 1, t));
            }
        }
        System.out.println("\t" + LINE);
        if (hits.isEmpty()) {
            System.out.println("\t No matching tasks found.");
        } else {
            System.out.println("\t Here are the matching tasks in your list:");
            for (String s : hits) System.out.println(s);
        }
        System.out.println("\t" + LINE);
        return false;
    }
}
