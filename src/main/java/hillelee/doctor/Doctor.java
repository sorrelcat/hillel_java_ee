package hillelee.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by JavaEE on 02.12.2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private  List<Specialization> specializations;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Record> shedule;

    public Doctor(String name, List<Specialization> specializations, List<Record> shedule) {
        this.name = name;
        this.specializations = specializations;
        this.shedule = shedule;
    }
}
