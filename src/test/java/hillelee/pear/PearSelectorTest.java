package hillelee.pear;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class PearSelectorTest {

    @Test
    public void getHeaviest() throws Exception {

        List<Pear> pears = ImmutableList.of(new Pear("RED", 120),
                new Pear("RED", 20),
                new Pear("YELLOW", 40),
                new Pear("GREEN", 60),
                new Pear("GOLD", 50),
                new Pear("RED", 100));

        Optional<Pear> mayBeHeaviest = PearSelector.getHeaviest(pears);

        if(mayBeHeaviest.isPresent()) {
            Pear heaviest = mayBeHeaviest.get();
            assertThat(heaviest.getWeight(), is(120));
        }
        else {
            fail();
        }
    }

    @Test
    public void filterHeavy() throws Exception {

        List<Pear> pears = ImmutableList.of(new Pear("RED", 120),
                new Pear("RED", 20),
                new Pear("YELLOW", 40),
                new Pear("GREEN", 60),
                new Pear("GOLD", 50),
                new Pear("RED", 100));

        assertThat(PearSelector.filterHeavy(pears, 50), hasSize(2));
    }

    @Test
    public void filterYellow() throws Exception {

        List<Pear> pears = ImmutableList.of(new Pear("RED", 120),
                new Pear("RED", 20),
                new Pear("YELLOW", 40),
                new Pear("GREEN", 60),
                new Pear("YELLOW", 50),
                new Pear("RED", 100));

        assertThat(PearSelector.filterYellow(pears, "YELLOW"), hasSize(2));
    }

    @Test
    public void filter() throws Exception {

        List<Pear> pears = ImmutableList.of(new Pear("RED", 120),
                new Pear("RED", 20),
                new Pear("YELLOW", 40),
                new Pear("GREEN", 60),
                new Pear("YELLOW", 50),
                new Pear("RED", 100));

        assertThat(PearSelector.filter(pears, pear -> pear.getWeight() < 50), hasSize(2));
        assertThat(PearSelector.filter(pears, pear -> pear.getColor().equals("YELLOW")), hasSize(2));

    }

}