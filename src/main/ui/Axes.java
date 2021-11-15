package ui;

import model.MixedDataframe;
import model.QuantitativeDataframe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Axes of graph
public class Axes extends JPanel {
    private int labelCount = 10;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int axisWidth;
    private int axisHeight;

    // MODIFIES: this
    // EFFECTS: initializes fields and extrapolates maximum values for quantitative dataframes
    public Axes(QuantitativeDataframe df, int screenWidth, int screenHeight) {
        super();
        minX = (int) df.getColXMin();
        minY = (int) df.getColYMin();
        maxX = (int) df.getColXMax() + 1;
        maxY = (int) df.getColYMax() + 1;
        axisWidth = screenWidth;
        axisHeight = screenHeight;
    }

    // MODIFIES: this
    // EFFECTS: initializes fields and extrapolates maximum values for mixed dataframes
    public Axes(MixedDataframe df, int screenWidth, int screenHeight) {
        super();
        minX = 0;
        minY = 0;
        maxX = (int) df.getCategories().size();
        maxY = max(df.maxima()) + 1;
        axisWidth = screenWidth;
        axisHeight = screenHeight;
    }

    // MODIFIES: this
    // EFFECTS: creates axes lines
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,axisHeight,axisWidth,2);
        g.fillRect(0,0,2,axisHeight);
    }

    // MODIFIES: this
    // EFFECTS: places "labelCount" labels on axes (for quantitative dataframes)
    private void createLabels(Graphics g) {
        for (int i = 0; i < labelCount; i++) {
            g.setColor(Color.BLACK);
            g.fillRect(axisWidth / i,axisHeight,1,5);
        }
        for (int i = 0; i < labelCount; i++) {
            g.setColor(Color.BLACK);
            g.fillRect(0,axisHeight / i,5,1);
        }
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
