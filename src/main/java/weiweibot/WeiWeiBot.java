package weiweibot;

import java.util.Locale;

import weiweibot.commands.Command;
import weiweibot.commands.DeleteCommand;
import weiweibot.commands.ExitCommand;
import weiweibot.commands.FindCommand;
import weiweibot.commands.HelpCommand;
import weiweibot.commands.ListCommand;
import weiweibot.commands.MarkCommand;
import weiweibot.exceptions.WeiExceptions;
import weiweibot.parsers.CommandValidator;
import weiweibot.parsers.DeadlineParser;
import weiweibot.parsers.EventParser;
import weiweibot.parsers.TodoParser;
import weiweibot.storage.Storage;
import weiweibot.tasks.TaskList;

/**
 * Application entry point for WeiWeiBot.
 *
 * <p>Sets up file-backed storage, loads the task list, constructs the console UI,
 * and starts the interactive loop.</p>
 */
public class WeiWeiBot {
    private Storage storage;
    private TaskList tasks;
    private boolean isExit;

    /**
     * Initializes the WeiWeiBot instance with storage and task list.
     */
    public WeiWeiBot() {
        storage = new Storage("data", "tasks.txt");
        tasks = storage.load();
    }

    public String getResponse(String input) {
        assert input != null : "Input must not be null";
        try {
            String[] parts = input.split("\\s+", 2);
            String cmd = parts[0].toLowerCase(Locale.ROOT);
            String rest = parts.length > 1 ? parts[1] : "";

            Command parsedCommand = parseCommand(cmd, rest);
            String exitString = parsedCommand.execute(tasks, storage);
            isExit = cmd.equals("bye");
            return exitString;
        } catch (WeiExceptions e) {
            return "Error: " + e.getMessage();
        }
    }

    public boolean shouldExit() {
        return isExit;
    }

    private Command parseCommand(String cmd, String rest) throws WeiExceptions {
        assert cmd != null : "Command must not be empty";

        Command toRun;
        switch (cmd) {
        case "bye":
            toRun = new ExitCommand();
            break;

        case "help":
            toRun = new HelpCommand();
            break;

        case "list":
            toRun = new ListCommand();
            break;

        case "todo":
            toRun = new TodoParser().parse(rest);
            break;

        case "deadline":
            toRun = new DeadlineParser().parse(rest);
            break;

        case "event":
            toRun = new EventParser().parse(rest);
            break;

        case "find": {
            String data = rest.trim();
            if (data.isEmpty()) {
                throw new WeiExceptions("Usage: find <matching strings in a task description>");
            }
            toRun = new FindCommand(data);
            break;
        }

        case "mark": {
            int idx = CommandValidator.parseIndex(rest);
            toRun = new MarkCommand(idx, true);
            break;
        }

        case "unmark": {
            int idx = CommandValidator.parseIndex(rest);
            toRun = new MarkCommand(idx, false);
            break;
        }

        case "delete": {
            int idx = CommandValidator.parseIndex(rest);
            toRun = new DeleteCommand(idx);
            break;
        }

        default:
            throw new WeiExceptions("Unknown command: " + cmd
                + ". Type 'help' to see available commands.");
        }
        return toRun;
    }
}
