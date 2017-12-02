package hillelee;

import hillelee.pet.PetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by JavaEE on 02.12.2017.
 */

@Configuration
public class HilleleeConfig {
    @Bean
    public PetService petService() {
        return new PetService();
    }
}
