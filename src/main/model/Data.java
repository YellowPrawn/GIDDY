package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    private final ArrayList<Object> colX;
    private final ArrayList<Object> colY;
    private String headerX;
    private String headerY;
    private String typeX;
    private String typeY;

    public Data(String path) throws FileNotFoundException {
        colX = new ArrayList<>();
        colY = new ArrayList<>();
        readCSV(path);
        setTypeX();
        setTypeY();
    }
    // REQUIRES: existing CSV file
    // EFFECTS: reads CSV file into project

    private void readCSV(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        scanner.useDelimiter(",");
        double i = 0;
        while (scanner.hasNext()) {
            splitData(scanner.next(), i);
            i++;
        }
        scanner.close();
    }
    // REQUIRES: Scanner object and count
    // MODIFIES: this
    // EFFECTS: splits 2 column CSV data into 2 columns and headers

    private void splitData(String data, double i) {
        if (i == 0) {
            headerX = data;
        } else if (i == 1) {
            headerY = data;
        } else {
            if (i % 2 == 0) {
                colX.add(changeObservationType(data.replace("\n", "").replace("\r", "")));
            } else {
                colY.add(changeObservationType(data.replace("\n", "").replace("\r", "")));
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

    public ArrayList<Object> getColX() {
        return colX;
    }

    public ArrayList<Object> getColY() {
        return colY;
    }

    public ArrayList<Object>[] getData() {
        return new ArrayList[]{colX, colY};
    }

    public String getHeaderX() {
        return headerX;
    }

    public String getHeaderY() {
        return headerY;
    }

    public String getTypeX() {
        return typeX;
    }

    public String getTypeY() {
        return typeY;
    }
}
