package persistence;

import model.Category;
import model.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads data from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads data from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Data read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseData(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses data from JSON object and returns it
    private Data parseData(JSONObject jsonObject) {
        return addRows(jsonObject);
    }

    // MODIFIES: data
    // EFFECTS: parses rows from JSON object and adds them to data
    private Data addRows(JSONObject jsonObject) {
        ArrayList<String> rows = new ArrayList<>();
        rows.add(jsonObject.getString("header"));
        JSONArray jsonArray = jsonObject.getJSONArray("row");
        for (Object json : jsonArray) {
            //JSONObject nextRow = (JSONObject) json;
            rows.add(String.valueOf(json));
        }
        return new Data(rows);
    }
}
