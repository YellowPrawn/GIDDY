package persistence;

import org.json.JSONObject;

// This class is modelled off the Writable interface found in the JsonSerializationDemo repository
public interface Writable {
    // Modelled off the toJson method found in the JsonSerializationDemo repository
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
