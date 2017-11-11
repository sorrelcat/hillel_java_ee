package hillelee.apple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import hillelee.App;
import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
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
    public void selectHeaviest() throws Exception {

        Optional<Apple> mayBeHeaviest = AppleSelector.getHeaviest(apples);

        if (mayBeHeaviest.isPresent()) {
            Apple heaviest = mayBeHeaviest.get();
            assertThat(heaviest.getWeight(), is(160));
        } else {
            fail();
        }
    }

    @Test
    public void selectHeaviestFromEmptyList() throws Exception {
        List<Apple> apples = ImmutableList.of();

        Optional<Apple> mayBeApple = AppleSelector.getHeaviest(apples); // .var
        if (mayBeApple.isPresent()) {
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
                .collect(toList());

        assertThat(filtered, hasSize(0));

    }

    @Test
    public void mapToColor() throws Exception {
        List<String> colors = apples.stream()
                //.map(apple -> apple.getColor())
                .map(Apple::getColor) //class as first parameter
                .collect(toList()); //terminal - return list, not stream

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
    public void adjustColor() throws Exception {

        ColorAdjuster colorAdjuster = new ColorAdjuster();

        Function<String, String> adjust = colorAdjuster::adjust;
        //примет --- вернет. первый параметр вшит. вызывается на объекте, поэтому два параметра

        BiFunction<ColorAdjuster, String, String> adjustWithAdjuster = ColorAdjuster::adjust;
        //объект, на котором будет вызвана --- примет --- вернет. вызывается на классе, нестатический метод, третий параметр - объект

        apples.stream()
                .map(Apple::getColor)
                .map(colorAdjuster::adjust)
                .forEach(System.out::println); //terminal method - void
    }


    @Test
    public void executionFlow() throws Exception {
        apples.stream()
                .filter(apple -> apple.getWeight() > 59)
                .map(Apple::getColor)
                .peek(System.out::println) // non-terminal - no print, lazy
                .limit(3) // first three
                .collect(toList()); // terminal, returns no stream - if present, method runs and get results
    }

    @Test
    public void findFirst() throws Exception {
        apples.stream()
                .filter(apple -> apple.getWeight() > 200)
                .findFirst() //  returns Optional with Apple
                .map(Apple::getColor) // calls on Optional, not stream. but has same method map(). returns Optional with color
                .ifPresent(System.out::println);  // for !NullPointerException
    }

    @Test
    public void intStream() throws Exception {
        /*LongSummaryStatistics longSummaryStatistics = apples.stream()
                .mapToLong(Apple::getWeight) // 'cause stream() in map() is stream of Object
                .summaryStatistics();*/

        IntSummaryStatistics longSummaryStatistics = apples.stream()
                .map(Apple::getWeight)
                //.mapToInt(weight -> weight)
                .mapToInt(Integer::intValue) // return int instead of Integer
                .summaryStatistics();

        System.out.println(longSummaryStatistics);
    }

    @Test
    public void name() throws Exception {
        Map<Integer, Apple> weightToApple = apples.stream()
                .collect(toMap(Apple::getWeight, Function.identity())); // key + value, value == object

        assertThat(weightToApple.get(100), is(apples.get(0)));

    }

    @Test
    public void groupingBy() throws Exception {

        Map<String, List<Apple>> collect = apples.stream()
                .collect(Collectors.groupingBy(Apple::getColor, toList()));
        System.out.println(collect.get("RED"));
    }

    @Test
    public void getAllWorms() throws Exception {
        apples.stream()
               /* .map(Apple::getWorms) // stream of lists of worms
                .flatMap(List::stream) // list of strings to stream of strings*/

               .flatMap(apple -> apple.getWorms().stream()) // same but on lambdas
                .distinct() // deleting same worms
                .forEach(System.out::println);
    }
}