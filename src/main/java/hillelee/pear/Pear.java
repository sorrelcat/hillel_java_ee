package hillelee.pear;


import hillelee.defaultMethods.Fruit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pear implements Fruit {

    private String color;
    private Integer weight;

}
