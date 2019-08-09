package SpringBootProject.services;

import SpringBootProject.dtos.Car;
import SpringBootProject.dtos.Person;
import SpringBootProject.dtos.Pet;
import SpringBootProject.utils.FileUtils;
import SpringBootProject.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private FileUtils fileUtils;

    private static File personDatabase = new File("src\\main\\java\\SpringBootProject\\mockDatabase\\PersonDatabase");

    @GetMapping("/api/search")
    public JSONArray Search(String attribute, String value) {
        return jsonUtils.parseJsonArrayAndGetMatches(jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase)).toString(), attribute, value);
    }

    @PutMapping("/api/update")
    public JSONArray updateAttribute(String attribute, String value, int id) {
        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", Integer.toString(id));
        JSONObject updatedPersonJson = jsonUtils.parseJSONObjectAndUpdateKey(personJson, attribute, value);
        fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, updatedPersonJson, "id", Integer.toString(id)).toJSONString());
        return jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
//        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
//        JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", id);
//        JSONArray pets = (JSONArray) personJson.get("pets");
//        String oldPets = pets.toString();
//        JSONObject petJson = jsonUtils.parseJsonArrayAndGetJsonObject(pets.toString(), "pet_name", petName);
//        petJson.put(attribute, value);
//        pets = jsonUtils.updateJSONArray(pets, petJson, "pet_name", petName);
//        personJson.put("pets", pets);
//        fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, personJson, "pets", oldPets).toJSONString());
//        return Search("id", id);
    }

    @PostMapping("/api/add")
    public JSONArray addAttribute(String attribute, String value, int id) {
        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", Integer.toString(id));
        personJson.put(attribute, value);
        fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, personJson, "id", Integer.toString(id)).toJSONString());
        //ResponseEntity.ok?
        return Search("id", Integer.toString(id));
    }

    @PostMapping("/api/add")
    public JSONArray addPerson(Person person) {
        try {
            JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
            ObjectMapper mapper = new ObjectMapper();
            person.setId(personList.size() + 1);
            //fix ioException
            personList.add(mapper.writeValueAsString(person));
            fileUtils.writeToFile(personDatabase, personList.toJSONString());
            return jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        } catch (JsonProcessingException je) {
            je.printStackTrace();
        }
        return null;
    }

    @PostMapping("/api/add")
    public JSONArray addPet(Pet pet, int id) {
        try {
            JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
            JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", Integer.toString(id));
            JSONArray petList = personList.get("pets");
            ObjectMapper mapper = new ObjectMapper();
            //fix ioException
            petList.add(mapper.writeValueAsString(pet));
            personJson.put("pets", petList);
            fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, personJson, "id", Integer.toString(id)).toJSONString());
            return Search("id", Integer.toString(id));
        } catch (JsonProcessingException je) {
            je.printStackTrace();
        }
        return null;
    }

    @PostMapping("/api/add")
    public JSONArray addCar(Car car, int id) {
        try {
            JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
            JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", Integer.toString(id));
            JSONArray carList = personList.get("cars");
            ObjectMapper mapper = new ObjectMapper();
            //fix ioException
            petList.add(mapper.writeValueAsString(car));
            personJson.put("pets", carList);
            fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, personJson, "id", Integer.toString(id)).toJSONString());
            return Search("id", Integer.toString(id));
        } catch (JsonProcessingException je) {
            je.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/api/remove")
    public JSONArray removePerson(String firstName) {
        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "first_name", firstName);
        personList.remove(personJson);
        fileUtils.writeToFile(personDatabase, personList.toJSONString());
        return jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
    }

    public JSONArray removeAttribute(String attribute, String value, int id) {
        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", Integer.toString(id));
        JSONObject updatedPersonJson = jsonUtils.parseJSONObjectAndRemoveKey(personJson, attribute, value);
        fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, updatedPersonJson, "id", Integer.toString(id)).toJSONString());
        return jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
    }
}
