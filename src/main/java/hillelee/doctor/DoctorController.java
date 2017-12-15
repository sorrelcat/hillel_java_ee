package hillelee.doctor;

import hillelee.util.ErrorBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
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
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    /* GET /doctors возвращает список всех докторов
    GET /doctors/{id} возвращает доктора либо 404
    GET /doctors?specialization=surgeon возвращает список всех хирургов
    GET /doctors?name=A возвращает список докторов у которых имя начинается на “А” */

    @GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam Optional<String> specialization,
                                   @RequestParam Optional<String> name) {

        return doctorService.getDoctorsUsingSingleJpaMethod(specialization, name);
    }

    @GetMapping("/doctors/{specializations}")
    public List<Doctor> getDoctorsWithAnySpecialisations(@PathVariable List<String> specializations) {
        return doctorService.getByListOfSpecializations(specializations);
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Integer id) {

        Optional<Doctor> mayBeDoctor = doctorService.getById(id);

        return mayBeDoctor.map(Object.class::cast)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest()
                        .body(new ErrorBody("there is no doctor with id = " + id)));
    }

    /*    Create
POST /doctros возвращает
201 с указанием пути к созданному доктору
400 если пытаются создать доктора с предопределенным ID     */

    @PostMapping("/doctors")
    public ResponseEntity<Void> createDoctor(@RequestBody Doctor doctor) {

        Doctor saved = doctorService.save(doctor);

        return ResponseEntity.created(URI.create("/doctors/" + saved.getId())).build();
    }


    /*    PUT /doctors/{id} возвращает
204 если операция успешна
404 если такого доктора не существует
400 если доктору пытаются изменить ID     */

    @PutMapping("/doctors/{id}")
    public void updateDoctor(@PathVariable Integer id,
                                          @RequestBody Doctor doctor) {
        doctor.setId(id);
        doctorService.save(doctor);
    }

    /*    DELETE /doctors/{id}
204 если удаление успешно
404 если такого доктора не существует     */

    @DeleteMapping("doctors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Integer id) {

        doctorService.delete(id)
                .orElseThrow(NoSuchDoctorException::new);
    }
}


