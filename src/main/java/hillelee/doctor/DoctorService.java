package hillelee.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DoctorService {

    private final JpaDoctorRepository doctorRepository;

    public List<Doctor> getDoctorsUsingSingleJpaMethod (@RequestParam Optional<String> name,
                                                        @RequestParam List<String> specializations) { //!!!

        return doctorRepository.findNullableBySpecializationAndName(name.orElse(null), specializations);
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
