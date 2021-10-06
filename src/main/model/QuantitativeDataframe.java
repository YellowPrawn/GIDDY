package model;

import java.util.ArrayList;

public class QuantitativeDataframe extends QuantitativeAnalyzer {
    ArrayList<Double> colX;
    ArrayList<Double> colY;

    public QuantitativeDataframe(Data data) {
        super(data);
        colX = toInt(data.getColX());
        colY = toInt(data.getColX());
    }

    public double getColXMean() {
        return getMean(colX);
    }

    public double getColYMean() {
        return getMean(colY);
    }

    public double getColXMedian() {
        return getMedian(colX);
    }

    public double getColYMedian() {
        return getMedian(colY);
    }

    public double getColXSD() {
        return getSD(colX);
    }

    public double getColYSD() {
        return getSD(colY);
    }

    public double getColX1QR() {
        return get1QR(colX);
    }

    public double getColY1QR() {
        return get1QR(colY);
    }

    public double getColXIqR() {
        return getIQR(colX);
    }

    public double getColYIqR() {
        return getIQR(colY);
    }

    public double getColXMin() {
        return min(colX);
    }

    public double getColYMin() {
        return min(colY);
    }

    public double getColXMax() {
        return max(colX);
    }

    public double getColYMax() {
        return max(colY);
    }

    public double getColXSum() {
        return sum(colX);
    }

    public double getColYSum() {
        return sum(colY);
    }

    public double correlationCoefficient() {
        double standardSum = 0;
        for (int i = 0; i < colX.size(); i++) {
            standardSum += standardizedScore(colX.get(i), colX) * standardizedScore(colY.get(i), colY);
        }
        return standardSum / colX.size();
    }

    public double regressionSlope() {
        return correlationCoefficient() * getSD(colY) / getSD(colX);
    }

    public double regressionIntercept() {
        return getMean(colY) - regressionSlope() * getMean(colX);
    }

    public double linearRegression(int x) {
        return regressionIntercept() + regressionSlope() * x;
    }
}
