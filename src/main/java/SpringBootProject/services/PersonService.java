package SpringBootProject.services;

import SpringBootProject.utils.FileUtils;
import SpringBootProject.utils.JsonUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.File;

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
    public JSONArray updatePetAttributes(String petName, String attribute, String value, String id){
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
    public JSONArray addPerson(String id, String firstName, String lastName, String age, JSONArray attributes) {
        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        JSONObject newPerson = new JSONObject();
        newPerson.put("id", id);
        newPerson.put("first_name", firstName);
        newPerson.put("last_name", lastName);
        newPerson.put("age", age);
        attributes.forEach(attribute -> newPerson.put(attribute));
        personList.add(newPerson);
        fileUtils.writeToFile(personDatabase, personList.toJSONString());
        return jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
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
