package SpringBootProject.services;

import SpringBootProject.dtos.Car;
import SpringBootProject.dtos.Person;
import SpringBootProject.dtos.Pet;
import SpringBootProject.utils.FileUtils;
import SpringBootProject.utils.JsonUtils;
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

@Service
public class PersonService {

    @Autowired
    private JsonUtils jsonUtils;

    @Autowired
    private FileUtils fileUtils;

    private static File personDatabase = new File("src\\main\\java\\SpringBootProject\\mockDatabase\\PersonDatabase");
    private static File tempFile = new File("src\\main\\java\\SpringBootProject\\mockDatabase\\temp");

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
    }

    @PostMapping("/api/add")
    public JSONArray addAttribute(String attribute, String value, int id) {
        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", Integer.toString(id));
        personJson.put(attribute, value);
        fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, personJson, "id", Integer.toString(id)).toJSONString());
        return Search("id", Integer.toString(id));
    }

    @PostMapping("/api/add")
    public JSONArray addPerson(Person person) {
        try {
            JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
            ObjectMapper mapper = new ObjectMapper();
            person.setId(personList.size() + 1);
            mapper.writeValue(tempFile, person);
            personList.add(jsonUtils.parseJSONObject(fileUtils.readFromFile(tempFile)));
            fileUtils.writeToFile(personDatabase, personList.toJSONString());
            return jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return null;
    }

    @PostMapping("/api/add")
    public JSONArray addPet(Pet pet, int id) {
        try {
            JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
            JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", Integer.toString(id));
            JSONArray petList = (JSONArray) personJson.get("pets");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(tempFile, pet);
            petList.add(jsonUtils.parseJSONObject(fileUtils.readFromFile(tempFile)));
            personJson.put("pets", petList);
            fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, personJson, "id", Integer.toString(id)).toJSONString());
            return Search("id", Integer.toString(id));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return null;
    }

    @PostMapping("/api/add")
    public JSONArray addCar(Car car, int id) {
        try {
            JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
            JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "id", Integer.toString(id));
            JSONArray carList = (JSONArray) personJson.get("cars");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(tempFile, car);
            carList.add(jsonUtils.parseJSONObject(fileUtils.readFromFile(tempFile)));
            personJson.put("cars", carList);
            fileUtils.writeToFile(personDatabase, jsonUtils.updateJSONArray(personList, personJson, "id", Integer.toString(id)).toJSONString());
            return Search("id", Integer.toString(id));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/api/remove")
    public JSONArray removePerson(String firstName) {
        JSONArray personList = jsonUtils.parseJSONArray(fileUtils.readFromFile(personDatabase));
        JSONObject personJson = jsonUtils.parseJsonArrayAndGetJsonObject(personList.toString(), "first_name", firstName);
        JSONArray updatedPersonList = new JSONArray();
        for (Object person : personList) {
            JSONObject json = (JSONObject) person;
            if (Integer.parseInt(json.get("id").toString()) < Integer.parseInt(personJson.get("id").toString())) {
                updatedPersonList.add(json);
            } else if (Integer.parseInt(json.get("id").toString()) > Integer.parseInt(personJson.get("id").toString())) {
                json.put("id", Integer.parseInt(json.get("id").toString()) - 1);
                updatedPersonList.add(json);
            }
        }
        fileUtils.writeToFile(personDatabase, updatedPersonList.toJSONString());
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
