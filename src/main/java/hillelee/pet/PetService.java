package hillelee.pet;

import hillelee.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by JavaEE on 02.12.2017.
 */

@Service
@RequiredArgsConstructor
public class PetService {

    private final JpaPetRepository petRepository;
    private final StoreService storeService;

    public List<Pet> getPetsUsingStreamFilters(@RequestParam Optional<String> specie,
                                               @RequestParam Optional<Integer> age) {
        Predicate<Pet> specieFilter = specie.map(this::filterBySpecie)
                .orElse(pet -> true);

        Predicate<Pet> ageFilter = age.map(this::filterByAge)
                .orElse(pet -> true);

        Predicate<Pet> complexFilter = ageFilter.and(specieFilter);

        return petRepository.findAll().stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }

    public Page<Pet> getPetsUsingSeparateJpaMethods (@RequestParam Optional<String> specie,
                                                     @RequestParam Optional<Integer> age,
                                                     Pageable pageable) {

       if(specie.isPresent() && age.isPresent()) {
           return petRepository.findBySpecieAndAge(specie.get(), age.get(), pageable);
       }

       if(specie.isPresent()) {
           return petRepository.findBySpecie(specie.get(), pageable);
       }

       if(age.isPresent()) {
           return petRepository.findByAge(age.get(), pageable);
       }

       return petRepository.findAll(pageable);
    }

    @Transactional
    public List<Pet> getPetsUsingSingleJpaMethod(Optional<String> specie,
                                                 Optional<Integer> age/*,
                                                 Optional<LocalDate> birthDate*/) {

        List<Pet> nullableBySpecieAndAge = petRepository.findNullableBySpecieAndAge(specie.orElse(null), age.orElse(null)/*, birthDate.orElse(null)*/);

        nullableBySpecieAndAge.forEach(pet -> System.out.println(pet.getPrescriptions()));

        return nullableBySpecieAndAge;
    }

    public Optional<Pet> getById(Integer id) {
        return petRepository.findById(id);
    }

    public Pet save(Pet pet) {

        return petRepository.save(pet);
    }

    public Optional<Pet> delete(Integer id) {

        Optional<Pet> mayBePet = petRepository.findById(id);
        mayBePet.ifPresent(pet -> petRepository.delete(pet.getId()));

        return mayBePet;
    }

    private Predicate<Pet> filterBySpecie(String specie) {
        return pet -> pet.getSpecie().equals(specie);
    }

    private Predicate<Pet> filterByAge(Integer age) {
        return pet -> pet.getAge().equals(age);
    }

    @Transactional
    @Retryable(ObjectOptimisticLockingFailureException.class)
    public void prescribe(Integer petId,
                          String description,
                          String medicineName,
                          Integer quantity,
                          Integer timesPerDay) {

        Pet pet = petRepository.findById(petId).orElseThrow(RuntimeException::new);
        pet.getPrescriptions().add(new Prescription(description, LocalDate.now(), timesPerDay/*, MedicineType.PERORAL*/));
        petRepository.save(pet);
        storeService.decrement(medicineName, quantity);
    }
}
