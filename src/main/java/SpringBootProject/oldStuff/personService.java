//package SpringBootProject.oldStuff;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//
//import java.io.File;
//
//public class personDBCode {
//    private static File personDatabase = new File("src\\test\\java\\mockDatabase\\PersonDatabase");
//    private static com.Services.Base Base = new Base();
//
//    public String findAttribute(String key, String value) {
//        return Base.parseJSONArrayAndGetValue(Base.readFromFile(personDatabase), key, value);
//    }
//
//    public void updatePetAttribute(String pet_name, String key, String value, String id) {
//        JSONArray personList = Base.parseJSONArray(Base.readFromFile(personDatabase));
//        JSONObject personJson = Base.parseJsonArrayAndGetJsonObject(personList.toString(), "id", id);
//        JSONArray pets = (JSONArray) personJson.get("pets");
//        String oldPets = pets.toString();
//        JSONObject petJson = Base.parseJsonArrayAndGetJsonObject(pets.toString(), "pet_name", pet_name);
//
//        petJson.put(key, value);
//        pets = Base.updateJSONArray(pets, petJson, "pet_name", pet_name);
//        personJson.put("pets", pets);
//        Base.writeToFile(personDatabase, Base.updateJSONArray(personList, personJson, "pets", oldPets).toString());
//    }
//
//    public void addAttribute(String attribute, String value, String id) {
//        JSONArray personList = Base.parseJSONArray(Base.readFromFile(personDatabase));
//        JSONObject personJson = Base.parseJsonArrayAndGetJsonObject(personList.toString(), "id", id);
//        personJson.put(attribute, value);
//        Base.writeToFile(personDatabase, Base.updateJSONArray(personList, personJson, "id", id).toString());
//    }
//
//    public void addPerson(String person) {
//        JSONArray personList = Base.parseJSONArray(Base.readFromFile(personDatabase));
//        personList.add(Base.parseJSONObject(person));
//        Base.writeToFile(personDatabase, personList.toString());
//    }
//
//    public void removePerson(String person) {
//        JSONArray personList = Base.parseJSONArray(Base.readFromFile(personDatabase));
//        JSONObject personJson = Base.parseJsonArrayAndGetJsonObject(personList.toString(), "first_name", person);
//        personList.remove(personJson);
//        Base.writeToFile(personDatabase, personList.toString());
//    }
//
//    public int checkHowManyAge(String age) {
//        JSONArray personList = Base.parseJSONArray(Base.readFromFile(personDatabase));
//        int count = 0;
//
//        for (Object person : personList) {
//            JSONObject tempJson = (JSONObject) person;
//            if (tempJson.get("age").toString().equalsIgnoreCase(age))
//                count++;
//        }
//        return count;
//    }
//
//    public int checkHowManyOwnCar(String model) {
//        JSONArray personList = Base.parseJSONArray(Base.readFromFile(personDatabase));
//        int count = 0;
//
//        for (Object person : personList) {
//            JSONObject personJson = (JSONObject) person;
//            JSONArray tempArray = (JSONArray) personJson.get("cars");
//            for (Object car : tempArray) {
//                JSONObject tempJson = (JSONObject) car;
//                if (tempJson.get("model").toString().equalsIgnoreCase(model))
//                    count++;
//            }
//        }
//
//        return count;
//    }
//
//    public int checkHowManyOwnACat(String animal) {
//        JSONArray personList = Base.parseJSONArray(Base.readFromFile(personDatabase));
//        int count = 0;
//        boolean catFlag = false;
//
//        for (Object person : personList) {
//            JSONObject personJson = (JSONObject) person;
//            JSONArray tempArray = (JSONArray) personJson.get("pets");
//            if (catFlag)
//                count++;
//            catFlag = false;
//            for (Object pet : tempArray) {
//                JSONObject tempJson = (JSONObject) pet;
//                if (tempJson.get("animal") != null && tempJson.get("animal").toString().equalsIgnoreCase(animal)) {
//                    catFlag = true;
//                }
//            }
//        }
//
//        return count;
//    }
//
//    public String findWhoOwnsAPet(String animal) {
//        JSONArray personJsonArray = (JSONArray) Base.parseJsonArrayAndGetMatches(Base.parseJSONArray(Base.readFromFile(personDatabase)).toString(), "animal", animal);
//        JSONObject person = Base.parseJsonArrayAndGetJsonObject(personJsonArray.toString(), "animal", animal);
//        return person.get("first_name").toString() + " " + person.get("last_name").toString();
//    }
//
//    public int howManInDB() {
//        return Base.parseJSONArray(Base.readFromFile(personDatabase)).size();
//    }
////
////    public String searchForAttribute(String attribute, String value) {
////        return Base.parseJsonArrayAndGetMatches(Base.parseJSONArray(Base.readFromFile(personDatabase)).toString(), attribute, value).toString();
////    }
//}
