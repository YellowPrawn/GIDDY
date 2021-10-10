package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Category {
    private String header;
    private ArrayList<Double> observations = new ArrayList<>();

    public Category(String header) {
        this.header = header;
    }

    // MODIFIES: this
    // EFFECTS: adds element to Observations
    public void addElement(Double element) {
        observations.add(element);
    }

    public Double getElement(int i) {
        return observations.get(i);
    }

    public ArrayList<Double> getObservations() {
        return observations;
    }

    public String getHeader() {
        return header;
    }
}
