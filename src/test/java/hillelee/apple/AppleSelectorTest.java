package hillelee.apple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by JavaEE on 28.10.2017.
 */
public class AppleSelectorTest {

    @Test
    public void selectHeaviest () throws Exception {
        List<Apple> apples = ImmutableList.of(new Apple("RED", 100),
                new Apple("RED", 120),
                new Apple("GOLD", 40),
                new Apple("GREEN", 60),
                new Apple("GOLD", 150),
                new Apple("RED", 10));

        Optional<Apple> mayBeHeaviest = AppleSelector.getHeaviest(apples);

        if(mayBeHeaviest.isPresent()) {
            Apple heaviest = mayBeHeaviest.get();
            assertThat(heaviest.getWeight(), is(150));
        }
        else {
            fail();
        }
    }

    @Test
    public void selectHeaviestFromEmptyList() throws Exception {
        List<Apple> apples = ImmutableList.of();

        Optional<Apple> mayBeApple = AppleSelector.getHeaviest(apples); // .var
        if(mayBeApple.isPresent()) {
            fail(); // 'cause is list of nulls
        }
    }

    @Test
    public void sort() throws Exception {
        List<Apple> apples = ImmutableList.of(new Apple("RED", 100), //ImmutableList not for sort
                new Apple("RED", 120),
                new Apple("GOLD", 40),
                new Apple("GREEN", 60),
                new Apple("GOLD", 150),
                new Apple("RED", 10));

        apples = new ArrayList<>(apples);

        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });

        System.out.println(apples);
    }

    @Test
    public void mapDefault() throws Exception {
        Map<String, Integer> nameToSalary = ImmutableMap.of("Ivan", 200);

        // Integer Salary = nameToSalary.get("Stepan"); - null

        Integer Salary = nameToSalary.getOrDefault("Stepan", 0);

    }

    @Test
    public void filterByPredicate() throws Exception {
        List<Apple> apples = ImmutableList.of(new Apple("RED", 100), //ImmutableList not for sort
                new Apple("RED", 120),
                new Apple("GOLD", 40),
                new Apple("GREEN", 60),
                new Apple("GOLD", 150),
                new Apple("GREEN", 10));

        List<Apple> filtered = AppleSelector.filter(apples, new ColorPredicate());

        assertThat(filtered, hasSize(2));

    }

    @Test
    public void filterByAnonymousPredicate() throws Exception {
        List<Apple> apples = ImmutableList.of(new Apple("RED", 100), //ImmutableList not for sort
                new Apple("RED", 120),
                new Apple("GOLD", 40),
                new Apple("GREEN", 160),
                new Apple("GOLD", 150),
                new Apple("GREEN", 10));

        /*@Override
        List<Apple> filtered = AppleSelector.filter(apples, new ApplePredicate() {

            public Boolean test(Apple apple) {
                return apple.getWeight() > 120;
            }
        });*/

        List<Apple> filtered = AppleSelector.filter(apples, (apple) -> apple.getWeight() > 120);

        assertThat(filtered, hasSize(2));

    }



}