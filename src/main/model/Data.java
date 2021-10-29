package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// An unclassified nx2 dataframe
public class Data implements Writable {
    private ArrayList<Object> colX;
    private ArrayList<Object> colY;
    private String headerX;
    private String headerY;
    private String typeX;
    private String typeY;

    // REQUIRES: CSV-like scanner without empty values or spaces
    // EFFECTS: creates 2 columns, reads a "CSV", and sets data types of both columns
    public Data(Scanner scanner) {
        colX = new ArrayList<>();
        colY = new ArrayList<>();
        readCSV(scanner);
        setTypeX();
        setTypeY();
    }

    // REQUIRES: name of CSV file without empty values or spaces
    // EFFECTS: creates 2 columns, reads a "CSV", and sets data types of both columns
    public Data(ArrayList<String> rawData) {
        colX = new ArrayList<>();
        colY = new ArrayList<>();
        readCSV(rawData);
        setTypeX();
        setTypeY();
    }

    // REQUIRES: CSV-like scanner without empty values or spaces
    // MODIFIES: this
    // EFFECTS: reads CSV scanner into project. Stops input if no new lines exist or line with : is entered
    private void readCSV(Scanner scanner) {
        scanner.useDelimiter(",|\\n");
        double i = 0;
        while (scanner.hasNext()) {
            String nextLine = scanner.next();
            if (nextLine.contains(":")) {
                break;
            }
            splitData(nextLine, i);
            i++;
        }
    }

    // REQUIRES: CSV-like ArrayList<String> without empty values or spaces
    // MODIFIES: this
    // EFFECTS: reads CSV-like ArrayList<String> into project.
    private void readCSV(ArrayList<String> rawData) {
        ArrayList<String> data = new ArrayList<>();
        for (String s : rawData) {
            String[] row = s.split(",|\\n");
            data.add(row[0]);
            data.add(row[1]);
        }
        double i = 0;
        for (String s : data) {
            splitData(s, i);
            i++;
        }
    }

    // REQUIRES: Non-empty values, no spaces
    // MODIFIES: this
    // EFFECTS: splits 2 column CSV data into 2 columns and headers
    private void splitData(String data, double i) {
        if (i == 0) {
            headerX = data;
        } else if (i == 1) {
            headerY = data;
        } else {
            if (i % 2 == 0) {
                colX.add(changeObservationType(data));
            } else {
                colY.add(changeObservationType(data));
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: determines colX type
    private void setTypeX() {
        try {
            double temp = (Double)colX.get(0);
            typeX = "Double";
        } catch (ClassCastException e) {
            typeX = "String";
        }
    }

    //MODIFIES: this
    //EFFECTS: determines colY type
    private void setTypeY() {
        try {
            double temp = (Double)colY.get(0);
            typeY = "Double";
        } catch (ClassCastException e) {
            typeY = "String";
        }
    }

    // REQUIRES: A non-null String
    // MODIFIES: this
    // EFFECTS: changes type of observation to appropriate data type for processing
    private Object changeObservationType(String data) {
        try {
            return Double.parseDouble(data);
        } catch (NumberFormatException e) {
            return data;
        }
    }

    // MODIFIES: this
    // EFFECTS: Swap columns with each other
    protected void swapColumns() {
        ArrayList<Object> temp;
        String tempType;
        temp = colX;
        tempType = typeX;
        colX = colY;
        typeX = typeY;
        colY = temp;
        typeY = tempType;
    }

    // EFFECTS: gets x column of data
    public ArrayList<Object> getColX() {
        return colX;
    }

    // EFFECTS: gets y column of data
    public ArrayList<Object> getColY() {
        return colY;
    }

    // EFFECTS: gets entire dataframe
    public ArrayList<Object>[] getData() {
        return new ArrayList[]{colX, colY};
    }

    // EFFECTS: gets header name of x column
    public String getHeaderX() {
        return headerX;
    }

    // EFFECTS: get header name of y column
    public String getHeaderY() {
        return headerY;
    }

    // EFFECTS: gets object type of x column
    public String getTypeX() {
        return typeX;
    }

    // EFFECTS: gets object type of y column
    public String getTypeY() {
        return typeY;
    }

    // Modelled off the toJson method found in the JsonSerializationDemo repository
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("header", getHeaderX() + "," + getHeaderY() + "\n");
        json.put("row", rowsToJson());
        return json;
    }

    // Modelled off the thingiesToJson method found in the JsonSerializationDemo repository
    // EFFECTS: returns columns in this dataset as a JSON array
    private JSONArray rowsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < getColX().size(); i++) {
            jsonArray.put(getColX().get(i) + "," + getColY().get(i) + "\n");
        }
        return jsonArray;
    }
}
