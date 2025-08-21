import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class weiwei {
    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();

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

            switch (userInput) {
            case "list":
                int count = 1;
                for (String item : arrayList) {
                    System.out.println("\t" + count + ". " + item);
                    count++;
                }
                break;
            case "bye":
                System.out.println("\tBye. Hope to see you again soon!");
                System.exit(0);
            default:
                System.out.println("\tadded: "+userInput);
                arrayList.add(userInput);
            }
        }
    }
}