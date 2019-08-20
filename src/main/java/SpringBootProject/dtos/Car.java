package SpringBootProject.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Car {
    String year;
    String make;
    String model;
    String color;

    public Car(String year, String make, String model, String color){
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
    }
}