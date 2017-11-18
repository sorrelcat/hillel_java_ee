package hillelee.knight;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by JavaEE on 11.11.2017.
 */
public class FairyTale {

    public static void main(String [] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext("hillelee");

        Knight knight1 = ctx.getBean(Knight.class);
        Knight knight2 = ctx.getBean(Knight.class);

        System.out.println("Knight: " + knight1);
        System.out.println("Knights are same: " + (knight1 == knight2));
        System.out.println("Quests are same: " + (knight1.getQuest() == knight2.getQuest()));

        //System.out.println(ctx.getBean(Knight.class)); // first letter in classname to lowcase. can find by name of by class

    }
}

@Configuration // component of specific type
class Config {

    @Bean
    //@Scope
    public Knight knight (/*@Qualifier("quest")*/ Quest quest) {
        return new Knight(quest);
    }

    @Bean
    @Primary
    Quest killDragon() {
        return new Quest("Kill dragon");
    }

    @Bean
    Quest rescuePrincess() {
        return new Quest("Rescue the Princess");
    }

    @Bean
    String task() {
        return "do nothing";
    }


}

@Data
//@Component("myKnight")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // not singletone
class Knight {

    private final Quest quest;

}

@Data
@Component
class Quest {
    private final String task;

}
