package hillelee.pet;

import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RandomGreetingTest {

    public static final Integer BIG_NUMBER = 999; // number of elements in the sample
    public static final Double TOLERANCE = 0.05; // allowable error

    @Test
    public void normalDistribution() throws Exception {

        List<String> greetingStack = new ArrayList<>();

        for (int i = 0; i < BIG_NUMBER; i++) {
            greetingStack.add(RandomGreeting.helloWorld()); }

        Map<String, Long> greetingCounting = greetingStack.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println(greetingCounting.toString());

        assertTrue(Math.abs(greetingCounting.get("hello world") - BIG_NUMBER / 3) < BIG_NUMBER * TOLERANCE );
        assertTrue(Math.abs(greetingCounting.get("hola world") - BIG_NUMBER / 3) < BIG_NUMBER * TOLERANCE );
        assertTrue(Math.abs(greetingCounting.get("bonjour world") - BIG_NUMBER / 3) < BIG_NUMBER * TOLERANCE );

    }
}