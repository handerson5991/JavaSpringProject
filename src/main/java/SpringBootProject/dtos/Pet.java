package SpringBootProject.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pet {
    String pet_name;
    String animal;
    String breed;
    String age;

    public Pet(String pet_name, String animal, String breed, String age) {
        this.pet_name = pet_name;
        this.animal = animal;
        this.breed = breed;
        this.age = age;
    }
}
