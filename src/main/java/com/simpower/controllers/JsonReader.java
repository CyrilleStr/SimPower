package com.simpower.controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonReader {
    private JSONParser jsonParser = new JSONParser();
    private JSONObject json;

    public JSONObject getJSON() {
        return this.json;
    }

    public JSONObject read(String path) {
        try (FileReader reader = new FileReader(path)) {

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