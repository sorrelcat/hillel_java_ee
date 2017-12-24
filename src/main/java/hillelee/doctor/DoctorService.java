package hillelee.doctor;

import hillelee.pet.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DoctorService {

    private final JpaDoctorRepository doctorRepository;

    public List<Doctor> getDoctorsUsingSingleJpaMethod (@RequestParam Optional<String> name,
                                                        @RequestParam List<Integer> specializations) {

        return doctorRepository.findNullableBySpecializationsAndName(name.orElse(null), specializations);
    }

    public Optional<Doctor> getById(Integer id) {
        return doctorRepository.findById(id);
    }

    public Doctor save(Doctor doctor) {

        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> delete(Integer id) {

        Optional<Doctor> mayBeDoctor = doctorRepository.findById(id);
        mayBeDoctor.ifPresent(doctor -> doctorRepository.delete(doctor.getId()));

        return mayBeDoctor;
    }

   /* public Optional<Doctor> getSheduleByDay(LocalDate day) {

        return doctorRepository.findSheduleByDay(day);
    }*/

    /*public Optional<Record> save(Integer doctorId, LocalDate day, Integer session, Integer petId) {

        return doctorRepository.saveRecord(doctorId, day, session, petId);
    }*/
}
