package SpringBootProject.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

    public JSONArray parseJSONArray(String text) {
        JSONParser parser = new JSONParser();
        try {
            if (parser.parse(text) instanceof JSONArray) {
                return (JSONArray) parser.parse(text);
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray parseJsonArrayAndGetMatches(String text, String key, String value) {
        JSONParser parser = new JSONParser();
        JSONArray returnArray = new JSONArray();
        JSONObject jsonObject = null;
        try {
            if (parser.parse(text) instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) parser.parse(text);

                for (Object obj : jsonArray) {
                    jsonObject = (JSONObject) obj;

                    for (Object k : jsonObject.keySet()) {
                        if (k.toString().equalsIgnoreCase(key) && jsonObject.get(k).toString().equalsIgnoreCase(value))
                            returnArray.add(jsonObject);
                        else if (jsonObject.get(k) instanceof JSONArray && !parseJSONArrayAndGetValue(jsonObject.get(k).toString(), key, value)
                                .equalsIgnoreCase("Could not find value")) {
                            returnArray.add(jsonObject);
                        }

                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnArray;
    }

    public String parseJSONArrayAndGetValue(String text, String key, String value) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        try {
            if (parser.parse(text) instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) parser.parse(text);

                for (Object obj : jsonArray) {
                    JSONObject jsonObject = (JSONObject) obj;

                    for (Object k : jsonObject.keySet()) {
                        if (k.toString().equalsIgnoreCase(key) && jsonObject.get(k).toString().equalsIgnoreCase(value))
                            return jsonObject.get(k).toString();
                        else if (jsonObject.get(k) instanceof JSONArray) {
                            if (!parseJSONArrayAndGetValue(jsonObject.get(k).toString(), key, value).equalsIgnoreCase("Could not find value"))
                                return parseJSONArrayAndGetValue(jsonObject.get(k).toString(), key, value);
                        }
                    }
                }
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        return "Could not find value";
    }

    public JSONObject parseJsonArrayAndGetJsonObject(String text, String key, String value) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            if (parser.parse(text) instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) parser.parse(text);

                for (Object obj : jsonArray) {
                    jsonObject = (JSONObject) obj;

                    for (Object k : jsonObject.keySet()) {
                        if (k.toString().equalsIgnoreCase(key) && jsonObject.get(k).toString().equalsIgnoreCase(value))
                            return jsonObject;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONArray updateJSONArray(JSONArray jsonArray, JSONObject json, String keyName, String value){
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject tempJSON = (JSONObject) jsonArray.get(i);
            if (tempJSON.get(keyName).toString().equalsIgnoreCase(value)) {
                jsonArray.remove(i);
                jsonArray.add(i, json);
            }
        }
        return jsonArray;
    }
}
