package hillelee.apple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by JavaEE on 28.10.2017.
 */
public class AppleSelectorTest {

    List<Apple> apples = ImmutableList.of(new Apple("RED", 100), //ImmutableList not for sort
            new Apple("RED", 120),
            new Apple("GOLD", 40),
            new Apple("GREEN", 160),
            new Apple("GOLD", 150),
            new Apple("GREEN", 10));

    @Test
    public void selectHeaviest () throws Exception {

        Optional<Apple> mayBeHeaviest = AppleSelector.getHeaviest(apples);

        if(mayBeHeaviest.isPresent()) {
            Apple heaviest = mayBeHeaviest.get();
            assertThat(heaviest.getWeight(), is(160));
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

        List<Apple> filtered = AppleSelector.filter(apples, new ColorPredicate());

        assertThat(filtered, hasSize(2));

    }

    Predicate<Apple> heavierThan120 = apple -> apple.getWeight() > 120;
    Predicate<Apple> isRed = apple -> apple.getColor().equals("RED");
    Predicate<Apple> heavyAndRed = isRed.and(heavierThan120);
    Predicate<Apple> heavyAndRed2 = ((Predicate<Apple>) (apple -> apple.getWeight() > 120))
            .and(apple -> apple.getColor().equals("RED"));


    @Test
    public void filterByAnonymousPredicate() throws Exception {


        //List<Apple> filtered = AppleSelector.filter(apples, (Apple apple) -> apple.getWeight() > 120);

        List<Apple> filtered = apples.stream()
                .filter(heavyAndRed)
                .collect(Collectors.toList());

                assertThat(filtered, hasSize(0));

    }

    @Test
    public void mapToColor () throws Exception {
        List<String> colors = apples.stream()
                //.map(apple -> apple.getColor())
                .map(Apple::getColor) //class as first parameter
                .collect(Collectors.toList());

        assertThat(colors, hasSize(6));
        assertThat(colors.get(0), is("RED"));
    }

    @Test
    public void printApples() throws Exception {
        /*apples.stream()
                .forEach(apple -> System.out.println(apple));
        */
        apples.stream()
                .forEach(System.out::println);
    }

    @Test
    public void findExactSame() throws Exception {
        Apple apple = new Apple("RED", 100);

       /* apples.stream()
                .filter();*/
    }







}