package hillelee.apple;

import com.google.common.collect.ImmutableList;
import hillelee.defaultMethods.Fruit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * Created by JavaEE on 28.10.2017.
 */

//@Getter

@Data
@AllArgsConstructor // see appleSelectorTest

public class Apple implements Fruit {
    private String color;
    private Integer weight;

    private List<String> worms = ImmutableList.of("worm1", "worm2", "worm3");

    public Apple(String color, Integer weight) {
        this.color = color;
        this.weight = weight;
    }
}
