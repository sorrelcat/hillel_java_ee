package hillelee.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Pet> getPets(@RequestParam Optional<String> specie, // по умолчанию необязательный
                             @RequestParam String gentle) {         // по умолчанию обязательный

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

    @GetMapping("/pets/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Integer id) {
        if(id >= pets.size()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorBody("there is no pet with id = " + id));
        }else {
            return ResponseEntity.ok(pets.get(id));
        }
    }

    @PostMapping("/pets")
    public void createPet(@RequestBody Pet pet) {
        pets.add(pet);
    }

    @PutMapping("/pets/{id}")
    public void updatePet(@PathVariable Integer id,
                          @RequestBody Pet pet) {
        pets.set(id, pet);
    }

    @DeleteMapping("/pets/{id}")
    public void deletePet(@PathVariable Integer id) {
        pets.remove(id.intValue());
    }

    private Predicate<Pet> filterBySpecie(String specie) {
        return pet -> pet.getSpecie().equals(specie);
    }

}

@Data
@AllArgsConstructor
class ErrorBody{
    private final Integer number = 400;
    private String body;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Pet {
    private String name;
    private String specie;
    private Integer age;
}
