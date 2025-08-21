import java.util.Locale;
import java.util.Scanner;

public class weiwei {
    public static void main(String[] args) {
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

            switch (userInput.toLowerCase(Locale.ROOT)) {
            case "bye":     
                System.out.println("\tBye. Hope to see you again soon!");
                System.exit(0);
            default:
                System.out.println("\t"+userInput);
            }
        }

    }
}