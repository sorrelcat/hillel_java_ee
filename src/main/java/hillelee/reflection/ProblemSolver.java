package hillelee.reflection;

import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.Arrays;
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

        return Stream.of(problem)
                .map(Object::getClass)
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.isAnnotationPresent(CorrectAnswer.class))
                .map(method -> (String) method.invoke(problem))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no CorrectAnswer annotation"));
    }
}

// invoke, flatmap, reflection, map
