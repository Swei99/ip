package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.Task;

import java.util.List;

public class HelpCommand extends Command {
    @Override
    public boolean execute(List<Task> tasks, Storage storage) {
        System.out.println("\t" + LINE);
        System.out.println(
                "\tCommands:\n" +
                "\t  todo <desc>\n" +
                "\t  deadline <desc> /by <d-M-yyyy HHmm | d-M-yyyy>\n" +
                "\t  event <desc> /from <d-M-yyyy HHmm> /to <d-M-yyyy HHmm>\n" +
                "\t  list | find <kw> | mark <n> | unmark <n> | delete <n> | bye\n"
        );
        System.out.println("\t" + LINE);
        return false;
    }
}
