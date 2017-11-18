package hillelee.reflection;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by JavaEE on 28.10.2017.
 */
public class ProblemSolver {

    @SneakyThrows
    public String solve(Object problem) {

        /*Class<?> aClass = problem.getClass();
        return Arrays.stream(aClass.getMethods())
                .filter(method -> method.isAnnotationPresent(CorrectAnswer.class))
                .map(method -> (String) method.invoke(problem))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no CorrectAnswer annotation"));*/

        return Stream.of(problem) // stream from 1 element
                .map(Object::getClass) // stream from 1 element - class of problem
                .flatMap(clazz -> Arrays.stream(clazz.getMethods())) // stream from any elements - their methods, new stream on base of original stream
                .filter(method -> method.isAnnotationPresent(CorrectAnswer.class)) // check annotation on methods
                //.map(method -> (String) method.invoke(problem)) // method (not problem in stream, problem from method parameter) with key annotation to string
                .map(invokeOn(problem)) // to get string from method
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no CorrectAnswer annotation")); // if optional is null, exception
    }

    private Function<Method, String> invokeOn(Object object) {

        return method -> {
            try {
                return (String) method.invoke(object);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}

// invoke, flatmap, reflection, map, effectively final
