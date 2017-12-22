package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findById(Integer id);

    List<Doctor> findBySpecializationAndName(Integer specialization, String name);

    List<Doctor> findBySpecialization(Integer specialization);

    List<Doctor> findByName(String name);

    @Query("SELECT doctor FROM Doctor AS doctor " +
            "JOIN doctor.specializations s WHERE s IN :specializations) " +
            "OR ((LOWER(doctor.name) = LOWER(:name)) OR :name IS NULL )")
    List<Doctor> findNullableBySpecializationAndName(@Param("name") String name, @Param("specializations") List<Integer> specializations);

    @Query("SELECT doctor.shedule")
    Optional<Doctor> findSheduleByDay(LocalDate day);


    Optional<Record> saveRecord(Integer id, LocalDate day, Integer session, Integer petId);
}
