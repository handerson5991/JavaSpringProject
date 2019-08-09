package SpringBootProject.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pet {
    @ApiModelProperty(hidden = true)
    String pet_name;
    @ApiModelProperty(hidden = true)
    String animal;
    @ApiModelProperty(hidden = true)
    String breed;
    @ApiModelProperty(hidden = true)
    String age;

    public Pet(String pet_name, String animal, String breed, String age) {
        this.pet_name = pet_name;
        this.animal = animal;
        this.breed = breed;
        this.age = age;
    }
}
