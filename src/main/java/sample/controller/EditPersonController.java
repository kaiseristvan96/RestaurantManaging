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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.pmw.tinylog.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sample.model.Meal;
import sample.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class that let's the user edit a person's bought meals.
 */
public class EditPersonController implements Initializable {

    /**
     * Used by the JPA.
     */
    private static EntityManagerFactory emf;

    /**
     * Used by the JPA.
     */
    private static EntityManager em;

    /**
     * Searching in the database with SQL statements returns a list of elements stored here.
     */
    TypedQuery<Person> search;

    /**
     * Storing the returned SQL elements in a list.
     */
    List<Person> results;

    /**
     * TextField, this is the name the SQL statement will look for in the database.
     */
    @FXML
    TextField editPersonSearchField;

    /**
     * Label to inform the user about the cost of the currently selected meals.
     */
    @FXML
    Label editPersonCostOfCurrentMeals;

    /**
     * Main part of the TableView.
     */
    @FXML
    private TableView<Meal> editPersonTable;

    /**
     * The column of the TableView.
     */
    @FXML
    public TableColumn<Meal,String> editPersonName;


    /**
     * The column of the ListView.
     */
    @FXML
    private ListView<String> editPersonTable1;


    /**
     * Additional aiding variable.
     */
    LinkedList<Meal> allMeals = new LinkedList<>();

    /**
     * Setting up the table view.
     * @param url Location or null.
     * @param rb Resource.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
       Logger.info("Initializing tables");
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(getClass().getResourceAsStream("/database/meals.xml")); //jó

            NodeList nlst = doc.getElementsByTagName("meal");
            for(int i = 0; i < nlst.getLength(); i++){
                org.w3c.dom.Node node = nlst.item(i);
                if(node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                    Meal meal = new Meal();
                    Element element = (Element) node;
                    meal.setName(element.getElementsByTagName("name").item(0).getTextContent().trim());
                    meal.setCost(Integer.parseInt(element.getElementsByTagName("cost").item(0).getTextContent().trim()));

                    allMeals.add(meal);
                }
            }

            editPersonName.setCellValueFactory(new PropertyValueFactory<>("name"));
            ObservableList<Meal> items2 = FXCollections.observableArrayList();

            for(int i = 0; i < allMeals.size(); i++){
                items2.add(allMeals.get(i));
            }
            editPersonTable.setItems(items2);


        } catch (ParserConfigurationException e) {
       Logger.error("ParserConfigurationException", new ParserConfigurationException());
        } catch (SAXException e) {
      Logger.error("SAXException", new SAXException(e));
        } catch (IOException e) {
       Logger.error("IOException", new IOException(e));
        }
    }

    /**
     * Opens up the main window.
     * @param event Clicking a button opens the main window.
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
     * Get the specified person's data from database and fill the table view.
     * @param event Clicking the search button get's the required data.
     */
    @FXML
    private void editPersonSearch(ActionEvent event){
        searchInDatabase();
    }

    /**
     * Get the specified person's data from database and fill the table view.
     */
    private void searchInDatabase(){
        Logger.info("Searching...");
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
        search = em.createQuery(
                "SELECT s FROM Person s WHERE s.name ='"+editPersonSearchField.getText()+"'",Person.class);
        results = search.getResultList();
        Logger.info("Result(s) of search, picking the first item: " + results);
        ObservableList<String> allMealsCurrently = FXCollections.observableArrayList();
        String[] splitMeals = (results.get(0)).getSelectedMeals().split(";");
        for(int i = 0; i < splitMeals.length/*results.size()*/;i++) {
            allMealsCurrently.add(splitMeals[i]);
        }
        editPersonTable1.setItems(allMealsCurrently);
        editPersonCostOfCurrentMeals.setText((results.get(0).getCost()+" Ft"));
    }

    /**
     * Add the selected meal from the table view to the list view, doing this also updates the database.
     * @param event Clicking add button will update the database for the specific person.
     */
    @FXML
    private void addSelectedMeal(ActionEvent event){
        Logger.info("Filling tables!");
        Meal additionalMealsToAdd;
        additionalMealsToAdd = editPersonTable.getSelectionModel().getSelectedItem();
        em.getTransaction().begin();
        search = em.createQuery(
                "SELECT s FROM Person s WHERE s.name ='"+editPersonSearchField.getText()+"'",Person.class);
        results = search.getResultList();
        results.get(0).setCost(results.get(0).getCost()+additionalMealsToAdd.getCost());
        results.get(0).setSelectedMeals(results.get(0).getSelectedMeals()+additionalMealsToAdd.getName()+";");
        em.getTransaction().commit();
        searchInDatabase();
    }


    /**
     * Removes the selected meal from the person's list of meals, this also updates the database.
     */
    @FXML
    private void editPersonRemove(){
        Logger.info("Removing data");
        String mealToRemove;
        mealToRemove = editPersonTable1.getSelectionModel().getSelectedItem();
        Logger.info("Removing " + mealToRemove);
        em.getTransaction().begin();
        search = em.createQuery(
                "SELECT s FROM Person s WHERE s.name ='"+editPersonSearchField.getText()+"'",Person.class);
        results = search.getResultList();
        int costOfSpecificMeal=0;
        for(int i = 0; i < allMeals.size();i++){
            if(allMeals.get(i).getName().equals(mealToRemove)){
                costOfSpecificMeal = allMeals.get(i).getCost();
            }
        }
        results.get(0).setCost(results.get(0).getCost()-costOfSpecificMeal);
        String listOfMeals = results.get(0).getSelectedMeals();
        listOfMeals = listOfMeals.replaceFirst(mealToRemove+";","");
        results.get(0).setSelectedMeals(listOfMeals);
        em.getTransaction().commit();
        searchInDatabase();
    }

}
