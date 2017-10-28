package hillelee.apple;

import hillelee.defaultMethods.Fruit;
import lombok.Data;
import lombok.Getter;

/**
 * Created by JavaEE on 28.10.2017.
 */

@Getter
public class Apple implements Fruit {
    private String color;
    private Integer weight;

    public Apple() {
    }

    public Apple(String color, Integer weight) {
        this.color = color;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Apple apple = (Apple) o;

        if (!color.equals(apple.color)) return false;
        return weight.equals(apple.weight);
    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + weight.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                '}';
    }
}
