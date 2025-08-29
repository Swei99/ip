package weiweibot.ui;

import java.util.Locale;
import java.util.Scanner;

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

import java.util.Locale;
import java.util.Scanner;

/**
 * Console-based UI that reads user commands, delegates to parsers/commands,
 * handles user-facing errors, and prints responses.
 *
 * <p>Lifecycle: construct with a {@link Storage} and {@link TaskList}, then
 * call {@link #run()} to start the interactive loop. The loop ends when a
 * command signals exit.</p>
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Storage storage;
    private final TaskList tasks;


    /**
     * Creates a UI bound to the given storage and task list.
     *
     * @param storage backing storage used by executed commands
     * @param tasks   mutable task list shared across commands
     */
    public Ui(Storage storage, TaskList tasks) {
        this.storage = storage;
        this.tasks = tasks;
    }

    /**
     * Starts the interactive REPL loop.
     *
     * <p>Reads lines from {@code System.in}, splits into a command word and
     * arguments, constructs the corresponding {@link Command}, executes it,
     * and exits when a command returns {@code true}. Any {@link WeiExceptions}
     * are caught and printed in a framed message.</p>
     */
    public void run() {
        printWelcome();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\s+", 2);
                String cmd = parts[0].toLowerCase(Locale.ROOT);
                String rest = parts.length > 1 ? parts[1] : "";

                try {
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
                            throw new WeiExceptions("Usage: find <keywords>");
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

                    boolean shouldExit = toRun.execute(tasks, storage);
                    if (shouldExit) {
                        return;
                    }

                } catch (WeiExceptions e) {
                    System.out.println("\t" + LINE);
                    System.out.println("\t " + e.getMessage());
                    System.out.println("\t" + LINE);
                }
            }
        }
    }

    private void printWelcome() {
        System.out.println("\t" + LINE);
        System.out.println("\t Hello! I'm weiwei");
        System.out.println("\t What can I do for you?");
        System.out.println("\t (Type 'help' for commands)");
        System.out.println("\t" + LINE);
    }
}
