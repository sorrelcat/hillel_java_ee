package hillelee.pet;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by JavaEE on 02.12.2017.
 */
@Service
public class PetService {
    private AtomicInteger counter = new AtomicInteger(1);

    private Map<Integer, Pet> pets = new ConcurrentHashMap<Integer, Pet>() {{
        put(0, new Pet(0, "Tom", "Cat", 3));
        put(1, new Pet(1, "Jerry", "Mouse", 1));
    }};

    public List<Pet> getPets(@RequestParam Optional<String> specie,
                             @RequestParam Optional<Integer> age) {

        Predicate<Pet> specieFilter = specie.map(this::filterBySpecie)
                .orElse(pet -> true);

        Predicate<Pet> ageFilter = age.map(this::filterByAge)
                .orElse(pet -> true);

        Predicate<Pet> complexFilter = ageFilter.and(specieFilter);

        return pets.values().stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }

    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(counter.incrementAndGet());
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    public Optional<Pet> delete(Integer id) {
        return Optional.ofNullable(pets.remove(id));
    }

    public Optional<Pet> getById(Integer id) {
        return Optional.ofNullable(pets.get(id));
    }

    private Predicate<Pet> filterBySpecie(String specie) {
        return pet -> pet.getSpecie().equals(specie);
    }

    private Predicate<Pet> filterByAge(Integer age) {
        return pet -> pet.getAge().equals(age);
    }
}
