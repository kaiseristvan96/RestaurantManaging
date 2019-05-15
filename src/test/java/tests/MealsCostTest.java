package tests;

import org.junit.jupiter.api.Test;
import sample.model.Meal;
import static org.junit.jupiter.api.Assertions.*;

public class MealsCostTest {
    Meal meal = new Meal("pizza1",100);
    Meal meal2 = new Meal("pizza2",1000);
    Meal meal3 = new Meal("pizza2",0);
    @Test
    void taxTestWithHundred(){
        assertEquals(127,meal.costWithTax(meal));
    }

    @Test
    void taxTestWithThousand(){
        assertEquals(1270,meal2.costWithTax(meal2));
    }

    @Test
    void taxTestWithNull(){
        assertEquals(0,meal3.costWithTax(meal3));
    }


    @Test
    void taxTestFail(){
        assertNotEquals(126,meal.costWithTax(meal));
    }


    @Test
    void convertToEURTestHundred(){assertEquals(
            true
            ,(meal.costInEuro(meal) >= (meal.getCost()/323.51)-0.01 ) && (meal.costInEuro(meal) <= (meal.getCost()/323.51)+0.01 ));}

    @Test
    void convertToEURTestThousand(){assertEquals(
            true
            ,(meal2.costInEuro(meal2) >= (meal2.getCost()/323.51)-0.01 ) && (meal2.costInEuro(meal2) <= (meal2.getCost()/323.51)+0.01 ));}



    @Test
    void convertToEURNull(){assertEquals(
            true
            ,(meal3.costInEuro(meal3) >= (meal3.getCost()/323.51)-0.01 ) && (meal3.costInEuro(meal3) <= (meal3.getCost()/323.51)+0.01 ));}

    @Test
    void convertToEURTestFails(){assertEquals(
            false
            ,(meal.getCost()/323.51) == 0.30);}

}
