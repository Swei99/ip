package weiweibot.commands;

import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;

/**
 * Displays a concise list of supported commands and expected date/time formats.
 *
 * <p>Side effects: prints a help message. Does not modify {@link TaskList} and does not save.</p>
 */
public class HelpCommand extends Command {
    @Override
    public boolean execute(TaskList tasks, Storage storage) {
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
