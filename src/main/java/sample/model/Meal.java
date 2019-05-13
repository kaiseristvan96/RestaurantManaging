package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class defines the selectable meals.
 */
public class Meal {
    /**
     * The name of the specific meal.
     */
    private SimpleStringProperty name;

    /**
     * The cost of the specific meal.
     */
    private SimpleIntegerProperty cost;


    /**
     * Returns the cost of the meals after taxes.
     * @param meal The meal they bought.
     * @return A double, the cost after taxes.
     */
    public double costWithTax(Meal meal){
        return meal.getCost()*1.27;
    }

    /**
     * Returns the cost of the meal in Euros.
     * @param meal The meals the person bought.
     * @return A double, the cost of the meal in Euros.
     */
    public double costInEuro(Meal meal){
        return meal.getCost()/323.51;
    }

    /**
     * Empty constructor, used by JPA.
     */
    public Meal() {
    }

    /**
     * A constructor to set-up one meal with it's name and cost.
     * @param name The name of the meals, String.
     * @param cost The cost of the meal, int.
     */
    public Meal(String name, int cost) {
        this.name = new SimpleStringProperty(name);
        this.cost = new SimpleIntegerProperty(cost);
    }

    /**
     * Returns the name of the meal.
     * @return A String, the name of the meal.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Set the name of the meal.
     * @param name A String, the name of the meal.
     */
    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    /**
     * Returns the cost of the meal.
     * @return An int, the cost of the meal.
     */
    public int getCost() {
        return cost.get();
    }

    /**
     * Set the cost of the meal.
     * @param cost An int, the cost of the meal.
     */
    public void setCost(int cost) {
        this.cost = new SimpleIntegerProperty(cost);
    }

    /**
     * Returns the attributes used by the class in a String form.
     * @return A String with the set attributes.
     */
    @Override
    public String toString() {
        return "Meal{" +
                "name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
