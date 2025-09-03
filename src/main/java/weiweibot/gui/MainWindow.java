package weiweibot.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import weiweibot.WeiWeiBot;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private WeiWeiBot bot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Taki.png"));
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/Mitsuha.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Bot instance */
    public void setBot(WeiWeiBot weiBotInstance) {
        bot = weiBotInstance;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Bot's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bot.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBotDialog(response, botImage)
        );
        userInput.clear();

        if (bot.shouldExit()) {
            Platform.exit();
        }
    }

    public void displayWelcome(String welcomeMessage, Image botImage) {
        dialogContainer.getChildren().add(
            DialogBox.getBotDialog(welcomeMessage, botImage)
        );
    }
}
