package hillelee.knight;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by JavaEE on 11.11.2017.
 */
public class FairyTale {

    public static void main(String [] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext("hillelee");

        //Knight knight = new Knight(new Quest());

        //System.out.println(ctx.getBean(Knight.class));
        System.out.println(ctx.getBean("knight")); // first letter in classname to lowcase

    }
}

@Component
@Data
class Knight {

    private final Quest quest;

    /*@Autowired
    public Knight(Quest quest) {
        this.quest = quest;
    }

    *//*public Knight() {
    }*//*

    public Quest getQuest() {
        return quest;
    }

    *//*@Autowired
    public void setQuest(Quest quest) {
        this.quest = quest;
    }*//*

    @Override
    public String toString() {
        return "Knight{" +
                "quest=" + quest +
                '}';
    }*/
}

@Component
class Quest {
    private String task = "Kill the Dragon";

    public Quest(String task) {
        this.task = task;
    }

    public Quest() {
    }

    public String getTask() {
        return task;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "task='" + task + '\'' +
                '}';
    }

    public void setTask(String task) {
        this.task = task;
    }
}
