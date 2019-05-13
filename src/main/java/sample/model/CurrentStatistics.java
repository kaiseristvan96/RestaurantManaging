package sample.model;

/**
 * Used in creating the primary results for the program.
 */
public class CurrentStatistics {
    /**
     * The name of the customer.
     */
    private String Name;

    /**
     * The time the customer checked-in.
     */
    private String Check_in;

    /**
     * The name of the bought meals concatenated.
     */
    private String Meals;


    /**
     * The cost of the bought meals in Hungarian Forints.
     */
    private int CostHUF;

    /**
     * The cost of the bought meals in Euros.
     */
    private double CostEUR;

    /**
     * The constructor for the class.
     * @param name The name of the person.
     * @param check_in The time the customer checked-in.
     * @param meals The name of the bought meals concatenated.
     * @param costHUF The cost of the bought meals in Hungarian Forints.
     * @param costEUR The cost of the bought meals in Euros.
     */
    public CurrentStatistics(String name, String check_in, String meals, int costHUF, double costEUR) {
        Name = name;
        Check_in = check_in;
        Meals = meals;
        CostHUF = costHUF;
        CostEUR = costEUR;
    }

    /**
     * Empty constructor required by the JPA.
     */
    public CurrentStatistics() {
    }

    /**
     * Returns the name of the person.
     * @return The person's name.
     */
    public String getName() {
        return Name;
    }

    /**
     * Set the name of the person.
     * @param name The name of the person needed to be set.
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Returns the time they checked-in.
     * @return The time in String format.
     */
    public String getCheck_in() {
        return Check_in;
    }

    /**
     * Set the time they checked in.
     * @param check_in The time in String format.
     */
    public void setCheck_in(String check_in) {
        Check_in = check_in;
    }

    /**
     * Returns the bought meals concatenated to one String.
     * @return A String of bought meals.
     */
    public String getMeals() {
        return Meals;
    }

    /**
     * Set the bought meals.
     * @param meals The meals bought by the person, in a String form.
     */
    public void setMeals(String meals) {
        Meals = meals;
    }

    /**
     * Returns the cost of the bought meals in Hungarian Forints.
     * @return A number representing  the cos of the meal.
     */
    public int getCostHUF() {
        return CostHUF;
    }

    /**
     * Set the cost of the bought meals, in Hungarian Forints.
     * @param costHUF An integer that represents the cost.
     */
    public void setCostHUF(int costHUF) {
        CostHUF = costHUF;
    }

    /**
     * Returns the cost of the bought meals in Euros.
     * @return A double that represents the cost of the meals, in Euros.
     */
    public double getCostEUR() {
        return CostEUR;
    }

    /**
     * Set the cost of the meals, in Euro.
     * @param costEUR A double that represents the cost of the meals, in Euros.
     */
    public void setCostEUR(double costEUR) {
        CostEUR = costEUR;
    }

    /**
     * Returns the attributes used in the class in a String form.
     * @return A String with the attributes.
     */
    @Override
    public String toString() {
        return "CurrentStatistics{" +
                "Name='" + Name + '\'' +
                ", Check_in='" + Check_in + '\'' +
                ", Meals='" + Meals + '\'' +
                ", CostHUF=" + CostHUF +
                ", CostEUR=" + CostEUR +
                '}';
    }
}
