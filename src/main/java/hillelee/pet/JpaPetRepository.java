package hillelee.pet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by JavaEE on 09.12.2017.
 */
public interface JpaPetRepository extends JpaRepository<Pet, Integer> {

    public Optional<Pet> findById(Integer id);

}
