package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {
    private ArrayList<Object> colX;
    private ArrayList<Object> colY;
    private String headerX;
    private String headerY;
    private String typeX;
    private String typeY;

    // REQUIRES: CSV-like scanner
    // EFFECTS: creates 2 columns, reads a "CSV", and sets data types of both columns
    public Data(Scanner scanner) throws FileNotFoundException {
        colX = new ArrayList<>();
        colY = new ArrayList<>();
        readCSV(scanner);
        setTypeX();
        setTypeY();
    }

    // REQUIRES: CSV-like scanner
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
        temp = colX;
        colX = colY;
        colY = temp;
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
}
