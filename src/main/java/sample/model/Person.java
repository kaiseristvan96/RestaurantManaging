package sample.model;

import javax.persistence.*;

/**
 * This class creates an individual person.
 */
@Entity
public class Person {
    /**
     * The Id of the person, automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * The name of the person, cannot be null.
     */
    @Column(name = "Name",nullable = false)
    private String name;

    /**
     * The time they checked-in.
     */
    @Column(name = "localDateTime")
    private String localDateTime;

    /**
     * The cost of their bought meals.
     */
    @Column(name = "Cost")
    private int cost;


    /**
     * The bought meals, concatenated to one String.
     */
    @Column(name = "SelectedMeals")
    private String selectedMeals;

    /**
     * Returns the attributes used in this class.
     * @return A String with the person's defined attributes.
     */
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", localDateTime='" + localDateTime + '\'' +
                ", cost=" + cost +
                ", selectedMeals='" + selectedMeals + '\'' +
                '}';
    }


    /**
     * Returns the bought meals concatenated to one String.
     * @return A String of bought meals.
     */
    public String getSelectedMeals() {
        return selectedMeals;
    }

    /**
     * Set the bought meals.
     * @param selectedMeals The meals bought by the person, in a String form.
     */
    public void setSelectedMeals(String selectedMeals) {
        this.selectedMeals = selectedMeals;
    }


    /**
     * An empty constructor used by the JPA.
     */
    public Person() {
    }


    /**
     * Returns the Id of the person.
     * @return A number, that represents the id of the person.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the person. Shouldn't be used, the id is auto generated.
     * @param id A number, the id of the person.
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Set the name of the person.
     * @param name A String, the name of the person.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the time the person checked-in, in String form.
     * @param localDateTime A String, the time the person checked-in.
     */
    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }


    /**
     * Set the cost of the meals the person bought.
     * @param cost An int, the cost of the meals.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Returns the name of the person.
     * @return A String, the name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the time they checked-in, in String form.
     * @return A String, the time the person checked-in.
     */
    public String getLocalDateTime() {
        return localDateTime;
    }


    /**
     * Returns the cost of the meals the person bought.
     * @return An int, the cost of the meals.
     */
    public int getCost() {
        return cost;
    }


    /**
     * The constructor to set-up a person.
     * @param name The name of the person, in String form.
     * @param localDateTime A String, the time they checked-in.
     * @param cost An int, the cost of their meals.
     * @param selectedMeals A String, the meals the person selected.
     */
    public Person(String name, String localDateTime, int cost, String selectedMeals) {
        this.name = name;
        this.localDateTime = localDateTime;
        this.cost = cost;
        this.selectedMeals = selectedMeals;
    }


}
