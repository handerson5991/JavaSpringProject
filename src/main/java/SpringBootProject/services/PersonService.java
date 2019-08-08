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
}
