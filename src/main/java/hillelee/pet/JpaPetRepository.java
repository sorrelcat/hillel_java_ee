package hillelee.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by JavaEE on 09.12.2017.
 */
public interface JpaPetRepository extends JpaRepository<Pet, Integer> {

    public Optional<Pet> findById(Integer id);

    List<Pet> findBySpecieAndAge(String specie, Integer age);

    List<Pet> findBySpecie(String specie);

    List<Pet> findByAge(Integer age);

    @Query("SELECT pet FROM Pet AS pet " +
    "WHERE (pet.specie = :specie OR :specie IS NULL )" +
    " AND (pet.age = :age OR :age IS NULL)")
    List<Pet> findNullableBySpecieAndAge(@Param("specie") String specie, @Param("age") Integer age);
}
