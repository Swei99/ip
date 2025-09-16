package weiweibot.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import weiweibot.WeiWeiBot;

/**
 * Starts the JavaFX user interface for WeiWeiBot.
 * <p>
 * This class loads the main FXML layout, configures the primary window,
 * connects the UI controller to the chatbot, and shows an initial greeting.
 * Use {@code Application.launch(Main.class, args)} to run the app.
 */
public class Main extends Application {

    private WeiWeiBot weiBot = new WeiWeiBot();
    private Image introImage = new Image(this.getClass().getResourceAsStream("/images/comet.png"));

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("WeiWeiBot");

            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setBot(weiBot);
            stage.show();
            fxmlLoader.<MainWindow>getController().displayWelcome(
                "Hello! I'm WeiWeiBot\nIf you do not keep track of your tasks the comet will destroy the world"
                + "\nType 'help' for more commands", introImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
