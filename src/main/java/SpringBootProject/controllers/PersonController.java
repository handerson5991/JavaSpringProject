package SpringBootProject.controllers;

import SpringBootProject.dtos.Car;
import SpringBootProject.dtos.Pet;
import SpringBootProject.services.PersonService;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public JSONArray Search(@RequestParam String attribute, @RequestParam String value) {
        return personService.Search(attribute, value);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        personService.test();
    }

    //Maybe change to updateAttribute
    @RequestMapping(value = "/update/petAttribute", method = RequestMethod.PUT)
    public JSONArray updatePetAttribute(@RequestParam String petName, @RequestParam String attribute, @RequestParam String value, @RequestParam String id) {
        return personService.updatePetAttributes(petName, attribute, value, id);
    }

    @RequestMapping(value = "/update/addAtribute", method = RequestMethod.POST)
    public JSONArray addAttribute(@RequestParam String attribute, @RequestParam String value, @RequestParam String id) {
        return personService.addAttribute(attribute, value, id);
    }

    @RequestMapping(value = "/update/addPerson", method = RequestMethod.POST)
    public JSONArray addPerson(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String age, @RequestBody List<Pet> pets, @RequestBody List<Car> cars) {
        return personService.addPerson(firstName, lastName, age, pets, cars);
    }

    @RequestMapping(value = "/update/removePerson", method = RequestMethod.DELETE)
    public JSONArray removePerson(@RequestParam String firstName) {
        return personService.removePerson(firstName);
    }

    @RequestMapping(value = "/update/removeAtribute", method = RequestMethod.DELETE)
    public JSONArray removeAttribute(@RequestParam String attribute, @RequestParam String value, @RequestParam String id) {
        return personService.removeAttribute(attribute, value, id);
    }

}
