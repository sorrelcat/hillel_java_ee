package hillelee.restraunt;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.*;
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

    @Test
    public void classicAverageCaloriesByType() throws Exception {

        Map<DishType, Double> dishGroupsCalories = new HashMap<>();
        for(DishType dishType : DishType.values()) {
            Double allCalories = .0;
            Integer countDishes = 0;
            for (Dish dish : restaraunt.getMenu()
                 ) {
                if(dish.getType().equals(dishType)) {
                    countDishes ++;
                    allCalories += dish.getCalories();
                }
            }
            dishGroupsCalories.put(dishType, allCalories/countDishes);
        }

        for (Map.Entry<DishType, Double> dishGroup : dishGroupsCalories.entrySet()
             ) {
            System.out.println(dishGroup);
        }
    }

    @Test
    public void streamAverageCaloriesByType() throws Exception {

        Map<DishType, Double> dishGroupsCalories = restaraunt.getMenu().stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getCalories)));

        System.out.println(dishGroupsCalories);
    }

    @Test
    public void classicBioDishes() throws Exception {

        Map<DishType, List<String>> bioDishes = new HashMap<>();

        for (DishType dishType : DishType.values()
             ) {
            bioDishes.put(dishType, new ArrayList<>());
        }

        for (Dish dish : restaraunt.getMenu()
             ) {
            if(dish.getIsBio())
            {
                bioDishes.get(dish.getType()).add(dish.getName());
            }
        }

        System.out.println(bioDishes);
    }

    @Test
    public void streamBioDishes() throws Exception {

        Map<DishType, List<String>> bioDishesGroupList = restaraunt.getMenu().stream()
                .filter(dish -> dish.getIsBio())
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));

        System.out.println(bioDishesGroupList);
    }
}