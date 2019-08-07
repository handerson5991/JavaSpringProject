package SpringBootProject.services;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

@Service
public class PersonService {

    private static File personDatabase = new File("SpringBootProject\\mockDatabase\\PersonDatabase");

    @GetMapping("/api/search")
    public String Search(String attribute, String value) {
        return parseJsonArrayAndGetMatches(parseJSONArray(readFromFile(personDatabase)).toString(), attribute, value).toString();
    }

    public String readFromFile(File file) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            StringBuffer sb = new StringBuffer();

            while ((str = in.readLine()) != null) {
                sb.append(str + "\n ");
            }
            in.close();
            return sb.toString();
        } catch (IOException ie) {
            System.out.println(ie.getMessage());
        }
        return "Could not read from file";
    }

    public JSONArray parseJSONArray(String text) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        JSONArray jsonArray = null;
        try {
            if (parser.parse(text) instanceof JSONArray) {
                jsonArray = (JSONArray) parser.parse(text);
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray parseJsonArrayAndGetMatches(String text, String key, String value) {
        JSONParser parser = new JSONParser(text, /*???????????????*/,false);
        JSONArray returnArray = new JSONArray();
        JSONObject jsonObject = null;
        try {
            if (parser.parse() instanceof JSONArray) {
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
}
