package ui;

import model.Category;
import model.Data;
import model.MixedDataframe;
import model.QuantitativeDataframe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

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
    int predictionX;
    int predictionY;
    boolean drawPrediction = false;
    Main main;

    // MODIFIES: this
    // EFFECTS: instantiates graph fields and scaling variables
    public Graph(Data data, int screenWidth, int screenHeight, Main main) throws ClassCastException {
        setSize(screenWidth, screenHeight);
        setLayout(null);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.data = data;
        this.main = main;
        originX = screenWidth / 10;
        originY = screenHeight - screenHeight / 8;
        maxX = screenWidth - screenWidth / 10;
        maxY = screenHeight / 10;
        labelScale = screenHeight / 75;
        drawHeaders();
    }

    // MODIFIES: this
    // EFFECTS: draws axis headers
    private void drawHeaders() {
        JLabel headerX = new JLabel(data.getHeaderX());
        JLabel headerY = new JLabel(data.getHeaderY());

        headerX.setBounds(maxX + screenWidth / 25,
                originY + 2 * labelScale,
                screenWidth / (labelScale * 2),
                labelScale * 3 / 2);
        headerY.setBounds(originX - 4 * labelScale,
                maxY - screenHeight / 25,
                screenWidth / (labelScale * 2),
                labelScale * 3 / 2);

        add(headerX);
        add(headerY);
    }

    // MODIFIES: this
    // EFFECTS: draws components according to given dataframe
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g);
        if (data.getTypeX().equals("Double") && data.getTypeY().equals("Double")) {
            getQuantitativeGraph(new QuantitativeDataframe(data), g);
            main.predictionEntryText.setVisible(true);
            main.predictionEntryField.setVisible(true);
            main.predictionEntryButton.setVisible(true);
        } else {
            getMixedGraph(new MixedDataframe(data), g);
        }
        drawYLabels(g);
        if (drawPrediction) {
            drawPoint(predictionX, predictionY, Color.BLUE, g);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates graph for quantitative data
    private void getQuantitativeGraph(QuantitativeDataframe df, Graphics g) {
        dfMaxX = df.getColXMax();
        dfMaxY = df.getColYMax();
        drawScatterPoints(df, g);
        drawQuantitativeXLabels(g);
        predictionOverlay(df, g);
    }

    // MODIFIES: this
    // EFFECTS: creates axes lines
    private void drawAxes(Graphics g) {
        g.drawLine(originX, originY, maxX + screenWidth / 25, originY);
        g.drawLine(originX, originY, originX, maxY - screenHeight / 25);
    }

    // MODIFIES: this
    // EFFECTS: creates labels for a dataframe's y axis
    private void drawYLabels(Graphics g) {
        for (int i = labels; i >= 0; i--) {
            // scaling by 100 then dividing by 100 prevents unintended rounding of small values
            double labelLegend = ((100 * i / labels) * dfMaxY) / 100;
            int labelY = scaleY(labelLegend);
            g.drawLine(originX,
                    labelY,
                    originX - labelScale,
                    labelY);

            JLabel legend = new JLabel(String.valueOf(labelLegend));
            legend.setBounds(originX - 4 * labelScale,
                    labelY - labelScale,
                    screenWidth / (labelScale * 2),
                    labelScale * 3 / 2);
            add(legend);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws labels for quantitative dataframes' x axis
    private void drawQuantitativeXLabels(Graphics g) {
        for (int i = 0; i <= labels; i++) {
            double labelLegend = ((100 * i / labels) * dfMaxX) / 100;
            int labelX = scaleX(labelLegend);
            g.drawLine(labelX,
                    originY,
                    labelX,
                    originY + labelScale);

            JLabel legend = new JLabel(String.valueOf(labelLegend));
            legend.setBounds(labelX,
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
            int scatterX = scaleX(df.getColX().get(i));
            int scatterY = scaleY(df.getColY().get(i));
            drawPoint(scatterX, scatterY, Color.black, g);
        }
        int meanX = scaleX(df.getColXMean());
        int meanY = scaleY(df.getColYMean());
        drawPoint(meanX, meanY, Color.red, g);
    }

    // MODIFIES: this
    // EFFECTS: creates graph for mixed data
    private void getMixedGraph(MixedDataframe df, Graphics g) {
        dfMaxX = max(df.maxima());
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
    // EFFECTS: draws labels for mixed dataframes' x axis
    private void drawMixedXLabels(ArrayList<String> categories, Graphics g) {
        int size = categories.size();
        for (int i = 1; i <= size; i++) {
            int labelX = maxX * i / (size + 1) + originX;
            g.drawLine(labelX,
                    originY,
                    labelX,
                    originY + labelScale);

            JLabel legend = new JLabel(categories.get(i - 1));
            legend.setBounds(labelX - labelScale,
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
        for (int i = 0; i < size; i++) {
            int center = maxX * (i + 1) / (size + 1) + originX;
            int max = scaleY(df.maxima().get(i));
            int min = scaleY(df.minima().get(i));
            int lowerFence = scaleY(df.get1QRs().get(i));
            int upperFence = scaleY(df.get3QRs().get(i));
            int median = scaleY(df.getMedians().get(i));
            int quartileRange = abs(upperFence - lowerFence);
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
                i,
                center + screenWidth / (4 * size),
                i);
    }

    // MODIFIES: this
    // EFFECTS: draws IQR "walls" for mixed dataframes
    private void drawIQR(int i, int lowerFence, int center, int size, Graphics g) {
        g.drawLine(center - screenWidth / (4 * size),
                    lowerFence,
                center - screenWidth / (4 * size),
                    (lowerFence - i));
        g.drawLine(center + screenWidth / (4 * size),
                    lowerFence,
                center + screenWidth / (4 * size),
                    (lowerFence - i));
    }

    // MODIFIES: this
    // EFFECTS: draws whiskers, min, and max values of mixed dataframes
    private void drawWhiskers(int min, int max, int lowerFence, int upperFence, int center, int size, Graphics g) {
        // whiskers
        g.drawLine(center,
                lowerFence,
                center,
                min);
        g.drawLine(center,
                upperFence,
                center,
                max);
        // extrema
        g.drawLine(center - screenWidth / (4 * size),
                min,
                center + screenWidth / (4 * size),
                min);
        g.drawLine(center - screenWidth / (4 * size),
                max,
                center + screenWidth / (4 * size),
                max);
    }

    // REQUIRES: i must be an x coordinate
    // EFFECTS: scales given number as a proportion size of the screen
    //          then scales accordingly to screenWidth
    public int scaleX(double i) {
        return (int) Math.round((i / dfMaxX) * (maxX - originX) + originX);
    }

    // REQUIRES: i must be a y coordinate
    // EFFECTS: scales given number as a proportion size of the screen
    //          then scales accordingly to screenHeight
    public int scaleY(double i) {
        return (int) Math.round((originY - (((i / dfMaxY) * (originY - maxY)))));
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

    // MODIFIES: this, main, main.ui, main.predictionEntryText, main.predictionEntryButton
    // EFFECTS: initiates prediction features for quantitative dataframes
    private void predictionOverlay(QuantitativeDataframe df, Graphics g) {
        main.predictionEntryButton.addActionListener(e -> {
            try {
                double target = Double.parseDouble(main.predictionEntryField.getText());
                double prediction = df.linearRegression(target);
                predictionX = scaleX(target);
                predictionY = scaleY(prediction);
                drawPrediction = true;
            } catch (Exception exception) {
                main.predictionEntryText.setText("incompatible prediction made. Try again");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: draws a point on the graph
    private void drawPoint(int x, int y, Color colour, Graphics g) {
        g.setColor(colour);
        g.fillOval(x,y,screenWidth / 200, screenWidth / 200);
        g.setColor(Color.BLACK);
        repaint();
    }
}
