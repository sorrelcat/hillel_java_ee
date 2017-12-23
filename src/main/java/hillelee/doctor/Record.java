package hillelee.doctor;


import hillelee.pet.Pet;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer petId;
    private LocalDate day;
    private Integer session;

    public Record(Integer petId, LocalDate day, Integer session) {
        this.petId = petId;
        this.day = day;
        this.session = session;
    }
}
