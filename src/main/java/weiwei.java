import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class weiwei {
    public static void main(String[] args) {
        List<Task> tasksList = new ArrayList<>();

        String logo =
        "__        __   _____    ___   __        __   _____    ___   \n" +
        "\\ \\      / /  | ____|  |_ _|  \\ \\      / /  | ____|  |_ _|  \n" +
        " \\ \\ /\\ / /   |  _|     | |    \\ \\ /\\ / /   |  _|     | |  \n" +
        "  \\ V  V /    | |___    | |     \\ V  V /    | |___    | |   \n" +
        "   \\_/\\_/     |_____|  |___|     \\_/\\_/     |_____|  |___|  \n" +
        "___________________________________________________________\n";

        System.out.println("Hello im \n" + logo + "\nWhat can I do for you?\n");
        Scanner sc = new Scanner(System.in);

        while (true) {
            String userInput = sc.nextLine().trim();
            String[] parts = userInput.split("\\s+", 2);
            String cmd = parts[0];
            String rest = parts.length > 1 ? parts[1].trim() : "";

            switch (cmd) {
                case "list":
                    if (tasksList.isEmpty()) {
                        System.out.println("\tThe list is empty");
                        break;
                    }
                for (int i = 0; i < tasksList.size(); i++) {
                    System.out.printf("\t%d.%s%n", i + 1, tasksList.get(i));
                }
                System.out.println("\n");
                break;

                case "todo": {
                    if (rest.isEmpty()) { System.out.println("\tUsage: todo <description>"); break; }
                    Task t = new Todo(rest);
                    tasksList.add(t);

                    System.out.println("\tGot it. I've added this task: \n\t  " + t.toString());
                    int n = tasksList.size();
                    System.out.printf("\tNow you have %d tasks in the list.\n\n", n);
                    break;
                }

                case "deadline": {
                    if (rest.isEmpty()) {
                        System.out.println("\tUsage: deadline <description> /by <when>");
                        break;
                    }

                    // Simpler parsing without regex or split
                    String lower = rest;
                    int byIdx = lower.indexOf("/by");
                    if (byIdx == -1) {
                        System.out.println("\tUsage: deadline <description> /by <when>");
                        break;
                    }

                    String desc = rest.substring(0, byIdx).trim();
                    String by   = rest.substring(byIdx + 3).trim(); // 3 == "/by".length()

                    if (desc.isEmpty() || by.isEmpty()) {
                        System.out.println("\tUsage: deadline <description> /by <when>");
                        break;
                    }

                    Task t = new Deadline(desc, by);
                    tasksList.add(t);

                    System.out.println("\tGot it. I've added this task: \n\t  " + t.toString());
                    int n = tasksList.size();
                    System.out.printf("\tNow you have %d tasks in the list.\n\n", n);
                    break;
                }

                case "event": {
                    if (rest.isEmpty()) { 
                        System.out.println("\tUsage: event <description> /from <start> /to <end>");
                        break; 
                    }

                    String lower = rest;
                    int fromIdx = lower.indexOf("/from");
                    int toIdx   = lower.indexOf("/to", fromIdx + 5); // ensure /to comes after /from

                    if (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx + 5) {
                        System.out.println("\tUsage: event <description> /from <start> /to <end>");
                        break;
                    }

                    String desc = rest.substring(0, fromIdx).trim();
                    String from = rest.substring(fromIdx + 5, toIdx).trim(); // 5 = "/from".length()
                    String to   = rest.substring(toIdx + 3).trim();          // 3 = "/to".length()

                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        System.out.println("\tUsage: event <description> /from <start> /to <end>");
                        break;
                    }

                    Task t = new Event(desc, from, to);
                    tasksList.add(t);

                    System.out.println("\tGot it. I've added this task: \n\t  " + t.toString());
                    int n = tasksList.size();
                    System.out.printf("\tNow you have %d tasks in the list.\n\n", n);
                    break;
                }

                case "mark": {
                    if (parts.length < 2) { System.out.println("\tUsage: mark <number>"); break; }
                    try {
                        int index = Integer.parseInt(parts[1].trim());
                            if (index < 1 || index > tasksList.size()) {
                                System.out.println("\tInvalid index: " + index);
                                break;
                            }
                        Task task = tasksList.get(index - 1);
                        task.mark();

                        System.out.println("\tNice! I've marked this task as done:\n");
                        System.out.println("\t\t" + task.toString() + "\n"); // e.g. [x] item 2
                    } catch (NumberFormatException e) {
                        System.out.println("\tPlease provide a valid number. Example: mark 2\n");
                    }
                    break;
                }

                case "unmark": {
                    if (parts.length < 2) { System.out.println("\tUsage: unmark <number>"); break; }
                    try {
                        int index = Integer.parseInt(parts[1].trim());
                        if (index < 1 || index > tasksList.size()) {
                            System.out.println("\tInvalid index: " + index);
                            break;
                        }
                        Task task = tasksList.get(index - 1);
                        task.unmark();

                        System.out.println("\tOK, I've marked this task as not done yet:\n");
                        System.out.println("\t\t" + task.toString() + "\n"); // e.g. [ ] item 2
                    } catch (NumberFormatException e) {
                        System.out.println("\tPlease provide a valid number. Example: unmark 2\n");
                    }
                    break;
                }


                case "bye": {
                    System.out.println("\tBye. Hope to see you again soon!");
                    System.exit(0);
                }

                default: {
                    System.out.println("\tadded: "+userInput);
                    tasksList.add(new Task(userInput));
                }
            }
        }
    }
}