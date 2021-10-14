package model;

import java.util.ArrayList;

// A quantitative nx1 vector
public class Category {
    private String header;
    private ArrayList<Double> observations = new ArrayList<>();

    // EFFECTS: sets category headers
    public Category(String header) {
        this.header = header;
    }

    // MODIFIES: this
    // EFFECTS: adds element to Observations
    public void addElement(Double element) {
        observations.add(element);
    }

    // EFFECTS: gets observation at ith position
    public Double getObservation(int i) {
        return observations.get(i);
    }

    // EFFECTS: gets all observations
    public ArrayList<Double> getObservations() {
        return observations;
    }

    // EFFECTS: gets category headers
    public String getHeader() {
        return header;
    }
}
