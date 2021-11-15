package ui;

import model.Data;
import model.MixedDataframe;
import model.QuantitativeDataframe;

import javax.swing.*;
import java.awt.*;

// Graphical representation of datqframe
public class Graph extends JPanel {
    JPanel ui;
    int screenWidth;
    int screenHeight;

    // MODIFIES: this
    // EFFECTS: Determines and creates appropriate graph for the given data
    public Graph(Data data, int screenWidth, int screenHeight) throws ClassCastException {
        setMinimumSize(new Dimension(screenWidth, screenHeight * 3 / 4));
        initiateBackdrop();
        if (data.getTypeX().equals("Double") && data.getTypeY().equals("Double")) {
            getQuantitativeGraph(new QuantitativeDataframe(data));
        } else {
            getMixedGraph(new MixedDataframe(data));
        }
    }

    // MODIFIES: this
    // EFFECTS: creates graph for mixed data
    private void getMixedGraph(MixedDataframe df) {
        Axes axes = new Axes(df, screenWidth, screenHeight);
        ui.add(axes);
    }

    // MODIFIES: this
    // EFFECTS: creates graph for quantitative data
    private void getQuantitativeGraph(QuantitativeDataframe df) {
        Axes axes = new Axes(df, screenWidth, screenHeight);
        ui.add(axes);
        for (int i = 0; i < df.getColX().size(); i++) {
            int scatterX = scale(df.getColX().get(i), df.getColXMax(), screenWidth);
            int scatterY = scale(df.getColY().get(i), df.getColYMax(), screenHeight);
            ScatterPoint point = new ScatterPoint(scatterX, scatterY);
            ui.add(point);
        }
    }

    // REQUIRES: screenBound must be either screenWidth or screenHeight
    // EFFECTS: scales given number as a proportion size of the screen
    //          then scales accordingly to a screen axis (width/height)
    private int scale(double i, double max, int screenBound) {
        return (int) Math.round(i / max * screenBound);
    }

    // Modelled off the createTools method found in the SimpleDrawingPlayer-Complete repository
    // MODIFIES: this
    // EFFECTS: creates graph backdrop/layout
    public void initiateBackdrop() {
        ui = new JPanel();
        ui.setLayout(new GridLayout(0,1));
        ui.setSize(new Dimension(0, 0));
        add(ui, BorderLayout.SOUTH);
    }
}
