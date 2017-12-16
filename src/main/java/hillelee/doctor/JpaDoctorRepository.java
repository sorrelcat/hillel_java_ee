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
            "WHERE (doctor.name = :name OR :name IS NULL )" +
            "AND (doctor.specialization IN :specializations)") // !!!
    List<Doctor> findNullableBySpecializationAndName(@Param("name") String name, @Param("specializations") List<String> specializations);

    //doesn't works with *IgnoreCase in function or LOWER UPPER in query. and MEMBER OF instead of IN (((

}
