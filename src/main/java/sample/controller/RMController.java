package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;


/**
 * This controller represents the main menu of the program.
 */
public class RMController {
    /**
     * Initializing Logger for the RMController class.
     */
    private static final Logger logger = LogManager.getLogger(RMController.class);

    /**
     * This opens the Add Person Window.
     * @param event A button click.
     */
    @FXML
    private void addPersonClick(ActionEvent event){
        logger.info("Opening Add Person Window!");
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/addPerson.fxml"));

            stage.setTitle("Add Person");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            logger.error("IOException", new IOException(e));
        }

    }

    /**
     * This opens the Edit Person Window.
     * @param event A button click.
     */
    @FXML
    private void editPersonClick(ActionEvent event){
        logger.info("Opening Edit Person Window!");
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/editPerson.fxml"));

            stage.setTitle("Edit Person");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            logger.error("IOException", new IOException(e));
        }

    }

    /**
     * This opens the Delete Person Window.
     * @param event A button click.
     */
    @FXML
    private void delPersonClick(ActionEvent event){
        logger.info("Opening Delete Person Window!");
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/delPerson.fxml"));

            stage.setTitle("Delete Person");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            logger.error("IOException", new IOException(e));
        }

    }

    /**
     * This opens the View Person Window.
     * @param event A button click.
     */
    @FXML
    private void viewPersonClick(ActionEvent event){
        logger.info("Opening View Person Window!");
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/viewPerson.fxml"));

            stage.setTitle("View Person List");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            logger.error("IOException", new IOException(e));
        }

    }


}
