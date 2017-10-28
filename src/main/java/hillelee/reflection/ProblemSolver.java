package hillelee.reflection;

import java.lang.reflect.Method;

/**
 * Created by JavaEE on 28.10.2017.
 */
public class ProblemSolver {

    public String solve(Object problem) {

        Class<?> aClass = problem.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods
                ) {
            if (method.isAnnotationPresent(CorrectAnswer.class)) {
                try {
                    return (String) method.invoke(problem);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException("There is no CorrectAnswer annotation");
    }
}
