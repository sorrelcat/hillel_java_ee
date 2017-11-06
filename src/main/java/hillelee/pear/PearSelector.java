package hillelee.pear;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class PearSelector {

    interface PearPredicate extends Predicate<Boolean> {
        Boolean test(Pear pear);
    }

    class YellowPredicate implements PearPredicate {

        @Override
        public Boolean test(Pear pear) {
            return pear.getColor().equals("YELLOW");
        }
    }

    class LightPredicate implements PearPredicate {

        @Override
        public Boolean test(Pear pear) {
            return pear.getWeight() <= 50;
        }
    }

    public static Optional<Pear> getHeaviest(List<Pear> pears) {

        return pears.stream()
                .max(Comparator.comparing(Pear::getWeight)
                );
    }

    public static List<Pear> filterHeavy(List<Pear> pears, Integer weight) {

        List<Pear> result = new ArrayList<>();
        result.addAll(pears.stream().allMatch(new Predicate<Pear>() {
            @Override
            public boolean test(Pear pear) {
                return new LightPredicate(pear);
            }
        }));
    }

    /*
    public static List<Apple> filterHeavy(List<Apple> apples, Integer weight) {
        List<Apple> result = new ArrayList<>();
        for(Apple apple : apples) {
            if(apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }
     */

}
