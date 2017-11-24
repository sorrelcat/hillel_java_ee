package hillelee.pet;

import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by JavaEE on 18.11.2017.
 */

@RestController
public class PetController {


    @GetMapping(value = "/greeting")
    String getHellos() {
        return RandomGreeting.helloWorld();
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("hillelee.pet"); // don't work with hillelee
        System.out.println(ctx.getBean(RandomGreeting.class));
    }
}

@Data
@Component
class RandomGreeting {
    public static String helloWorld() {
        int choice = (int) (3 * Math.random());
        switch (choice) {
            case 0:
                return "hello world";
            case 1:
                return "hola world";
            default:
                return "bonjour world";
        }
    }
}

// comment for test branch

