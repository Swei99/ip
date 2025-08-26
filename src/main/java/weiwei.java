//packagepackage todobuddy.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class weiwei {
    private static final String LINE = "____________________________________________________________";
    private static final String HELP =
            "Commands:\n"
            + "  todo <desc>\n"
            + "  deadline <desc> /by <when>\n"
            + "  event <desc> /from <start> /to <end>\n"
            + "  list | mark <n> | unmark <n> | delete <n> | bye\n";

    private static final String logo =
        "__        __   _____    ___   __        __   _____    ___   \n" +
        "\\ \\      / /  | ____|  |_ _|  \\ \\      / /  | ____|  |_ _|  \n" +
        " \\ \\ /\\ / /   |  _|     | |    \\ \\ /\\ / /   |  _|     | |  \n" +
        "  \\ V  V /    | |___    | |     \\ V  V /    | |___    | |   \n" +
        "   \\_/\\_/     |_____|  |___|     \\_/\\_/     |_____|  |___|  \n";
    
    public static void main(String[] args) {
        Storage storage = new Storage("data", "data.txt");
        List<Task> tasksList = new ArrayList<>(storage.load());
        System.out.printf("(loaded %d %s)%n", tasksList.size(), tasksList.size() == 1 ? "task" : "tasks");
        System.out.println("Hello im");
        System.out.println(logo);
        System.out.println(LINE);
        System.out.println("What can I do for you?");
        System.out.println(HELP);

        Scanner sc = new Scanner(System.in);

        while (true) {
            String userInput = sc.nextLine().trim();
            if (userInput.isEmpty()) {
                continue;
            }

            String[] parts = userInput.split("\\s+", 2);
            String cmd = parts[0].toLowerCase(Locale.ROOT);
            String rest = parts.length > 1 ? parts[1].trim() : "";

            switch (cmd) {
            case "list":
                System.out.println("\t" + LINE);
                if (tasksList.isEmpty()) {
                    System.out.println("\t (empty)");
                } else {
                    System.out.println("\t Here are the tasks in your list:");
                    for (int i = 0; i < tasksList.size(); i++) {
                        System.out.println("\t " + (i + 1) + "." + tasksList.get(i));
                    }
                }
                System.out.println("\t" + LINE);
                break;

            case "todo":
                if (rest.isEmpty()) {
                    System.out.println("\tUsage: todo <description>");
                    break;
                }
                Task tTodo = new Todo(rest);
                tasksList.add(tTodo);
                storage.save(tasksList);
                printAdded(tTodo, tasksList.size());
                break;

            case "deadline":
                if (rest.isEmpty()) {
                    System.out.println("\tUsage: deadline <description> /by <when>");
                    break;
                }
                int byIdx = rest.toLowerCase(Locale.ROOT).indexOf("/by");
                if (byIdx == -1) {
                    System.out.println("\tUsage: deadline <description> /by <when>");
                    break;
                }
                String dDesc = rest.substring(0, byIdx).trim();
                String dBy = rest.substring(byIdx + 3).trim();
                if (dDesc.isEmpty() || dBy.isEmpty()) {
                    System.out.println("\tUsage: deadline <description> /by <when>");
                    break;
                }
                Task tDeadline = new Deadline(dDesc, dBy);
                tasksList.add(tDeadline);
                storage.save(tasksList);
                printAdded(tDeadline, tasksList.size());
                break;

            case "event":
                if (rest.isEmpty()) {
                    System.out.println("\tUsage: event <description> /from <start> /to <end>");
                    break;
                }
                String lower = rest.toLowerCase(Locale.ROOT);
                int fromIdx = lower.indexOf("/from");
                int toIdx = lower.indexOf("/to", fromIdx + 5);
                if (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx + 5) {
                    System.out.println("\tUsage: event <description> /from <start> /to <end>");
                    break;
                }
                String eDesc = rest.substring(0, fromIdx).trim();
                String eFrom = rest.substring(fromIdx + 5, toIdx).trim();
                String eTo = rest.substring(toIdx + 3).trim();
                if (eDesc.isEmpty() || eFrom.isEmpty() || eTo.isEmpty()) {
                    System.out.println("\tUsage: event <description> /from <start> /to <end>");
                    break;
                }
                Task tEvent = new Event(eDesc, eFrom, eTo);
                tasksList.add(tEvent);
                storage.save(tasksList);
                printAdded(tEvent, tasksList.size());
                break;

            case "mark":
                if (rest.isEmpty()) {
                    System.out.println("\tUsage: mark <number>");
                    break;
                }
                try {
                    int idx = Integer.parseInt(rest);
                    if (idx < 1 || idx > tasksList.size()) {
                        System.out.println("\tInvalid index: " + idx);
                        break;
                    }
                    Task task = tasksList.get(idx - 1);
                    task.mark();
                    storage.save(tasksList);
                    System.out.println("\tNice! I've marked this task as done:\n");
                    System.out.println("\t\t" + task + "\n");
                } catch (NumberFormatException e) {
                    System.out.println("\tPlease provide a valid number. Example: mark 2\n");
                }
                break;

            case "unmark":
                if (rest.isEmpty()) {
                    System.out.println("\tUsage: unmark <number>");
                    break;
                }
                try {
                    int idx = Integer.parseInt(rest);
                    if (idx < 1 || idx > tasksList.size()) {
                        System.out.println("\tInvalid index: " + idx);
                        break;
                    }
                    Task task = tasksList.get(idx - 1);
                    task.unmark();
                    storage.save(tasksList);
                    System.out.println("\tOK, I've marked this task as not done yet:\n");
                    System.out.println("\t\t" + task + "\n");
                } catch (NumberFormatException e) {
                    System.out.println("\tPlease provide a valid number. Example: unmark 2\n");
                }
                break;

            case "delete":
                if (rest.isEmpty()) {
                    System.out.println("\tUsage: delete <number>");
                    break;
                }
                try {
                    int idx = Integer.parseInt(rest);
                    if (idx < 1 || idx > tasksList.size()) {
                        System.out.println("\tInvalid index: " + idx);
                        break;
                    }
                    Task removed = tasksList.remove(idx - 1);
                    storage.save(tasksList);
                    System.out.println("\tNoted. I've removed this task:");
                    System.out.println("\t  " + removed);
                    printTaskCount(tasksList);
                    System.out.println();
                } catch (NumberFormatException e) {
                    System.out.println("\tPlease provide a valid number. Example: delete 3\n");
                }
                break;

            case "bye":
                System.out.println("\tBye. Hope to see you again soon!");
                System.exit(0);
                break;

            default:
                System.out.println("\tSorry, please use an appropriate command");
                System.out.println("\t" + HELP);
                break;
            }
        }
    }

    private static void printAdded(Task t, int size) {
        System.out.println("\t" + LINE);
        System.out.println("\t Got it. I've added this task:");
        System.out.println("\t   " + t);
        printTaskCount(size);
        System.out.println("\t" + LINE);
    }

    private static void printTaskCount(List<?> tasks) {
        printTaskCount(tasks.size());
    }

    private static void printTaskCount(int size) {
        System.out.printf("\t Now you have %d %s in the list.%n", size, size == 1 ? "task" : "tasks");
    }
}
