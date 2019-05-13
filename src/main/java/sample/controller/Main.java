package sample.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pmw.tinylog.Logger;


import java.io.IOException;


/**
 * <p>Main class.</p>
 *
 * @author Kaiser István
 * @version 1.0
 */
public class Main extends Application {

    /**
     * This starts the application by opening the main menu.
     * @param primaryStage Main menu.
     */
    @Override
    public void start(Stage primaryStage){
      Logger.info("Starting the Application");

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/mainStage.fxml"));
        } catch (IOException e) {
       Logger.error("IOException", new IOException(e));
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
