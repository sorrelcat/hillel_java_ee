package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {

    public Optional<Doctor> findById(Integer id);

    List<Doctor> findBySpecializationAndName(String specialization, String name);

    List<Doctor> findBySpecialization(String specialization);

    List<Doctor> findByName(String name);

    @Query("SELECT doctor FROM Doctor AS doctor " +
            "WHERE (LOWER(doctor.specialization) = LOWER(:specialization) OR :specialization IS NULL )" +
            " AND (LOWER(doctor.name) = LOWER(:name) OR :name IS NULL)")
    List<Doctor> findNullableBySpecializationAndName(@Param("specialization") String specialization, @Param("name") String name);
}
