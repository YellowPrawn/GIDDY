package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class QuantitativeAnalyzer {
    Data data;

    public QuantitativeAnalyzer(Data data) {
        this.data = data;
    }

    // EFFECTS: returns mean of column
    private double getMean(ArrayList<Double> col) {
        return sum(col) / col.size();
    }

    // EFFECTS: returns median of column
    private double getMedian(ArrayList<Double> col) {
        int midIndex = round(col.size() / 2);
        return col.get(midIndex);
    }

    // EFFECTS: returns standard deviation of column
    private double getSD(ArrayList<Double> col) {
        return sqrt(getVariance(col));
    }

    // EFFECTS: returns variance of column
    private double getVariance(ArrayList<Double> col) {
        double sum = 0;
        double mean = getMean(col);
        for (double i : col) {
            sum += (i - mean) * (i - mean);
        }
        return sum / col.size();
    }

    // EFFECTS: returns 1st quartile of column
    private double get1QR(ArrayList<Double> col) {
        int quartile = round(col.size() / 4);
        return col.get(quartile);
    }

    // EFFECTS: returns 3rd quartile of column
    private double get3QR(ArrayList<Double> col) {
        int quartile = round(3 * col.size() / 4);
        return col.get(quartile);
    }

    // EFFECTS: returns interquartile range of column
    private double getIQR(ArrayList<Double> col) {
        return get3QR(col) - get1QR(col);
    }

    // EFFECTS: returns maximum value of column
    private double max(ArrayList<Double> col) {
        double max = col.get(0);
        for (double i : col) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    // EFFECTS: returns minimum value of column
    private double min(ArrayList<Double> col) {
        double min = col.get(0);
        for (Double i : col) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    // EFFECTS: returns sum of values of column
    private double sum(ArrayList<Double> col) {
        double sum = 0;
        for (Double i : col) {
            sum += i;
        }
        return sum;
    }

    // EFFECTS: returns sorted column
    private ArrayList<Double> sort(ArrayList<Double> col) {
        Collections.sort(col);
        return col;
    }
}
