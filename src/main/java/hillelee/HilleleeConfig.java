package hillelee;

import hillelee.doctor.*;
import hillelee.pet.*;
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
    @Primary
    CommandLineRunner initDb(JpaPetRepository petRepository) {
        return args -> {
            /*if(petRepository.findAll().isEmpty()) {
                return;
            }*/

            MedicalCard tomsCard = new MedicalCard(LocalDate.now(), "bla-blah");
            MedicalCard jerrysCard = new MedicalCard(LocalDate.now(), "jerryyy");

            List<Prescription> tomsPrescriptionList = new ArrayList<>();
            tomsPrescriptionList.add(new Prescription("paracetamol", LocalDate.now(), 3/*, MedicineType.PERORAL*/));
            tomsPrescriptionList.add(new Prescription("aspirin", LocalDate.now(), 2/*, MedicineType.PERORAL*/));

            List<Prescription> jerrysPrescriptionList = new ArrayList<>();
            jerrysPrescriptionList.add(new Prescription("paracetamol", LocalDate.now(), 5/*, MedicineType.PERORAL*/));

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
/*
    @Bean

    CommandLineRunner initDoctorDb(JpaDoctorRepository doctorRepository) {

        List<Prescription> tomsPrescriptionList = new ArrayList<>();
        tomsPrescriptionList.add(new Prescription("paracetamol", LocalDate.now(), 3));
        tomsPrescriptionList.add(new Prescription("aspirin", LocalDate.now(), 2));

        List<Prescription> jerrysPrescriptionList = new ArrayList<>();
        jerrysPrescriptionList.add(new Prescription("paracetamol", LocalDate.now(), 5));

        List<Specialization> houseSpecializationList = new ArrayList<>();
        houseSpecializationList.add(new Specialization("diagnostician"));

        List<Specialization> chaseSpecializationList = new ArrayList<>();
        houseSpecializationList.add(new Specialization("surgeon"));
        houseSpecializationList.add(new Specialization("diagnostician"));
        houseSpecializationList.add(new Specialization("therapeut"));

        List<Record> shedule = new ArrayList<>();
        shedule.add(new Record(1, LocalDate.now(), 8));
        shedule.add(new Record(2,  LocalDate.now(), 9));

        return args -> {
            doctorRepository.save(new Doctor("House", houseSpecializationList, shedule));
            doctorRepository.save(new Doctor("Chase", chaseSpecializationList, shedule));
            doctorRepository.save(new Doctor("Forman", chaseSpecializationList, shedule));
            doctorRepository.save(new Doctor("Aisfirstletter", chaseSpecializationList, shedule));
        };
    }*/
}
