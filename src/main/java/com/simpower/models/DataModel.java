package com.simpower.models;

import com.simpower.Main;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URISyntaxException;

public class DataModel {
    private JSONParser jsonParser = new JSONParser();
    private JSONObject json;

    public DataModel() {}

    public void setJSON(JSONObject json) {
        this.json = json;
    }

    public JSONObject getJSON() {
        return this.json;
    }

    /**
     * Read a JSON file
     *
     * @param path path of the json file
     * @return json file to read
     *
     * @warning can only read JSON Object, Array must be wrapped in objects
     */
    public JSONObject read(String path) {
        try (FileReader reader = new FileReader(Main.class.getResource(path).getPath().replaceAll("%20", " "))) {
            // read JSON file
            this.setJSON((JSONObject) this.jsonParser.parse(reader));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return this.getJSON();
    }

    public void write(String path) throws IOException {
        FileWriter writer = new FileWriter(Main.class.getResource(path).getPath().replaceAll("%20", " "));

        try {
            // write JSON to file
            writer.write(this.getJSON().toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
