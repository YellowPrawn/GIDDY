package model;

import java.util.ArrayList;

// Operations for nx2 dataframes which consists of 2 1xn quantitative vectors
public class QuantitativeDataframe extends QuantitativeAnalyzer {
    protected ArrayList<Double> colX;
    protected ArrayList<Double> colY;

    // REQUIRES: non-zero ArrayList<Double>s in data
    // EFFECTS: sets both column types to double
    public QuantitativeDataframe(Data data) {
        super(data);
        colX = toDouble(data.getColX());
        colY = toDouble(data.getColY());
    }

    // EFFECTS: gets colX
    public ArrayList<Double> getColX() {
        return colX;
    }

    // EFFECTS: gets colY
    public ArrayList<Double> getColY() {
        return colY;
    }

    // EFFECTS: gets mean of column x
    public double getColXMean() {
        return getMean(colX);
    }

    // EFFECTS: gets mean of column y
    public double getColYMean() {
        return getMean(colY);
    }

    // EFFECTS: gets median of column x
    public double getColXMedian() {
        return getMedian(colX);
    }

    // EFFECTS: gets median of column y
    public double getColYMedian() {
        return getMedian(colY);
    }

    // EFFECTS: gets standard deviation of column x
    public double getColXSD() {
        return getSD(colX);
    }

    // EFFECTS: gets standard deviation of column y
    public double getColYSD() {
        return getSD(colY);
    }

    // EFFECTS: gets 1st quartile of column x
    public double getColX1QR() {
        return get1QR(colX);
    }

    // EFFECTS: gets 1st quartile of column y
    public double getColY1QR() {
        return get1QR(colY);
    }

    // EFFECTS: gets 3rd quartile of column x
    public double getColX3QR() {
        return get3QR(colX);
    }

    // EFFECTS: gets 3rd quartile of column y
    public double getColY3QR() {
        return get3QR(colY);
    }

    // EFFECTS: gets interquartile range of column x
    public double getColXIqR() {
        return getIQR(colX);
    }

    // EFFECTS: gets interquartile range of column y
    public double getColYIqR() {
        return getIQR(colY);
    }

    // EFFECTS: gets minimum of column x
    public double getColXMin() {
        return min(colX);
    }

    // EFFECTS: gets minimum of column y
    public double getColYMin() {
        return min(colY);
    }

    // EFFECTS: gets maximum of column x
    public double getColXMax() {
        return max(colX);
    }

    // EFFECTS: gets maximum of column y
    public double getColYMax() {
        return max(colY);
    }

    // EFFECTS: gets sum of column x
    public double getColXSum() {
        return sum(colX);
    }

    // EFFECTS: gets sum of column y
    public double getColYSum() {
        return sum(colY);
    }

    // REQUIRES: non-zero ArrayList<Double>
    // EFFECTS: gets standardized score of dataframe
    protected double standardizedScore(double i, ArrayList<Double> col) {
        return i - getMean(col) / getSD(col);
    }

    // EFFECTS: finds the correlation coefficient of the dataframe
    public double correlationCoefficient() {
        double standardSum = 0;
        for (int i = 0; i < colX.size(); i++) {
            standardSum += standardizedScore(colX.get(i), colX) * standardizedScore(colY.get(i), colY);
        }
        return standardSum / colX.size();
    }

    // EFFECTS: finds the slope of regression of the dataframe
    public double regressionSlope() {
        return correlationCoefficient() * getSD(colY) / getSD(colX);
    }

    // EFFECTS: finds the intercept of regression of the dataframe
    public double regressionIntercept() {
        return getMean(colY) - regressionSlope() * getMean(colX);
    }

    // EFFECTS: finds the equation for linear regression of the dataframe
    public double linearRegression(double x) {
        return regressionIntercept() + regressionSlope() * x;
    }
}
