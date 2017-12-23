package hillelee.pet;

import hillelee.pet.dto.PrescriptionInputDto;
import hillelee.util.ErrorBody;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by JavaEE on 18.11.2017.
 */

@RestController
@AllArgsConstructor
public class PetController {

    private final PetService petService;

    @GetMapping(value = "/greeting")
    public String helloWorld() {
        return "Hello world! ";
    }

    @GetMapping("/pets")
    public List<Pet> getPets(@RequestParam Optional<String> specie,
                             @RequestParam Optional<Integer> age) {
       return petService.getPetsUsingSingleJpaMethod(specie, age);
    }


    @GetMapping("/pets/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Integer id) {

        Optional<Pet> mayBePet = petService.getById(id);

        return mayBePet.map(Object.class::cast)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest()
                        .body(new ErrorBody("there is no pet with id = " + id)));
    }

    @PostMapping("/pets")
    public ResponseEntity<Void> createPet(@RequestBody Pet pet) {

        Pet saved = petService.save(pet);

        return ResponseEntity.created(URI.create("/pets/" + saved.getId())).build();
    }

    @PutMapping("/pets/{id}")
    public void updatePet(@PathVariable Integer id,
                          @RequestBody Pet pet) {
        pet.setId(id);
        petService.save(pet);
    }

    @DeleteMapping("/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Integer id) {

        petService.delete(id)
                .orElseThrow(NoSuchPetException::new);

    }

    @PostMapping("/pets/{id}/prescriptions")
    public void prescribe(@PathVariable Integer id,
                          @RequestBody PrescriptionInputDto dto) {
        petService.prescribe(id,
                dto.getDescription(),
                dto.getMedicineName(),
                dto.getQuantity(),
                dto.getTimesPerDay());

    }

   /* @ExceptionHandler(MyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(MyException exception) {
        log.error("error throw");
    }*/
}






