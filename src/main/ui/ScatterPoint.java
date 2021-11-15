package ui;

import model.QuantitativeDataframe;

import javax.swing.*;
import java.awt.*;

public class ScatterPoint extends JPanel {
    int scatterX;
    int scatterY;

    public ScatterPoint(int x, int y) {
        super();
    }

    // MODIFIES: this
    // EFFECTS: creates individual scatter point
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillOval(scatterX,scatterY,2,2);
    }
}
