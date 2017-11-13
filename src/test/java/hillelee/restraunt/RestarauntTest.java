package hillelee.restraunt;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RestarauntTest {

    static final Integer LOW_CALORIES = 500;

    List<Dish> restrauntMenu = ImmutableList.of(new Dish("CAESAR", 350, true, DishType.CHICKEN),
            new Dish("MARGARITHA", 1100, true, DishType.VEGETABLES),
            new Dish("HUMUS", 500, true, DishType.VEGETABLES),
            new Dish("SHAURMA", 950, false, DishType.CHICKEN),
            new Dish("CUTLET", 350, true, DishType.BEEF),
            new Dish("FO BO", 300, false, DishType.BEEF),
            new Dish("MEATBALLS", 450, false, DishType.CHICKEN),
            new Dish("SALAD", 200, true, DishType.VEGETABLES),
            new Dish("BARBECUE", 1500, false, DishType.BEEF),
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
        List<Dish> mutableMenu = new ArrayList<>();
        mutableMenu.addAll(restaraunt.getMenu());

        Collections.sort(mutableMenu, Comparator.comparing(Dish::getCalories).reversed());
        for (int i = 0; i < 3; i++) {
            System.out.println(mutableMenu.get(i));
        }
    }

    @Test
    public void streamTop3HighCalories() throws Exception {
        restaraunt.getMenu().stream()
                .sorted(Comparator.comparing(Dish::getCalories).reversed())
                .peek(System.out::println)
                .limit(3)
                .collect(Collectors.toList());
    }

    @Test
    public void classicSortBioAlphabet() throws Exception {

        List<Dish> mutableMenu = new ArrayList<>();
        mutableMenu.addAll(restaraunt.getMenu());

        Collections.sort(mutableMenu, Comparator.comparing(Dish::getIsBio).thenComparing(Dish::getName));

        for (Dish dish : mutableMenu
             ) {
            System.out.println(dish.toString());
        }
    }

    @Test
    public void streamSortBioAlphabet() throws Exception {

        restaraunt.getMenu().stream()
                .sorted(Comparator.comparing(Dish::getIsBio).thenComparing(Dish::getName))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }
}