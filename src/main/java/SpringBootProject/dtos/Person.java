package SpringBootProject.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Person {

    @ApiModelProperty(hidden = true)
    int id;
    @ApiModelProperty(hidden = true)
    String first_name;
    @ApiModelProperty(hidden = true)
    String last_name;
    @ApiModelProperty(hidden = true)
    String age;
    @ApiModelProperty(hidden = true)
    List<Pet> pets;
    @ApiModelProperty(hidden = true)
    List<Car> cars;

    public Person(int id, String first_name, String last_name, String age, List<Pet> pets, List<Car> cars) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.pets = pets;
        this.cars = cars;
    }

    public int getid(){
        return this.id;
    }
}