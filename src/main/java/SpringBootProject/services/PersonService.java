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

    @GetMapping("/api/test")
    public void test() {
        ObjectMapper mapper = new ObjectMapper();
        Pet pet = new Pet("dog", "dog", "dog", "2");
        List<Pet> pets = new ArrayList<>();
        pets.add(pet);
        Person person = new Person(1, "bob", "bobson", "2", pets, new ArrayList<>());
        try {
            System.out.println(mapper.writeValueAsString(person));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @PutMapping("/api/update")
    public JSONArray updatePetAttributes(String petName, String attribute, String value, String id) {
        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", id);
        JSONArray pets = (JSONArray) personJson.get("pets");
        String oldPets = pets.toString();
        JSONObject petJson = jsonUtils.parseJsonArrayAndGetJsonObject(pets.toString(), "pet_name", petName);
        petJson.put(attribute, value);
        pets = jsonUtils.updateJSONArray(pets, petJson, "pet_name", petName);
        personJson.put("pets", pets);
        fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, personJson, "pets", oldPets).toJSONString());
        return Search("id", id);
    }

    @PostMapping("/api/add")
    public JSONArray addAttribute(String attribute, String value, String id) {
        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", id);
        personJson.put(attribute, value);
        fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, personJson, "id", id).toJSONString());
        //ResponseEntity.ok?
        return Search("id", id);
    }

    @PostMapping("/api/add")
    public JSONArray addPerson(String firstName, String lastName, String age, List<Pet> pets, List<Car> cars) {
        try {
            JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
            ObjectMapper mapper = new ObjectMapper();
            Person newPerson = new Person(personList.size() + 1, firstName, lastName, age, new ArrayList<>(), new ArrayList<>());
            //fix ioException
            personList.add(mapper.writeValueAsString(newPerson));
            fileUtils.writeToFile(personDatabase, personList.toJSONString());
            return jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
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

    public JSONArray removeAttribute(String attribute, String value, String id) {
        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", id);
        JSONObject updatedPersonJson = jsonUtils.parseJSONObjectAndRemoveKey(personJson, attribute, value);
        fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, updatedPersonJson, "id", id).toJSONString());
        return jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
    }
}
