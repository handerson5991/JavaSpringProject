package SpringBootProject.controllers;

import SpringBootProject.Services.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    WelcomeService welcomeService;

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public String welcome(){
        return welcomeService.retrieveWelcomeMessage();
    }

}
