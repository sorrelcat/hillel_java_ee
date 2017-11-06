package hillelee.pear;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PearSelector {

    interface PearPredicate extends Predicate<Pear> {

        @Override
        boolean test(Pear pear);
    }

    class YellowPredicate implements PearPredicate {

        @Override
        public boolean test(Pear pear) {
            return pear.getColor().equals("YELLOW");
        }

    }

    class LightPredicate implements PearPredicate {

        @Override
        public boolean test(Pear pear) {
            return pear.getWeight() <= 50;
        }
    }

    public static Optional<Pear> getHeaviest(List<Pear> pears) {

        return pears.stream()
                .max(Comparator.comparing(Pear::getWeight)
                );
    }

    public static List<Pear> filterHeavy(List<Pear> pears, Integer weight) {

        return new ArrayList<>(pears.stream()
                .filter(pear -> pear.getWeight() <= weight)
                .collect(Collectors.toList()));
    }

    public static List<Pear> filterYellow(List<Pear> pears, String color) {
        return new ArrayList<>(pears.stream()
                .filter(pear -> pear.getColor().equals(color))
                .collect(Collectors.toList()));
    }

    public static List<Pear> filter(List<Pear> pears, PearPredicate predicate) {
        return new ArrayList<Pear>(pears.stream()
        .filter(predicate)
        .collect(Collectors.toList()));
    }

}
