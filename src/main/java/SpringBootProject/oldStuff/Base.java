//package SpringBootProject.oldStuff;
//
//import daos.animalDBCode;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import java.io.*;
//
//public class Base {
//    private static animalDBCode db = new animalDBCode();
//
//    public JSONObject parseJSONObject(String text) {
//        JSONParser parser = new JSONParser();
//        JSONObject json = null;
//        try {
//            if (parser.parse(text) instanceof JSONObject)
//                json = (JSONObject) parser.parse(text);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return json;
//    }
//
//    public JSONArray parseJSONArray(String text) {
//        JSONParser parser = new JSONParser();
//        JSONArray jsonArray = null;
//        try {
//            if (parser.parse(text) instanceof JSONArray) {
//                jsonArray = (JSONArray) parser.parse(text);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return jsonArray;
//    }
//
//    public String parseJSONArrayAndGetValue(String text, String key, String value) {
//        JSONParser parser = new JSONParser();
//        try {
//            if (parser.parse(text) instanceof JSONArray) {
//                JSONArray jsonArray = (JSONArray) parser.parse(text);
//
//                for (Object obj : jsonArray) {
//                    JSONObject jsonObject = (JSONObject) obj;
//
//                    for (Object k : jsonObject.keySet()) {
//                        if (k.toString().equalsIgnoreCase(key) && jsonObject.get(k).toString().equalsIgnoreCase(value))
//                            return jsonObject.get(k).toString();
//                        else if (jsonObject.get(k) instanceof JSONArray) {
//                            if (!parseJSONArrayAndGetValue(jsonObject.get(k).toString(), key, value).equalsIgnoreCase("Could not find value"))
//                                return parseJSONArrayAndGetValue(jsonObject.get(k).toString(), key, value);
//                        }
//                    }
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return "Could not find value";
//    }
//
//    public JSONObject parseJsonArrayAndGetJsonObject(String text, String key, String value) {
//        JSONParser parser = new JSONParser();
//        JSONObject jsonObject = null;
//        try {
//            if (parser.parse(text) instanceof JSONArray) {
//                JSONArray jsonArray = (JSONArray) parser.parse(text);
//
//                for (Object obj : jsonArray) {
//                    jsonObject = (JSONObject) obj;
//
//                    for (Object k : jsonObject.keySet()) {
//                        if (k.toString().equalsIgnoreCase(key) && jsonObject.get(k).toString().equalsIgnoreCase(value))
//                            return jsonObject;
//                    }
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return jsonObject;
//    }
//
//    public JSONArray parseJsonArrayAndGetMatches(String text, String key, String value) {
//        JSONParser parser = new JSONParser();
//        JSONArray returnArray = new JSONArray();
//        JSONObject jsonObject = null;
//        try {
//            if (parser.parse(text) instanceof JSONArray) {
//                JSONArray jsonArray = (JSONArray) parser.parse(text);
//
//                for (Object obj : jsonArray) {
//                    jsonObject = (JSONObject) obj;
//
//                    for (Object k : jsonObject.keySet()) {
//                        if (k.toString().equalsIgnoreCase(key) && jsonObject.get(k).toString().equalsIgnoreCase(value))
//                            returnArray.add(jsonObject);
//                        else if (jsonObject.get(k) instanceof JSONArray && !parseJSONArrayAndGetValue(jsonObject.get(k).toString(), key, value)
//                                .equalsIgnoreCase("Could not find value")) {
//                            returnArray.add(jsonObject);
//                        }
//
//                    }
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return returnArray;
//    }
//
//    public JSONArray updateJSONArray(JSONArray jsonArray, JSONObject json, String keyName, String value){
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject tempJSON = (JSONObject) jsonArray.get(i);
//            if (tempJSON.get(keyName).toString().equalsIgnoreCase(value)) {
//                jsonArray.remove(i);
//                jsonArray.add(i, json);
//            }
//        }
//        return jsonArray;
//    }
//
//    public String parseNestedJSON(String outerKey, String innerKey, String JSONString) {
//        return parseJSONObject(parseJSONObject(JSONString).get(outerKey).toString()).get(innerKey).toString();
//    }
//
//    public String readFromFile(File file) {
//        try {
//            BufferedReader in = new BufferedReader(new FileReader(file));
//            String str;
//            StringBuffer sb = new StringBuffer();
//
//            while ((str = in.readLine()) != null) {
//                sb.append(str + "\n ");
//            }
//            in.close();
//            return sb.toString();
//        } catch (IOException ie) {
//            System.out.println(ie.getMessage());
//        }
//        return "Could not read from file";
//    }
//
//    public void writeToFile(File file, String writeString) {
//        try {
//            FileWriter output = new FileWriter(file);
//            BufferedWriter writer = new BufferedWriter(output);
//            writer.write(writeString);
//            writer.flush();
//            writer.close();
//        } catch (IOException ie) {
//            System.out.println(ie.getMessage());
//        }
//    }
//}
