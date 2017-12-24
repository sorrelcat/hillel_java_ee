package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findById(Integer id);

    Optional<Doctor> findByName(String name);

    List<Doctor> findBySpecializationsAndName(List<Integer> specializations, String name);

    List<Doctor> findBySpecializations(List<Integer> specializations);


    @Query("SELECT doctor FROM Doctor AS doctor " +
            "JOIN doctor.specializations s " +
            "WHERE s IN :specializations " +
            "OR (doctor.name = :name) " +
            "OR (:name IS NULL) ")
    List<Doctor> findNullableBySpecializationsAndName(@Param("name") String name, @Param("specializations") List<Integer> specializations);

    //Optional<Record> saveRecord(Integer id, LocalDate day, Integer session, Integer petId);

   // Optional<Doctor> findSheduleByDay(LocalDate day);
}
