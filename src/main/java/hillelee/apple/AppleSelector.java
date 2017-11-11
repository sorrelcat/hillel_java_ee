package hillelee.apple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by JavaEE on 28.10.2017.
 */
public class AppleSelector {
    public static Optional<Apple> getHeaviest(List<Apple> apples) {
        Apple heaviest = null;
        for (Apple apple: apples
             ) {
            if(heaviest == null || apple.getWeight() > heaviest.getWeight()) { // first null, then .getWeight - for !NullPointerException
                heaviest = apple;
            }
        }

        return Optional.ofNullable(heaviest); // if .of - may be NullPointerException
    }

    public static List<Apple> filterHeavy(List<Apple> apples, Integer weight) {
        List<Apple> result = new ArrayList<>();
        for(Apple apple : apples) {
            if(apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterByColor(List<Apple> apples, String color) {
        List<Apple> result = new ArrayList<>();
        for(Apple apple : apples) {
            if(apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filter(List<Apple> apples, ApplePredicate predicate) {
        List<Apple> result = new ArrayList<>();
        for(Apple apple : apples) {
            if(predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static <T> List<T> filter(List<T> items, AnyPredicate predicate) {
        List<T> result = new ArrayList<>();
        for(T item : items) {
            if(predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }


}

interface AnyPredicate<T> { // for any list
    Boolean test(T apple);
}

interface ApplePredicate {
    Boolean test(Apple apple);
}

class ColorPredicate implements /*ApplePredicate*/ AnyPredicate<Apple> {
    @Override
    public Boolean test(Apple apple) {
        return apple.getColor().equals("GREEN");
    }
}

class HeavyPredicate implements ApplePredicate {

    @Override
    public Boolean test(Apple apple) {
        return apple.getWeight() > 120;
    }
}
