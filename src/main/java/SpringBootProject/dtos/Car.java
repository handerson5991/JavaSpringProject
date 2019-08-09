package SpringBootProject.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Car {
    @ApiModelProperty(hidden = true)
    String year;
    @ApiModelProperty(hidden = true)
    String make;
    @ApiModelProperty(hidden = true)
    String model;
    @ApiModelProperty(hidden = true)
    String color;

    public Car(String year, String make, String model, String color){
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
    }
}