package hillelee.shedule;

import hillelee.doctor.Doctor;
import hillelee.pet.Pet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SheduleService {

    private final SheduleRepository sheduleRepository;

    public Optional<Record> getById(Integer id) {
        return sheduleRepository.findById(id);
    }

    public Optional<Pet> getPetById(Integer id) {
        return sheduleRepository.findByPetId(id);
    }

    public Optional<Doctor> getDoctorById(Integer id) {
        return sheduleRepository.findByDoctorId(id);
    }

    public List<Record> getRecordsByDayAndDoctorId(LocalDate day, Integer doctorId) {
        return sheduleRepository.findByDayAndDoctorId(day, doctorId);
    }

    public Record save(Integer doctorId, LocalDate day, Integer session, Integer petId) {
        return sheduleRepository.save(new Record(doctorId, petId, day, session));
    }
}
