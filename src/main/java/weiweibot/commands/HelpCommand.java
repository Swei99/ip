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
    public String execute(TaskList tasks, Storage storage) {
        StringBuilder returnString = new StringBuilder();
        returnString.append("Commands:\n");
        returnString.append(" todo <desc>\n");
        returnString.append(" deadline <desc> /by <d-M-yyyy HHmm | d-M-yyyy>\n");
        returnString.append(" event <desc> /from <d-M-yyyy HHmm> /to <d-M-yyyy HHmm>\n");
        returnString.append(" list | find <kw> | mark <n> | unmark <n> | delete <n> | bye\n");
        return returnString.toString();
    }
}
