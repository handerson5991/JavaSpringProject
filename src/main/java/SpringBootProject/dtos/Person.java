package SpringBootProject.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @ApiModelProperty(hidden = true)
    int id;
    String first_name;
    String last_name;
    String age;
    List<Pet> pets;
    List<Car> cars;

    public Person(String first_name, String last_name, String age, List<Pet> pets, List<Car> cars) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.pets = pets;
        this.cars = cars;
    }

    public void setId(int id) {
        this.id = id;
    }
}