package SpringBootProject.controllers;

import SpringBootProject.services.PersonService;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public JSONArray Search(@RequestParam String attribute, @RequestParam String value){
        return personService.Search(attribute, value);
    }

    @RequestMapping(value = "/update/petAttribute", method = RequestMethod.PUT)
    public JSONArray updatePetAttribute(@RequestParam String petName, @RequestParam String attribute, @RequestParam String value, @RequestParam String id){
        return personService.updatePetAttributes(petName, attribute, value, id);
    }

}
