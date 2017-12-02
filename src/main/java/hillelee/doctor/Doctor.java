package hillelee.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by JavaEE on 02.12.2017.
 */
@Data
@AllArgsConstructor
public class Doctor {
    Integer id;
    String name;
    String specialization;
}
