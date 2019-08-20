package SpringBootProject.controllers;

import SpringBootProject.dtos.Car;
import SpringBootProject.dtos.Person;
import SpringBootProject.dtos.Pet;
import SpringBootProject.services.PersonService;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.annotation.Target;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> Search(@RequestParam String attribute, @RequestParam String value) {
        return ResponseEntity.ok(personService.Search(attribute, value));
    }

    @RequestMapping(value = "/update/attribute", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAttribute(@RequestParam String attribute, @RequestParam String value, @RequestParam int id) {
        return ResponseEntity.ok(personService.updateAttribute(attribute, value, id));
    }

    @RequestMapping(value = "/update/addAtribute", method = RequestMethod.POST)
    public ResponseEntity<?> addAttribute(@RequestParam String attribute, @RequestParam String value, @RequestParam int id) {
        return ResponseEntity.ok(personService.addAttribute(attribute, value, id));
    }

    @PostMapping("update/addPerson")
    public ResponseEntity<?> addPerson(@RequestBody @Valid Person person) {
        return ResponseEntity.ok(personService.addPerson(person));
    }

    @RequestMapping(value = "/update/addPet", method = RequestMethod.POST)
    public ResponseEntity<?> addPet(@RequestBody Pet pet, @RequestParam int id) {
        return ResponseEntity.ok(personService.addPet(pet, id));
    }

    @RequestMapping(value = "/update/addCar", method = RequestMethod.POST)
    public ResponseEntity<?> addCar(@RequestBody Car car, @RequestParam int id) {
        return ResponseEntity.ok(personService.addCar(car, id));
    }

    @RequestMapping(value = "/update/removePerson", method = RequestMethod.DELETE)
    public ResponseEntity<?> removePerson(@RequestParam String firstName) {
        return ResponseEntity.ok(personService.removePerson(firstName));
    }

    @RequestMapping(value = "/update/removeAtribute", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeAttribute(@RequestParam String attribute, @RequestParam String value, @RequestParam int id) {
        return ResponseEntity.ok(personService.removeAttribute(attribute, value, id));
    }

}
