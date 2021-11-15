package ui;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.Category;
import model.Data;
import model.MixedDataframe;
import model.QuantitativeDataframe;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;

public class Main extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    JButton b1;
    JButton b2;
    JLabel text;
    JLabel error;

    JButton readButton;
    JButton writeButton;
    JButton fileSearchButton;
    JButton saveButton;
    JButton predictionEntryButton;

    JLabel selectMode;
    JLabel fileName;
    JLabel fileNotFoundError;
    JLabel fileNameEntryText;
    JLabel incompatibleDataframeError;
    JLabel predictionEntryText;

    JTextField fileSearchField;
    JTextField saveFileField;
    JTextField predictionEntryField;

    JPanel ui;
    JPanel startPage;
    JPanel readPage;
    JPanel writePage;
    JPanel mixedDataPage;
    JPanel quantitativeDataPage;

    JTextField field;
    CardLayout cardLayout;

    public static void main(String[] args) {
        new Main();
    }

    // MODIFIES: this
    // EFFECTS: Initializes program
    public Main() {
        super("GIDDY");
        cardLayout = new CardLayout();
        initializeGraphics();
    }

    // Modelled off the initializeGraphics method found in the SimpleDrawingPlayer-Complete repository
    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this program will operate, starts mode selection
    public void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        initiateBackdrop();
        initiateButtons();
        initiateLabels();
        initiateFields();
        initiatePages();
        selectMode();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Modelled off the createTools method found in the SimpleDrawingPlayer-Complete repository
    // MODIFIES: this
    // EFFECTS: creates program backdrop/layout
    public void initiateBackdrop() {
        ui = new JPanel();
        ui.setLayout(cardLayout);
        ui.setSize(new Dimension(0, 0));
        add(ui, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: creates program buttons
    public void initiateButtons() {
        readButton = new JButton("read existing file");
        writeButton = new JButton("write new data");
        fileSearchButton = new JButton("search");
        saveButton = new JButton("save");
        predictionEntryButton = new JButton("predict");
    }

    // MODIFIES: this
    // EFFECTS: creates program labels
    public void initiateLabels() {
        selectMode = new JLabel("select mode:", SwingConstants.CENTER);
        fileName = new JLabel("enter file name", SwingConstants.CENTER);
        fileNotFoundError = new JLabel("file not found. Try again");
        fileNameEntryText = new JLabel("enter data: (separated with commas)");
        incompatibleDataframeError = new JLabel("incompatible dataframe entered. Try again\n");
        predictionEntryText = new JLabel("enter x value for prediction: ");
    }

    // MODIFIES: this
    // EFFECTS: creates program fields
    public void initiateFields() {
        fileSearchField = new JTextField(20);
        saveFileField = new JTextField(20);
        predictionEntryField = new JTextField(20);
    }

    // MODIFIES: this, ui, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage
    // EFFECTS: creates necessary JPanels for displaying all "pages" of program
    public void initiatePages() {
        startPage = new JPanel();
        readPage = new JPanel();
        writePage = new JPanel();
        mixedDataPage = new JPanel();
        quantitativeDataPage = new JPanel();

        initiateStartPage();
        initiateReadPage();
    }

    // MODIFIES: this, ui, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage
    // EFFECTS: sets up functionality for startPage
    public void initiateStartPage() {
        ui.add(startPage, "startPage");
        startPage.add(selectMode);
        startPage.add(readButton);
        startPage.add(writeButton);

        readButton.addActionListener(e -> read());

        writeButton.addActionListener(e -> write());
    }

    // MODIFIES: this, ui, readPage, mixedDataPage, QuantitativeDataPage
    // EFFECTS: sets up functionality for startPage
    public void initiateReadPage() {
        ui.add(readPage, "readPage");
        readPage.add(fileName);
        readPage.add(fileSearchField);
        readPage.add(fileSearchButton);

        fileSearchButton.addActionListener(e -> {
            try {
                JsonReader reader = new JsonReader("src/main/data/" + field.getText() + ".json");
                Data data = reader.read();
                processData(data);
            } catch (IOException exception) {
                readPage.remove(fileNotFoundError);
                readPage.add(fileNotFoundError);
                read();
            }
        });
    }

    // MODIFIES: this, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage
    // EFFECTS: Allows user to choose between reading existing data or to input data manually.
    public void selectMode() {
        cardLayout.show(ui, "startPage");
    }

    // REQUIRES: valid csv file name in ../data/
    // MODIFIES: this, ui, readPage, mixedDataPage, QuantitativeDataPage
    // EFFECTS:  reads existing file in ../data/ and loads it into program
    public void read() {
        cardLayout.show(ui, "readPage");
    }

    // REQUIRES: valid CSV-like data
    // EFFECT: allows user to input data in CSV format and loads it into program
    public void write() {
        text = new JLabel("enter data: (separated with commas)");
        field = new JTextField();
        b1 = new JButton("search");
        b1.addActionListener(e -> {
            Data data = new Data(field.getText());
            resetComponents();
            processData(data);
            saveData(data);
        });
        ui.add(text);
        ui.add(field);
        ui.add(b1);
    }

    // REQUIRES: non-empty Data object
    // EFFECTS: Saves raw data written by user into csv file. If selection is invalid, restart this function
    public void saveData(Data data) {
        b1 = new JButton("save raw data");
        b1.addActionListener(e -> {
            resetComponents();
            text = new JLabel("enter file name:");
            field = new JTextField();
            b1 = new JButton("save");
            b1.addActionListener(e2 -> {
                JsonWriter writer = new JsonWriter("src/main/data/" + field.getText() + ".json");
                try {
                    writer.open();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                writer.write(data);
                writer.close();
            });
            ui.add(text);
            ui.add(field);
            ui.add(b1);
        });
        ui.add(b1);
    }

    // REQUIRES: non-empty Data object with 2 Double columns or a String column + Double column
    // EFFECTS: processes data into correct outputs. If data is incompatible, restart program
    public void processData(Data data) {
        try {
            Graph graph = new Graph(data, WIDTH, HEIGHT);
            ui.add(graph);
        } catch (ClassCastException e) {
            error = new JLabel("incompatible dataframe entered. Try again\n");
            ui.add(error);
            selectMode();
        }
    }

    // REQUIRES: non-empty Data object
    // EFFECTS: prints all summary statistics in dataframe
    public static void getQuantitativeSummary(QuantitativeDataframe df) {
        System.out.println("optimal plot type: Scatterplot");
        System.out.println("mean (x): " + df.getColXMean());
        System.out.println("mean (y): " + df.getColYMean());
        System.out.println("median (x): " + df.getColXMedian());
        System.out.println("median (y): " + df.getColYMedian());
        System.out.println("1st quartile (x): " + df.getColX1QR());
        System.out.println("1st quartile (y): " + df.getColY1QR());
        System.out.println("3rd quartile (x): " + df.getColX3QR());
        System.out.println("3rd quartile (y): " + df.getColY3QR());
        System.out.println("interquartile range (x): " + df.getColXIqR());
        System.out.println("interquartile range (y): " + df.getColYIqR());
        System.out.println("min (x): " + df.getColXMin());
        System.out.println("min (y): " + df.getColYMin());
        System.out.println("max (x): " + df.getColXMax());
        System.out.println("max (y): " + df.getColYMax());
        System.out.println("sum (x): " + df.getColXSum());
        System.out.println("sum (y): " + df.getColYSum());
        System.out.println("linear regression: " + df.regressionIntercept() + " + " + df.regressionSlope() + " * x");
        System.out.println("enter x value for prediction: ");
        //System.out.println("prediction: " + df.linearRegression(scanner.nextDouble()));
    }

    // REQUIRES: Enon-empty Data object
    // EFFECTS: prints all summary statistics in dataframe then exits program
    public static void getMixedSummary(MixedDataframe df) {
        System.out.println("optimal plot type: boxplot");
        System.out.println("data is mapped according to the following order: ");
        ArrayList<String> headers = new ArrayList<>();
        for (Category c : df.getCategories()) {
            headers.add(c.getHeader());
        }
        System.out.println(headers + "\n");
        System.out.println("means: " + df.getMeans());
        System.out.println("medians: " + df.getMedians());
        System.out.println("1st quartiles: " + df.get1QRs());
        System.out.println("3rd quartiles: " + df.get3QRs());
        System.out.println("interquartile ranges: " + df.getIQRs());
        System.out.println("minima: " + df.minima());
        System.out.println("maxima: " + df.maxima());
        System.out.println("sums: " + df.sums());
    }

    // MODIFIES: this
    // EFFECTS: resets all JFrame components in screen
    public void resetComponents() {
        try {
            b1.setVisible(false);
            b2.setVisible(false);
            text.setVisible(false);
            error.setVisible(false);
            field.setVisible(false);
        } catch (NullPointerException e) {
            // expected behavior when component isn't initialized
        }
    }
}
