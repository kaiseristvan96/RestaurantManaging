package tests;

import org.junit.jupiter.api.Test;
import sample.model.Meal;
import static org.junit.jupiter.api.Assertions.*;

public class MealsCostTest {
    Meal meal = new Meal("pizza1",100);
    @Test
    void TaxTest(){
        assertEquals(127,meal.costWithTax(meal));
    }

    @Test
    void TaxTestFail(){
        assertNotEquals(126,meal.costWithTax(meal));
    }


    @Test
    void ConvertToEURTest(){assertEquals(
            true
            ,(meal.costInEuro(meal) >= (meal.getCost()/323.51)-0.01 ) && (meal.costInEuro(meal) <= (meal.getCost()/323.51)+0.01 ));}

    @Test
    void ConvertToEURTestFails(){assertEquals(
            false
            ,(meal.getCost()/323.51) == 0.30);}

}
