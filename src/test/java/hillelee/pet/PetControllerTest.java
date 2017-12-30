package hillelee.pet;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.regex.Matcher;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by JavaEE on 30.12.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {
    @Autowired
    JpaPetRepository petRepository;

    @Autowired
    MockMvc mockMvc;

    @After
    public void cleanup() {
        petRepository.deleteAll();
    }

    @Test
    public void getAllPets() throws Exception {
        petRepository.save(new Pet(null, "Tom", "Cat", 3, LocalDate.now(), null, null));
        mockMvc.perform(get("/pets"))
                //.andExpect(MockMvcResultMatchers.status().isBadRequest());
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Tom")));
    }

    @Test
    public void sortByAge() throws Exception {
        petRepository.save(new Pet(null, "Tom", "Cat", 3, LocalDate.now(), null, null));
        petRepository.save(new Pet(null, "Jerry", "Mouse", 1, LocalDate.now(), null, null));

        mockMvc.perform(get("/pets")
                .param("sort", "age"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].age", is(1)))
                .andExpect(jsonPath("$.content[1].age", is(3)));
    }
}