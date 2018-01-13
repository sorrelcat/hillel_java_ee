package hillelee.pet;

import com.google.common.io.Resources;
import hillelee.store.Medicine;
import hillelee.store.MedicineRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
    MedicineRepository medicineRepository;

    @Autowired
    MockMvc mockMvc;

    @After
    public void cleanup() {
        petRepository.deleteAll();
        medicineRepository.deleteAll();
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

    @Test
    public void getPetById() throws Exception {
        Integer id = petRepository.save(new Pet(null, "Tom", "Cat", 3, LocalDate.now(), null, null))
                .getId();

        mockMvc.perform(get("/pets/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is("Tom")))
                .andExpect(jsonPath("$.*", hasSize(7)));
    }

    @Test
    public void petNotFound() throws Exception {
        mockMvc.perform(get("/pets/1"))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void createPet() throws Exception {
        String body = readFile("cat.json");
        mockMvc.perform(post("/pets")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/pets")));

        List<Pet> all = petRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all.get(0).getName().get(), is("Tom"));
    }

    @Test
    public void updatePet() throws Exception {
        Integer id = petRepository.save(new Pet(null, "Jerry", "Mouse", 1, LocalDate.now(), null, null))
                .getId();

        String body = readFile("cat.json");

        mockMvc.perform(put("/pets/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
        .andExpect(status().isOk());

                Pet pet = petRepository.findById(id).get();

                assertThat(pet.getName().get(), is("Tom"));
                assertThat(pet.getSpecie(), is("Cat"));

    }

    @Test
    public void deletePet() throws Exception {
        Integer id = petRepository.save(new Pet(null, "Tom", "Cat", 3, LocalDate.now(), null, null))
                .getId();

        mockMvc.perform(delete("/pets/{id}", id))
                .andExpect(status().isNoContent());

        Optional<Pet> mayBePet = petRepository.findById(id);

        assertFalse(mayBePet.isPresent());

    }

    @Test
    public void prescribeMedicine() throws Exception {
        Integer id = petRepository.save(new Pet(null, "Tom", "Cat", 3, LocalDate.now(), null, null))
                .getId();

        medicineRepository.save(new Medicine("Brilliantum Greenus", 1));

        String body = readFile("prescriptions.json");

        mockMvc.perform(post("/pets/{id}/prescriptions", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
                .andExpect(status().isOk());

        List<Prescription> prescriptions = petRepository.findById(id).get().getPrescriptions();

        assertThat(prescriptions, hasSize(1));

        Medicine greenus = medicineRepository.findByName("Brilliantum Greenus").get();

        assertThat(greenus.getQuantity(), is(0));

    }


    @Test
    public void notEnoughMedicine() throws Exception {

        Integer id = petRepository.save(new Pet(null, "Tom", "Cat", 3, LocalDate.now(), null, null))
                .getId();

        String body = readFile("prescriptions.json");

        mockMvc.perform(post("/pets/{id}/prescriptions", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

    private String readFile(String resourceName) throws IOException {
        return Resources.toString(Resources.getResource(resourceName), Charset.defaultCharset());
    }
}