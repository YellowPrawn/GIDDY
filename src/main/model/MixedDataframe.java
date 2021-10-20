package model;

import java.util.ArrayList;
import java.util.Objects;

// Operations for nx2 dataframes which consists of a nx1 categorical vector and a 1xn quantitative vector
public class MixedDataframe extends QuantitativeAnalyzer {

    private final ArrayList<Category> categories = new ArrayList<>();

    // REQUIRES: A column of Strings and a column of Doubles
    // EFFECTS: organizes data for processing
    public MixedDataframe(Data data) throws ClassCastException {
        super(data);
        correctColumnOrder();
        makeCategories();
        addCategoryData();
    }


    // MODIFIES: this
    // EFFECTS: ensures dataframe is in order {String, Double}
    private void correctColumnOrder() {
        if (!Objects.equals(data.getTypeX(), "String")) {
            data.swapColumns();
        }
    }

    // MODIFIES: this
    // EFFECTS: splits categorical data into separate Category objects
    private void makeCategories() {
        for (int i = 0; i < data.getColX().size(); i++) {
            if (!scanExistingCategories(i)) {
                categories.add(new Category((String) data.getColX().get(i)));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds each observation to corresponding category
    private void addCategoryData() throws ClassCastException {
        for (int i = 0; i < data.getColX().size(); i++) {
            for (Category c : categories) {
                if (c.getHeader().equals(data.getColX().get(i))) {
                    c.addElement((Double) data.getColY().get(i));
                }
            }
        }
    }

    // REQUIRES: i = [0, data.getColX().size()]
    // EFFECTS: checks if ith element in colX exists as a category
    private boolean scanExistingCategories(int i) {
        boolean existingCategory = false;
        for (Category j : categories) {
            if (j.getHeader().equals(data.getColX().get(i))) {
                existingCategory = true;
                break;
            }
        }
        return existingCategory;
    }

    // EFFECTS: returns mean of all categories
    public ArrayList<Double> getMeans() {
        ArrayList<Double> means = new ArrayList<>();
        for (Category i : categories) {
            means.add(getMean(i.getObservations()));
        }
        return means;
    }

    // EFFECTS: returns SD of all categories
    public ArrayList<Double> getSDs() {
        ArrayList<Double> sds = new ArrayList<>();
        for (Category i : categories) {
            sds.add(getSD(i.getObservations()));
        }
        return sds;
    }

    // EFFECTS: returns median of all categories
    public ArrayList<Double> getMedians() {
        ArrayList<Double> medians = new ArrayList<>();
        for (Category i : categories) {
            medians.add(getMedian(i.getObservations()));
        }
        return medians;
    }

    // EFFECTS: returns 1st quartile of all categories
    public ArrayList<Double> get1QRs() {
        ArrayList<Double> quartiles = new ArrayList<>();
        for (Category i : categories) {
            quartiles.add(get1QR(i.getObservations()));
        }
        return quartiles;
    }

    // EFFECTS: returns 3rd quartile of all categories
    public ArrayList<Double> get3QRs() {
        ArrayList<Double> quartiles = new ArrayList<>();
        for (Category i : categories) {
            quartiles.add(get3QR(i.getObservations()));
        }
        return quartiles;
    }

    // EFFECTS: returns interquartile ranges of all categories
    public ArrayList<Double> getIQRs() {
        ArrayList<Double> quartiles = new ArrayList<>();
        for (Category i : categories) {
            quartiles.add(getIQR(i.getObservations()));
        }
        return quartiles;
    }

    // EFFECTS: returns maximum of all categories
    public ArrayList<Double> maxima() {
        ArrayList<Double> maxima = new ArrayList<>();
        for (Category i : categories) {
            maxima.add(max(i.getObservations()));
        }
        return maxima;
    }

    // EFFECTS: returns minimum of all categories
    public ArrayList<Double> minima() {
        ArrayList<Double> minima = new ArrayList<>();
        for (Category i : categories) {
            minima.add(min(i.getObservations()));
        }
        return minima;
    }

    // EFFECTS: returns sum of all categories
    public ArrayList<Double> sums() {
        ArrayList<Double> sums = new ArrayList<>();
        for (Category i : categories) {
            sums.add(sum(i.getObservations()));
        }
        return sums;
    }

    // EFFECTS: gets all categories in dataframe
    public ArrayList<Category> getCategories() {
        return categories;
    }
}
