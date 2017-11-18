package hillelee.pet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by JavaEE on 18.11.2017.
 */

@RestController
public class PetController {

    //@RequestMapping(value = "/greeting", method = RequestMethod.GET)
    @GetMapping(value = "/greeting")
    public String helloWorld() {
        return "Hello world! ";
    }
}
