package sample.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/**
 * <p>Main class.</p>
 *
 * @author Kaiser István
 * @version 1.0
 */
public class Main extends Application {
    /**
     * Initializing Logger for the Main class.
     */
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * This starts the application by opening the main menu.
     * @param primaryStage Main menu.
     */
    @Override
    public void start(Stage primaryStage){
        logger.info("Starting the Application");

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/mainStage.fxml"));
        } catch (IOException e) {
            logger.error("IOException", new IOException(e));
        }
        primaryStage.setTitle("Restaurant Managing");
        primaryStage.setScene(new Scene(root, 465, 255));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * The main method.
     * @param args Arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
