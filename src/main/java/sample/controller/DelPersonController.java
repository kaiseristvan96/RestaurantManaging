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
import org.pmw.tinylog.Logger;
import sample.model.Person;


import javax.persistence.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class lets the user delete a person from the database.
 */
public class DelPersonController implements Initializable {

    /**
     * Main part of the TableView.
     */
    @FXML
    private TableView<Person> delPersonTable;

    /**
     * Part of the TableView, displays the name of the person.
     */
    @FXML
    public TableColumn<Person, String> delPersonName;

    /**
     * Part of the TableView, displays the check-in time for the person.
     */
    @FXML
    public TableColumn<Person, String> delPersonDate;

    /**
     * Part of the TableView, displays the cost of the selected meals.
     */
    @FXML
    public TableColumn<Person, Integer> delPersonCost;

    /**
     * Part of the TableView, displays the name of the selected meals.
     */
    @FXML
    public TableColumn<Person, String> delPersonMeals;


    /**
     * Part of the JPA.
     */
    private static EntityManagerFactory emf;
    /**
     * Part of the JPA.
     */
    private static EntityManager em;

    /**
     * Initializes the TableView, sets it up with data.
     * @param url Location or null.
     * @param rb Resource.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        Logger.info("Initializing data");
        loadTable();

    }

    /**
     * Helper variable for the SQL 'select' statements.
     */
    TypedQuery<Person> search;

    /**
     * Helper variable.
     */
    List<Person> results;

    /**
     * This method gets the required data from the database.
     */
    private void loadTable(){
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
        search = em.createQuery("SELECT s FROM Person s", Person.class);
        results = search.getResultList();

        delPersonName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        delPersonDate.setCellValueFactory(new PropertyValueFactory<>("localDateTime"));
        delPersonCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        delPersonMeals.setCellValueFactory(new PropertyValueFactory<>("SelectedMeals"));

        ObservableList<Person> items = FXCollections.observableArrayList();
        for(int i = 0; i < results.size(); i++){
            items.add(new Person(
                    results.get(i).getName(),
                    results.get(i).getLocalDateTime(),
                    results.get(i).getCost(),
                    results.get(i).getSelectedMeals()
            ));
        }
        delPersonTable.setItems(items);
        em.close();
        emf.close();
    }

    /**
     * This method loads in the main window scene.
     * @param event Clicking this button loads in the main window scene.
     */
    @FXML
    private void backButtonClick(ActionEvent event){
        Logger.info("Returning to main window!");
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainStage.fxml"));

            stage.setTitle("Restaurant Managing");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
          Logger.error("IOException", new IOException(e));
        }

    }

    /**
     * This method removes the specified person from the database.
     * @param event Clicking this button will remove the selected person from the database.
     */
    @FXML
    private void deleteSelectedPersonButton(ActionEvent event){
       Logger.info("Deleting selected item from database!");
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();


        em.getTransaction().begin();
        String selectedPersonName = delPersonTable.getSelectionModel().getSelectedItem().getName();
        TypedQuery<Person> deleteById = em.createQuery(
                "SELECT s FROM Person s WHERE s.name ='"+selectedPersonName+"'",Person.class);

        em.remove(deleteById.getResultList().get(0));
        em.getTransaction().commit();

        em.close();
        emf.close();
        loadTable();
    }




}
