package model;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;

// An interface containing operations for dataframes that consist of at least 1 nx1 vector
public abstract class QuantitativeAnalyzer {
    protected Data data;

    public QuantitativeAnalyzer(Data data) {
        this.data = data;
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: returns mean of column
    protected double getMean(ArrayList<Double> col) {
        return sum(col) / col.size();
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: returns median of column
    protected double getMedian(ArrayList<Double> col) {
        int midIndex = round(col.size() / 2);
        return sort(col).get(midIndex);
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: returns standard deviation of column
    protected double getSD(ArrayList<Double> col) {
        double sum = 0;
        double mean = getMean(col);
        for (double i : col) {
            sum += (i - mean) * (i - mean);
        }
        return sqrt(sum / col.size());
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: returns 1st quartile of column
    protected double get1QR(ArrayList<Double> col) {
        int quartile = round(col.size() / 4);
        return sort(col).get(quartile);
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: returns 3rd quartile of column
    protected double get3QR(ArrayList<Double> col) {
        int quartile = round(3 * col.size() / 4);
        return sort(col).get(quartile);
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: returns interquartile range of column
    protected double getIQR(ArrayList<Double> col) {
        return get3QR(col) - get1QR(col);
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: returns maximum value of column
    protected double max(ArrayList<Double> col) {
        double max = col.get(0);
        for (double i : col) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: returns minimum value of column
    protected double min(ArrayList<Double> col) {
        double min = col.get(0);
        for (Double i : col) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: returns sum of values of column
    protected double sum(ArrayList<Double> col) {
        double sum = 0;
        for (Double i : col) {
            sum += i;
        }
        return sum;
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: returns sorted column
    protected ArrayList<Double> sort(ArrayList<Double> col) {
        Collections.sort(col);
        return col;
    }

    // REQUIRES: non-zero ArrayList<Double>
    // MODIFIES: this
    // EFFECTS: Changes all elements in a column to doubles
    protected ArrayList<Double> toDouble(ArrayList<Object> col) {
        ArrayList<Double> convertedList = new ArrayList<>();
        for (Object i : col) {
            convertedList.add((Double) i);
        }
        return convertedList;
    }
}
