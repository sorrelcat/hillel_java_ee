package hillelee.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by JavaEE on 18.11.2017.
 */

@RestController
public class PetController {

    private List<Pet> pets = new ArrayList<Pet>() {{
        add(new Pet("Tom", "Cat", 3));
        add(new Pet("Jerry", "Mouse", 1));
    }};

    @GetMapping(value = "/greeting")
    public String helloWorld() {
        return "Hello world! ";
    }

    @GetMapping("/pets")
    public List<Pet> getPets(@RequestParam Optional<String> specie) {

        Predicate<Pet> specieFilter = specie.map(this::filterBySpecie)
                .orElse(pet -> true);

        return pets.stream()
                .filter(specieFilter)
                .collect(Collectors.toList());

        /*if(!specie.isPresent()) {
            return pets;
        } else {
            return pets.stream()
                    .filter(pet -> pet.getSpecie().equals(specie.get()))
                    .collect(Collectors.toList());
        }*/
    }

    private Predicate<Pet> filterBySpecie(String specie) {
        return pet -> pet.getSpecie().equals(specie);
    }
}

@Data
@AllArgsConstructor
class Pet {
    private String name;
    private String specie;
    private Integer age;
}
