package hillelee.shedule;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class SheduleController {
    private final SheduleService sheduleService;

    @GetMapping("/doctors/{doctorId}/schedule/{day}")
    public List<Record> getRecordsByDayAndDoctorId(@PathVariable LocalDate day, @PathVariable Integer doctorId) {
        return sheduleService.getRecordsByDayAndDoctorId(day, doctorId);
    }

    //POST /doctors/id/schedule/2010-01-01/10
    @PostMapping("/doctors/{doctorId}/schedule/{day}/{session}")
    public ResponseEntity<Void> createRecord(@PathVariable Integer doctorId,
                                             @PathVariable LocalDate day,
                                             @PathVariable Integer session,
                                             @RequestParam Integer petId) {

        Record saved = sheduleService.save(doctorId, day, session, petId);
        return ResponseEntity.created(URI.create("/doctors/" + saved.getDoctorId() + "/shedule/" + saved.getDay() + "/" + saved.getSession())).build();

    }
}
