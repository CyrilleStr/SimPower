package com.simpower.controllers;

import com.simpower.Main;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URISyntaxException;

public class JsonReader {
    private JSONParser jsonParser = new JSONParser();
    private JSONObject json;

    public JsonReader() {}

    /**
     * Get a JSON object
     *
     * @return json file
     */
    public JSONObject getJSON() {
        return this.json;
    }

    /**
     * Read a JSON file
     *
     * @param path path of the json file
     * @return json file to read
     */
    public JSONObject read(String path) {

        try (FileReader reader = new FileReader((String.valueOf(Main.class.getResource(path)).replace("file:", "")))) {

            // read JSON file
            Object obj = this.jsonParser.parse(reader);
            return (JSONObject) obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
