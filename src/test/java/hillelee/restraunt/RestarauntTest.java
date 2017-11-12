package hillelee.restraunt;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class RestarauntTest {

    static final Integer LOW_CALORIES = 500;

    List<Dish> restrauntMenu = ImmutableList.of(new Dish("CAESAR", 350, true, DishType.CHICKEN),
            new Dish("MARGARITHA", 1500, true, DishType.VEGETABLES),
            new Dish("HUMUS", 500, true, DishType.VEGETABLES),
            new Dish("SHAURMA", 950, false, DishType.CHICKEN),
            new Dish("CUTLET", 350, true, DishType.BEEF),
            new Dish("FO BO", 300, false, DishType.BEEF),
            new Dish("MEATBALLS", 450, false, DishType.CHICKEN),
            new Dish("SALAD", 200, true, DishType.VEGETABLES),
            new Dish("BARBECUE", 1200, false, DishType.BEEF),
            new Dish("FRENCH FRIES", 550, false, DishType.VEGETABLES)
    );

    Restaraunt restaraunt = new Restaraunt(restrauntMenu);

    @Test
    public void classicLowCalories() throws Exception {
        for (Dish dish : restaraunt.getMenu()
                ) {
            if (dish.getCalories() <= LOW_CALORIES) {
                System.out.println(dish.toString());
            }
        }
    }

    @Test
    public void streamLowCalories() throws Exception {
        restaraunt.getMenu().stream()
                .filter(dish -> dish.getCalories() <= LOW_CALORIES)
                .forEach(System.out::println);
    }

    @Test
    public void classicTop3HighCalories() throws Exception {
        // List<Dish> sortedMenu = Collections.sort(restaraunt.getMenu());
    }
}