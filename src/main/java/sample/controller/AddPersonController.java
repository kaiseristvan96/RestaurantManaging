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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * This class adds a person with selected meals and check-in time to the database.
 */
public class AddPersonController implements Initializable {

    /**
     * TextField where tha name of the person is specified.
     */
    @FXML
    TextField addPersonTextField; //name

    /**
     * DatePicker where the check-in time is specified.
     */
    @FXML
    DatePicker addPersonDatePicker;

    /**
     *
     * TextField where the check-in time hours and minutes are specified.
     */
    @FXML
    TextField addPersonHHMM;

    /**
     * The main part of the TableView where the selectable meals are.
     */
    @FXML
    private TableView<Meal> addPersonTable;

    /**
     * The name part of the selectable meal in the TableView.
     */
    @FXML
    public TableColumn<Meal, String> addPersonName;

    /**
     * The cost part of the selectable meal in the TableView.
     */
    @FXML
    public TableColumn<Meal, Integer> addPersonCost;

    /**
     * The msin part of the selected meal in the TableView.
     */
    @FXML
    private TableView<Meal> addPersonTable1;

    /**
     * The name part of the selected meal in the TableView.
     */
    @FXML
    public TableColumn<Meal, String> addPersonName1;

    /**
     * The cost part of the selected meal in the TableView.
     */
    @FXML
    public TableColumn<Meal, Integer> addPersonCost1;

    /**
     * Used by the JPA.
     */
    private static EntityManagerFactory emf;

    /**
     * Used by the JPA.
     */
    private static EntityManager em;

    /**
     * At the start of the scene initialize the selectable meals, and it's table.
     * @param url Location or null.
     * @param rb Resource.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        addPersonRefreshed();
        Logger.info("Initializing database");
    }

    /**
     * Return to the main window.
     * @param event Clicking this button loads the main window scene.
     */
    @FXML
    private void backButtonClick(ActionEvent event){
       Logger.info("Going back to the main window");

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
        allMeals2.clear();

    }


    /**
     * This method adds the new person to the database using JPA.
     */
    @FXML
    private void writeToDatabase(){
       Logger.info("Writing to database");
        double sumCost = 0.0;
        String allFoods = "";
        for(int i = 0; i < items2.size();i++){
            sumCost += items2.get(i).getCost();
            allFoods += items2.get(i).getName()+";";
        }
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();

        addPerson(addPersonTextField.getText()
                ,addPersonDatePicker.getValue()+" "+addPersonHHMM.getText()
                ,(int)Math.round(sumCost),
                allFoods);
        em.close();
        emf.close();
    }

    /**
     * This method initializes and adds the new person to the database.
     * @param name String, the name of the person.
     * @param localDateTime String, the time the person checked-in.
     * @param cost Int, the cost of the selected meals.
     * @param selectedMeals String, the concatenated string of selected meals.
     */
    private static void addPerson(String name, String localDateTime, int cost, String selectedMeals){
       Logger.info("Adding a new person to the database");
        Person person = new Person(name, localDateTime,cost, selectedMeals);

        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
    }

    /**
     * Saving the data to the database.
     * @param event Clicking this button saves the data to the database.
     */
    @FXML
    private void saveAll(ActionEvent event){
      Logger.info("Saving data");
        writeToDatabase();
        allMeals2.clear();
        items2.clear();
        addPersonTextField.clear();
        addPersonDatePicker.setValue(null);
        addPersonHHMM.setText("hh:mm");
    }

    /**
     * Loading in the selectable meals to the TableView.
     */
    @FXML
    private void addPersonRefreshed(){
      Logger.info("Refreshing tables");

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(getClass().getResourceAsStream("/database/meals.xml")); //jó
            //XML Beolvasása


            LinkedList<Meal> allMeals = new LinkedList<>();


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

            addPersonName.setCellValueFactory(new PropertyValueFactory<>("name"));
            addPersonCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
            addPersonName1.setCellValueFactory(new PropertyValueFactory<>("name"));
            addPersonCost1.setCellValueFactory(new PropertyValueFactory<>("cost"));
            ObservableList<Meal> items2 = FXCollections.observableArrayList();

            for(int i = 0; i < allMeals.size(); i++){
                items2.add(allMeals.get(i));
            }
            addPersonTable.setItems(items2);


        } catch (ParserConfigurationException e) {
           Logger.error("ParserConfigurationException", new ParserConfigurationException());
        } catch (SAXException e) {
          Logger.error("SAXException", new SAXException(e));
        } catch (IOException e) {
          Logger.error("IOException", new IOException(e));
        }
    }

    /**
     * Helper variable.
     */
    LinkedList<Meal> allMeals2 = new LinkedList<>();
    /**
     * This is required for the TableView to show the selected meals.
     */
    ObservableList<Meal> items2 = FXCollections.observableArrayList();

    /**
     * Adds the selected meals from the selectable meals table to the selected meals table.
     * @param event Clicking this button adds the selected item from selectable meals table to selected meals table.
     */
    @FXML
    private void addMealsToTable1(ActionEvent event){

        items2.clear();
        Meal newMeal = addPersonTable.getSelectionModel().getSelectedItem();
        allMeals2.add(newMeal);

        for(int i = 0; i < allMeals2.size(); i++){
            items2.add(allMeals2.get(i));
        }
        addPersonTable1.setItems(items2);
        

    }


}

