package hillelee.shedule;

import hillelee.doctor.Doctor;
import hillelee.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SheduleRepository extends JpaRepository<Record, Integer> {

    Optional<Record> findById(Integer id);

    Optional<Pet> findByPetId(Integer id);

    Optional<Doctor> findByDoctorId(Integer id);

    List<Record> findByDay(LocalDate day);

    @Query("SELECT record FROM Record AS record " +
    "WHERE (record.doctorId = :doctorId) " +
    "AND (record.day = :day)")
    List<Record> findByDayAndDoctorId(@Param("day") LocalDate day, @Param("doctorId") Integer doctorId);



}
