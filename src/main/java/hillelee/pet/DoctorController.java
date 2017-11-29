package hillelee.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class DoctorController {

    private AtomicInteger counter = new AtomicInteger(3);

    private List<Doctor> doctors = new ArrayList<Doctor>() {{
        add(new Doctor(0, "House", "diagnostician"));
        add(new Doctor(1, "Chase", "surgeon"));
        add(new Doctor(2, "Forman", "neurologist"));
        add(new Doctor(3, "Aisfirstletter", "surgeon"));
    }};

    /*    Create
POST /doctros возвращает
201 с указанием пути к созданному доктору
400 если пытаются создать доктора с предопределенным ID     */

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@RequestParam Doctor doctor) {
// не работает, если передавать json без id
        if (doctor.getId() == null) {
            doctors.add(new Doctor(counter.incrementAndGet(), doctor.getName(), doctor.getSpecialization()));
            return ResponseEntity.created(URI.create("/doctors/" + counter)).build();
        } else {
            return ResponseEntity.badRequest()
                    .body(new DoctorErrorBody("Can't create doctor with predetermined id"));
        }
    }

    /* GET /doctors возвращает список всех докторов
    GET /doctors/{id} возвращает доктора либо 404
    GET /doctors?specialization=surgeon возвращает список всех хирургов
    GET /doctors?name=A возвращает список докторов у которых имя начинается на “А” */

    private Predicate<Doctor> filterByFirstLetterInName(String name) {
        return doctor -> doctor.getName().startsWith(name);
    }

    private Predicate<Doctor> filterBySpecialization(String specialization) {
        return doctor -> doctor.getSpecialization().equals(specialization);
    }

    @GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam Optional<String> specialization,
                                   @RequestParam Optional<String> name) {

        Predicate<Doctor> specializationFilter = specialization.map(this::filterBySpecialization)
                .orElse(doctor -> true);
        Predicate<Doctor> nameFilter = name.map(this::filterByFirstLetterInName)
                .orElse(doctor -> true);
        Predicate<Doctor> complexFilter = specializationFilter.and(nameFilter);

        return doctors.stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Integer id) {

        if (getDoctorById(id).isPresent())
            return ResponseEntity.ok(getDoctorById(id).get());
        else return ResponseEntity.notFound().build();
    }

    /*    PUT /doctors/{id} возвращает
204 если операция успешна
404 если такого доктора не существует
400 если доктору пытаются изменить ID     */

    @PutMapping("/doctors/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Integer id,
                                          @RequestBody Doctor doctor) {

        if (!getDoctorById(id).isPresent()) return ResponseEntity.notFound().build();
        if (doctor.getId() != id) return ResponseEntity.badRequest()
                    .body(new DoctorErrorBody(String.format("Can't change doctor's id from %d to %d", id, doctor.getId())));

        doctors.remove(getDoctorById(id).get());
        doctors.add(doctor);
        return ResponseEntity.status(204).build();
    }

    /*    DELETE /doctors/{id}
204 если удаление успешно
404 если такого доктора не существует     */

    @DeleteMapping("doctors/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer id) {

        if (!getDoctorById(id).isPresent()) return ResponseEntity.notFound().build();

        doctors.remove(getDoctorById(id).get());
        return ResponseEntity.status(204).build();
    }

    public Optional<Doctor> getDoctorById(Integer id) {
        return doctors.stream()
                .filter(doctor -> doctor.getId() == id)
                .findFirst();
    }
}


@Data
@AllArgsConstructor
class Doctor {
    Integer id;
    String name;
    String specialization;

    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
        this.id = null;
    }
}

@Data
@AllArgsConstructor
class DoctorErrorBody {
    private final Integer code = 400;
    private String body;
}