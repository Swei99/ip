import java.util.Locale;
import java.util.Scanner;
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

            switch (cmd) {
                case "list":
                    if (tasksList.isEmpty()) {
                        System.out.println("\tThe list is empty");
                        break;
                    }
                for (int i = 0; i < tasksList.size(); i++) {
                    System.out.printf("\t%d.%s%n", i + 1, tasksList.get(i));
                }
                break;

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
                        System.out.println("\tNice! I've marked this task as done:");
                        System.out.println("\t\t" + task.toString()); // e.g. [x] item 2
                    } catch (NumberFormatException e) {
                        System.out.println("\tPlease provide a valid number. Example: mark 2");
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
                        System.out.println("\tOK, I've marked this task as not done yet:");
                        System.out.println("\t\t" + task.toString()); // e.g. [ ] item 2
                    } catch (NumberFormatException e) {
                        System.out.println("\tPlease provide a valid number. Example: unmark 2");
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