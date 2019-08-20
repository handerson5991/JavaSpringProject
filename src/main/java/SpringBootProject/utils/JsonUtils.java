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

    public JSONObject parseJSONObject(String text) {
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            if (parser.parse(text) instanceof JSONObject)
                json = (JSONObject) parser.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return json;
    }

    public JSONArray parseJsonArrayAndGetMatches(String text, String key, String value) {
        JSONArray returnArray = new JSONArray();
        JSONObject jsonObject = null;
        JSONArray jsonArray = parseJSONArray(text);
        for (Object obj : jsonArray)){
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
        return returnArray;
    }

    public String parseJSONArrayAndGetValue(String text, String key, String value) {
        JSONArray jsonArray = parseJSONArray(text);
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
        return "Could not find value";
    }

    public JSONObject parseJSONObjectAndRemoveKey(JSONObject jsonObject, String key, String value) {
        if (jsonObject.keySet().contains(key)) {
            jsonObject.remove(key);
            return jsonObject;
        } else {
            for (Object k : jsonObject.keySet()) {
                if (jsonObject.get(k) instanceof JSONArray) {
                    JSONArray innerArray = (JSONArray) jsonObject.get(k);
                    JSONObject innerJson = parseJsonArrayAndGetJsonObject(innerArray.toString(), key, value);
                    innerJson.remove(key);
                    innerArray = updateJSONArray(innerArray, innerJson, key, value);
                    jsonObject.put(k, innerArray);
                    return jsonObject;
                }
            }
        }
        return null;
    }

    public JSONObject parseJSONObjectAndUpdateKey(JSONObject jsonObject, String key, String value) {
        for (Object k : jsonObject.keySet()) {
            if (k.toString().equalsIgnoreCase(key) && jsonObject.get(k).toString().equalsIgnoreCase(value)) {
                jsonObject.put(key, value);
            } else if (jsonObject.get(k) instanceof JSONArray) {
                JSONArray innerArray = (JSONArray) jsonObject.get(k);
                JSONObject innerJson = parseJsonArrayAndGetJsonObject(innerArray.toString(), key, value);
                innerJson.put(key, value);
                innerArray = updateJSONArray(innerArray, innerJson, key, value);
                jsonObject.put(k, innerArray);
                return jsonObject;
            }
        }
        return null;
    }

    public JSONObject parseJsonArrayAndGetJsonObject(String text, String key, String value) {
        JSONArray jsonArray = parseJSONArray(text);
        JSONObject jsonObject = null;
                for (Object obj : jsonArray) {
                    jsonObject = (JSONObject) obj;
                    for (Object k : jsonObject.keySet()) {
                        if (k.toString().equalsIgnoreCase(key) && jsonObject.get(k).toString().equalsIgnoreCase(value))
                            return jsonObject;
                    }
                }
        return jsonObject;
    }

    public String parseNestedJSON(String outerKey, String innerKey, String JSONString) {
        return parseJSONObject(parseJSONObject(JSONString).get(outerKey).toString()).get(innerKey).toString();
    }

    public JSONArray updateJSONArray(JSONArray jsonArray, JSONObject json, String keyName, String value) {
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
