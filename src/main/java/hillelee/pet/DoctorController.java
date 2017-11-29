package hillelee.pet;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoctorController {
}

class Doctor {
    Integer id;
    String name;
    String specialization;
}