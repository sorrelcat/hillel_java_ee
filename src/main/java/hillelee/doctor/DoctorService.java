package hillelee.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DoctorService {

    private final JpaDoctorRepository doctorRepository;

    public List<Doctor> getDoctorsUsingSeparateJpaMethods (@RequestParam Optional<String> specialization,
                                                        @RequestParam Optional<String> name) {

        if(specialization.isPresent() && name.isPresent()) {
            return doctorRepository.findBySpecializationAndName(specialization.get(), name.get());
        }

        if(specialization.isPresent()) {
            return doctorRepository.findBySpecialization(specialization.get());
        }

        if(name.isPresent()) {
            return doctorRepository.findByName(name.get());
        }

        return doctorRepository.findAll();
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
