package hillelee.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DoctorService {

    private final JpaDoctorRepository doctorRepository;

    public List<Doctor> getDoctorsUsingSingleJpaMethod (@RequestParam Optional<String> specialization,
                                                        @RequestParam Optional<String> name) {

        return doctorRepository.findNullableBySpecializationAndName(specialization.orElse(null), name.orElse(null));
    }

    public List<Doctor> getByListOfSpecializations (@RequestParam List<String> specializations) {

        return doctorRepository.findByListOfSpecializations(specializations);
    }

    public Optional<Doctor> getById(Integer id) {
        return doctorRepository.findById(id);
    }

    public Doctor save(Doctor doctor) {

        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> delete(Integer id) {

        Optional<Doctor> mayBeDoctor = doctorRepository.findById(id);
        mayBeDoctor.ifPresent(pet -> doctorRepository.delete(pet.getId()));

        return mayBeDoctor;
    }
}
