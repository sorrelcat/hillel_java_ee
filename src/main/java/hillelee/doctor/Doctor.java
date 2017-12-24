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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private  List<Specialization> specializations;

    public Doctor(String name, List<Specialization> specializations) {
        this.name = name;
        this.specializations = specializations;
    }
}
