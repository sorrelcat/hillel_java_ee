package hillelee.pet;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Created by JavaEE on 16.12.2017.
 */

@Data
@NoArgsConstructor
@Entity
public class MedicalCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate firstApeearance;
    private String complains;

    public MedicalCard(LocalDate firstApeearance, String complains) {
        this.firstApeearance = firstApeearance;
        this.complains = complains;
    }
}
