package hillelee.restraunt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;

@Data
@AllArgsConstructor
public class Dish {

    private String name;
    private Integer calories;
    private Boolean isBio;
    private DishType type;

}
