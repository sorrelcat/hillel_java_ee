package hillelee;

import hillelee.doctor.Doctor;
import hillelee.doctor.DoctorService;
import hillelee.doctor.JpaDoctorRepository;
import hillelee.pet.JpaPetRepository;
import hillelee.pet.Pet;
import hillelee.pet.PetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JavaEE on 02.12.2017.
 */

@Configuration
public class HilleleeConfig {

    @Bean
    public PetService petService(JpaPetRepository petRepository) {
        return new PetService(petRepository);
    }

    @Bean
    public DoctorService doctorService(JpaDoctorRepository doctorRepository) {
        return new DoctorService(doctorRepository);
    }

    @Bean
    CommandLineRunner initDb(JpaPetRepository petRepository) {
        return args -> {
            petRepository.save(new Pet(null, "Tom", "Cat", 3, LocalDate.now()));
            petRepository.save(new Pet(null, "Jerry", "Mouse", 1, LocalDate.now()));

        };
    }

    @Bean
    @Primary
    CommandLineRunner initDoctorDb(JpaDoctorRepository doctorRepository) {
        return args -> {
            doctorRepository.save(new Doctor(null, "House", "diagnostician"));
            doctorRepository.save(new Doctor(null, "Chase", "surgeon"));
            doctorRepository.save(new Doctor(null, "Forman", "neurologist"));
            doctorRepository.save(new Doctor(null, "Aisfirstletter", "therapeut"));
        };
    }
}
