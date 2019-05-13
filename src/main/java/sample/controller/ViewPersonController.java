package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.model.CurrentStatistics;
import sample.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class let's the user see all the database items in one window.
 */
public class ViewPersonController implements Initializable {
    /**
     * Initializing the Logger for the ViewPersonController class.
     */
    private static final Logger logger = LogManager.getLogger(ViewPersonController.class);

    /**
     * This method loads the main window scene.
     * @param event Clicking this button will take the user back to the main window.
     */
    @FXML
    private void backButtonClick(ActionEvent event){
        logger.info("Opening the main Window!");
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainStage.fxml"));

            stage.setTitle("Restaurant Managing");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            logger.error("IOException", new IOException(e));
        }

    }

    /**
     * The main part of the TableView where the people are.
     */
    @FXML
    private TableView<CurrentStatistics> viewPersonTable;

    /**
     * Part of the TableView, showing the name of the person.
     */
    @FXML
    public TableColumn<CurrentStatistics, String> viewPersonName;

    /**
     * Part of the TableView, showing the check-in time for the person.
     */
    @FXML
    public TableColumn<CurrentStatistics, String> viewPersonDate;

    /**
     * Part of the TableView, showing the name of the meals the person bought.
     */
    @FXML
    public TableColumn<CurrentStatistics, String> viewPersonMeals;

    /**
     * Part of the TableView, showing the cost of the selected meals in HUF after taxes..
     */
    @FXML
    public TableColumn<CurrentStatistics, Integer> viewPersonCostHUF;

    /**
     * Part of the TableView, showing the cost of the selected meals in Euros.
     */
    @FXML
    public TableColumn<CurrentStatistics, Integer> viewPersonCostEUR;


    /**
     * The method that returns the cost of the selected meals after taxes in HUF.
     * @param meal The meals the person bought.
     * @return Int, the cost of the meal after taxes rounded to the nearest integer.
     */
    private int costWithTax(Person meal){
        return (int)Math.round(meal.getCost()*1.27);
    }

    /**
     * Return the cost of the selected meal in Euros.
     * @param meal The selected meal.
     * @return Double, the cost of the selected meal in Euros.
     */
    private double costInEuro(Person meal){
        return meal.getCost()/323.51;
    }

    /**
     * Used by the JPA.
     */
    private static EntityManagerFactory emf;

    /**
     * Used by the JPA.
     */
    private static EntityManager em;

    /**
     * At the start of the scene this method initializes the TableView and sets it up with data from the database.
     * @param url Location or null.
     * @param rb Resource.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        logger.info("Initialization!");
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
        TypedQuery<Person> search = em.createQuery("SELECT s FROM Person s", Person.class);
        List<Person> results = search.getResultList();

        viewPersonName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        viewPersonDate.setCellValueFactory(new PropertyValueFactory<>("Check_in"));
        viewPersonCostHUF.setCellValueFactory(new PropertyValueFactory<>("CostHUF"));
        viewPersonCostEUR.setCellValueFactory(new PropertyValueFactory<>("CostEUR"));
        viewPersonMeals.setCellValueFactory(new PropertyValueFactory<>("Meals"));

        ObservableList<CurrentStatistics> items = FXCollections.observableArrayList();
        for(int i = 0; i < results.size(); i++){
            items.add(new CurrentStatistics(
                    results.get(i).getName(),
                    results.get(i).getLocalDateTime(),
                    results.get(i).getSelectedMeals(),
                    costWithTax(results.get(i)),
                    costInEuro(results.get(i))
            ));
        }
        viewPersonTable.setItems(items);

        em.close();
        emf.close();
    }
}
