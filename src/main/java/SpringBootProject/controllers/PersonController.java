package SpringBootProject.controllers;

import SpringBootProject.dtos.Car;
import SpringBootProject.dtos.Person;
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

    @RequestMapping(value = "/update/attribute", method = RequestMethod.PUT)
    public JSONArray updateAttribute(@RequestParam String attribute, @RequestParam String value, @RequestParam int id) {
        return personService.updateAttribute(attribute, value, id);
    }

    @RequestMapping(value = "/update/addAtribute", method = RequestMethod.POST)
    public JSONArray addAttribute(@RequestParam String attribute, @RequestParam String value, @RequestParam int id) {
        return personService.addAttribute(attribute, value, id);
    }

    @RequestMapping(value = "/update/addPerson", method = RequestMethod.POST)
    public JSONArray addPerson(@RequestParam Person person) {
        return personService.addPerson(person);
    }

    @RequestMapping(value = "/update/addPet", method = RequestMethod.POST)
    public JSONArray addPet(@RequestParam Pet pet, @RequestParam int id) {
        return personService.addPet(pet, id);
    }

    @RequestMapping(value = "/update/addCar", method = RequestMethod.POST)
    public JSONArray addCar(@RequestParam Car car, @RequestParam int id) {
        return personService.addCar(car, id);
    }

    @RequestMapping(value = "/update/removePerson", method = RequestMethod.DELETE)
    public JSONArray removePerson(@RequestParam String firstName) {
        return personService.removePerson(firstName);
    }

    @RequestMapping(value = "/update/removeAtribute", method = RequestMethod.DELETE)
    public JSONArray removeAttribute(@RequestParam String attribute, @RequestParam String value, @RequestParam int id) {
        return personService.removeAttribute(attribute, value, id);
    }

}
