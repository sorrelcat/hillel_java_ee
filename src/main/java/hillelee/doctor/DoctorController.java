package hillelee.doctor;

import hillelee.pet.Pet;
import hillelee.util.ErrorBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
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
    public List<Doctor> getDoctors(@RequestParam Optional<String> name,
                                   @RequestParam(required = false) List<Integer> specializations) {

        return doctorService.getDoctorsUsingSingleJpaMethod(name, specializations);
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Integer id) {

        Optional<Doctor> mayBeDoctor = doctorService.getById(id);

        return mayBeDoctor.map(Object.class::cast)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest()
                        .body(new ErrorBody("there is no doctor with id = " + id)));
    }

    @GetMapping("/doctors/{id}/shedule/{day}")
    public ResponseEntity<?> getDoctorSheduleByDay (@PathVariable LocalDate id, @PathVariable LocalDate day) {
        Optional<Doctor> mayBeShedule = doctorService.getSheduleByDay(day);

        return mayBeShedule.map(Object.class::cast)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest()
                        .body(new ErrorBody("there is no shedule for doctor with id = " + id + "and date " + day)));
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

    @PostMapping("/doctors/{id}/schedule/{date}/{session}")
    public ResponseEntity<Void> createAcceptance(@PathVariable Integer id, @PathVariable LocalDate date, @PathVariable Integer session, @RequestParam Integer petId) {

        Optional<Record> saved = doctorService.save(id, date, session, petId);

        return ResponseEntity.created(URI.create("/doctors/" + saved.get().getId())).build();
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


