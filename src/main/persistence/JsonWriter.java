package persistence;

import model.Data;
import org.json.JSONObject;


import java.io.*;

// This class is modelled off the JsonWriter class found in the JsonSerializationDemo repository
// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // Modelled off the JsonWriter method found in the JsonSerializationDemo repository
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Modelled off the open method found in the JsonSerializationDemo repository
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // Modelled off the write method found in the JsonSerializationDemo repository
    // MODIFIES: this
    // EFFECTS: writes JSON representation of data to file
    public void write(Data data) {
        JSONObject json = data.toJson();
        saveToFile(json.toString(TAB));
    }

    // Modelled off the close method found in the JsonSerializationDemo repository
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // Modelled off the saveToFile method found in the JsonSerializationDemo repository
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
