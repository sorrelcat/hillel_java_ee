package hillelee;

import hillelee.doctor.*;
import hillelee.pet.*;
import hillelee.shedule.Record;
import hillelee.shedule.SheduleRepository;
import hillelee.store.Medicine;
import hillelee.store.MedicineRepository;
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
    public DoctorService doctorService(JpaDoctorRepository doctorRepository) {
        return new DoctorService(doctorRepository);
    }

    @Bean
    CommandLineRunner initDb(JpaPetRepository petRepository) {
        return args -> {
            /*if(petRepository.findAll().isEmpty()) {
                return;
            }*/

            MedicalCard tomsCard = new MedicalCard(LocalDate.now(), "bla-ble");
            MedicalCard jerrysCard = new MedicalCard(LocalDate.now(), "jerryyy");

            List<Prescription> tomsPrescriptionList = new ArrayList<>();
            tomsPrescriptionList.add(new Prescription("paracetamol", LocalDate.now(), 3));
            tomsPrescriptionList.add(new Prescription("aspirin", LocalDate.now(), 2));

            List<Prescription> jerrysPrescriptionList = new ArrayList<>();
            jerrysPrescriptionList.add(new Prescription("paracetamol", LocalDate.now(), 5));

            petRepository.save(new Pet(null, "Tom", "Cat", 3, LocalDate.now(), tomsCard, tomsPrescriptionList));
            petRepository.save(new Pet(null, "Jerry", "Mouse", 1, LocalDate.now(), jerrysCard, jerrysPrescriptionList));

        };
    }

    @Bean
    CommandLineRunner initMedicineStore(MedicineRepository medicineRepository) {
        return args -> {
          medicineRepository.save(new Medicine("Brilliant green", 1));
        };
    }

    @Bean
    CommandLineRunner initShedule(SheduleRepository sheduleRepository) {
        return args -> {
          sheduleRepository.save(new Record(1, 1, LocalDate.now(), 8));
          sheduleRepository.save(new Record(1, 1, LocalDate.of(2017, 12, 24), 9));
        };
    }

    @Bean
    @Primary
    CommandLineRunner initDoctorDb(JpaDoctorRepository doctorRepository) {

        List<Prescription> tomsPrescriptionList = new ArrayList<>();
        tomsPrescriptionList.add(new Prescription("paracetamol", LocalDate.now(), 3));
        tomsPrescriptionList.add(new Prescription("aspirin", LocalDate.now(), 2));

        List<Prescription> jerrysPrescriptionList = new ArrayList<>();
        jerrysPrescriptionList.add(new Prescription("paracetamol", LocalDate.now(), 5));

        List<Specialization> houseSpecializationList = new ArrayList<>();
        houseSpecializationList.add(new Specialization("diagnostician"));

        List<Specialization> chaseSpecializationList = new ArrayList<>();
        chaseSpecializationList.add(new Specialization("surgeon"));
        chaseSpecializationList.add(new Specialization("diagnostician"));
        chaseSpecializationList.add(new Specialization("therapeut"));

        return args -> {
            doctorRepository.save(new Doctor("House", houseSpecializationList));
            doctorRepository.save(new Doctor("Chase", chaseSpecializationList));
            doctorRepository.save(new Doctor("Forman", chaseSpecializationList));
            doctorRepository.save(new Doctor("Aisfirstletter", chaseSpecializationList));
        };
    }
}
