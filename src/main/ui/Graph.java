package ui;

import model.Category;
import model.Data;
import model.MixedDataframe;
import model.QuantitativeDataframe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Graphical representation of dataframe
public class Graph extends JPanel {
    int screenWidth;
    int screenHeight;
    int labels = 10;
    Data data;
    int originX;
    int originY;
    int maxX;
    int maxY;
    int labelScale;
    double dfMaxX;
    double dfMaxY;

    // MODIFIES: this
    // EFFECTS: instantiates graph fields and scaling variables
    public Graph(Data data, int screenWidth, int screenHeight) throws ClassCastException {
        setSize(screenWidth, screenHeight);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.data = data;
        originX = screenWidth / 10;
        originY = screenHeight - screenHeight / 8;
        maxX = screenWidth - screenWidth / 10;
        maxY = screenHeight / 10;
        labelScale = screenHeight / 75;
    }

    // MODIFIES: this
    // EFFECTS: draws components according to given dataframe
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g);
        if (data.getTypeX().equals("Double") && data.getTypeY().equals("Double")) {
            getQuantitativeGraph(new QuantitativeDataframe(data), g);
        } else {
            getMixedGraph(new MixedDataframe(data), g);
        }
        drawYLabels(g);
    }

    // MODIFIES: this
    // EFFECTS: creates graph for mixed data
    private void getMixedGraph(MixedDataframe df, Graphics g) {
        dfMaxY = max(df.maxima());
        ArrayList<Category> categories = df.getCategories();
        ArrayList<String> categoryNames = new ArrayList<>();
        for (Category c :  categories) {
            categoryNames.add(c.getHeader());
        }
        drawBoxPlots(df, g);
        drawMixedXLabels(categoryNames, g);
    }

    // MODIFIES: this
    // EFFECTS: creates graph for quantitative data
    private void getQuantitativeGraph(QuantitativeDataframe df, Graphics g) {
        dfMaxX = df.getColXMax();
        dfMaxY = df.getColYMax();
        drawScatterPoints(df, g);
        drawQuantitativeXLabels(g);
    }

    // MODIFIES: this
    // EFFECTS: creates axes lines
    private void drawAxes(Graphics g) {
        g.drawLine(originX, originY, maxX + screenWidth / 25, originY);
        g.drawLine(originX, originY, originX, maxY - screenHeight / 25);
    }

    private void drawYLabels(Graphics g) {
        for (int i = labels; i > 0; i--) {
            g.drawLine(originX, originY * i / labels,
                    originX - labelScale, originY * i / labels);

            JLabel legend = new JLabel(String.valueOf(dfMaxY * (labels - i) / labels));
            legend.setBounds(originX - 4 * labelScale, originY * i / labels - labelScale,
                    screenWidth / (labelScale * 2), labelScale * 3 / 2);
            add(legend);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws labels for quantitative dataframes' x axis
    private void drawQuantitativeXLabels(Graphics g) {
        for (int i = 0; i < labels; i++) {
            g.drawLine(maxX * i / labels + originX,
                    originY,
                    maxX * i / labels + originX,
                    originY + labelScale);

            JLabel legend = new JLabel(String.valueOf(dfMaxX * i / labels));
            legend.setBounds(maxX * i / labels + originX,
                    originY + 2 * labelScale,
                    screenWidth / (labelScale * 2),
                    labelScale * 3 / 2);
            add(legend);
        }
    }


    // MODIFIES: this
    // EFFECTS: draws scatter points and mean value for quantitative dataframes
    private void drawScatterPoints(QuantitativeDataframe df, Graphics g) {
        for (int i = 0; i < df.getColX().size(); i++) {
            int scatterX = scale(df.getColX().get(i), df.getColXMax(), maxX);
            int scatterY = originY - scale(df.getColY().get(i), df.getColYMax(), originY - maxY);
            g.fillOval(scatterX,scatterY,screenWidth / 200, screenWidth / 200);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws labels for mixed dataframes' x axis
    private void drawMixedXLabels(ArrayList<String> categories, Graphics g) {
        int size = categories.size();
        for (int i = 1; i <= size; i++) {
            g.drawLine(maxX * i / (size + 1) + originX,
                    originY,
                    maxX * i / (size + 1) + originX,
                    originY + labelScale);

            JLabel legend = new JLabel(categories.get(i - 1));
            legend.setBounds(maxX * i / (size + 1) + originX - labelScale,
                    originY + 2 * labelScale,
                    screenWidth / (labelScale * 2),
                    labelScale * 3 / 2);
            add(legend);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws box plots for mixed dataframes
    private void drawBoxPlots(MixedDataframe df, Graphics g) {
        int size = df.getCategories().size();
        int globalMax = max(df.maxima());
        for (int i = 0; i < size; i++) {
            int center = maxX * (i + 1) / (size + 1) + originX;
            int max = scale(df.maxima().get(i), globalMax,originY - maxY);
            int min = scale(df.minima().get(i), globalMax,originY - maxY);
            int lowerFence = scale(df.get1QRs().get(i), globalMax,originY - maxY);
            int upperFence = scale(df.get3QRs().get(i), globalMax,originY - maxY);
            int median = scale(df.getMedians().get(i), globalMax,originY - maxY);
            int quartileRange = scale(df.getIQRs().get(i), globalMax,originY - maxY);
            drawFence(lowerFence, center, size, g);
            drawFence(upperFence, center, size, g);
            drawFence(median, center, size, g);
            drawIQR(quartileRange, lowerFence, center, size, g);
            drawWhiskers(min, max, lowerFence, upperFence, center, size, g);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws fences for mixed dataframes
    private void drawFence(int i, int center, int size, Graphics g) {
        g.drawLine(center - screenWidth / (4 * size),
                originY - i,
                center + screenWidth / (4 * size),
                originY - i);
    }

    // MODIFIES: this
    // EFFECTS: draws IQR "walls" for mixed dataframes
    private void drawIQR(int i, int lowerFence, int center, int size, Graphics g) {
        g.drawLine(center - screenWidth / (4 * size),
                originY - lowerFence,
                center - screenWidth / (4 * size),
                originY - (lowerFence + i));
        g.drawLine(center + screenWidth / (4 * size),
                originY - lowerFence,
                center + screenWidth / (4 * size),
                originY - (lowerFence + i));
    }

    // MODIFIES: this
    // EFFECTS: draws whiskers, min, and max values of mixed dataframes
    private void drawWhiskers(int min, int max, int lowerFence, int upperFence, int center, int size, Graphics g) {
        // whiskers
        g.drawLine(center,
                originY - lowerFence,
                center,
                originY - min);
        g.drawLine(center,
                originY - upperFence,
                center,
                originY - max);
        // extrema
        g.drawLine(center - screenWidth / (4 * size),
                originY - min,
                center + screenWidth / (4 * size),
                originY - min);
        g.drawLine(center - screenWidth / (4 * size),
                originY - max,
                center + screenWidth / (4 * size),
                originY - max);
    }

    // REQUIRES: screenBound must be either maxX or maxY
    // EFFECTS: scales given number as a proportion size of the screen
    //          then scales accordingly to a screen axis (width/height)
    private int scale(double i, double max, int screenBound) {
        return (int) Math.round((i / max) * screenBound);
    }

    // EFFECTS: return largest integer of given array
    private int max(ArrayList<Double> observations) {
        double max = 0;
        for (Double d : observations) {
            if (d > max) {
                max = d;
            }
        }
        return (int) max;
    }
}
