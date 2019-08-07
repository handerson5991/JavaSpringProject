package SpringBootProject.controllers;

import SpringBootProject.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @RequestMapping(value = "/search/{attribute}/{value}", method = RequestMethod.GET)
    public String Search(@PathVariable String attribute, @PathVariable String value){
        return personService.Search(attribute, value);
    }

}
