package com.simpower.models;

import com.simpower.Main;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URISyntaxException;

public class JSONReader {
    private JSONParser jsonParser = new JSONParser();
    private JSONObject json;

    public JSONReader() {}

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

    /**
     * Write a JSON to file
     *
     * @param path path of the json path
     * @throws IOException exceptions
     */
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

    /**
     * set JSON to the current json object
     * @param json
     */
    public void setJSON(JSONObject json) {
        this.json = json;
    }

    /**
     * get JSON from the current json object
     * @return
     */
    public JSONObject getJSON() {
        return this.json;
    }
}
