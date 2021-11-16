package ui;

import java.awt.*;
import java.io.IOException;

import model.Data;
import model.QuantitativeDataframe;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;

public class Main extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    JButton readButton;
    JButton writeButton;
    JButton fileSearchButton;
    JButton saveButton;
    JButton predictionEntryButton;
    JButton writeFileButton;

    JLabel selectMode;
    JLabel fileName;
    JLabel dataEntryText;
    JLabel predictionEntryText;

    JTextField fileSearchField;
    JTextField saveFileField;
    JTextField writeFileField;
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
        cardLayout.minimumLayoutSize(this);
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
        add(ui, null);
    }

    // MODIFIES: this
    // EFFECTS: creates program buttons
    public void initiateButtons() {
        readButton = new JButton("read existing file");
        writeButton = new JButton("write new data");
        fileSearchButton = new JButton("search");
        writeFileButton = new JButton("submit");
        saveButton = new JButton("save");
        predictionEntryButton = new JButton("predict");

        // panels with 2 buttons
        readButton.setBounds(WIDTH * 3 / 4, 0, WIDTH / 5, HEIGHT / 10);
        writeButton.setBounds(WIDTH / 2, 0, WIDTH / 5, HEIGHT / 10);

        // panels with single buttons
        fileSearchButton.setBounds(WIDTH * 3 / 4, 0, WIDTH / 5, HEIGHT / 10);
        writeFileButton.setBounds(WIDTH * 3 / 4, 0, WIDTH / 5, HEIGHT / 10);
        saveButton.setBounds(WIDTH * 3 / 4, 0, WIDTH / 5, HEIGHT / 10);
        predictionEntryButton.setBounds(WIDTH * 3 / 4, 0, WIDTH / 5, HEIGHT / 10);
    }

    // MODIFIES: this
    // EFFECTS: creates program labels
    public void initiateLabels() {
        selectMode = new JLabel("select mode:", SwingConstants.CENTER);
        fileName = new JLabel("enter file name", SwingConstants.CENTER);
        dataEntryText = new JLabel("enter data: (separated with commas)");
        predictionEntryText = new JLabel("enter x value for prediction: ");

        selectMode.setBounds(WIDTH / 4, 0, WIDTH / 5, HEIGHT / 10);
        fileName.setBounds(WIDTH / 4, 0, WIDTH / 5, HEIGHT / 10);
        dataEntryText.setBounds(WIDTH / 4, 0, WIDTH / 5, HEIGHT / 10);
        predictionEntryText.setBounds(WIDTH / 4, 0, WIDTH / 5, HEIGHT / 10);
    }

    // MODIFIES: this
    // EFFECTS: creates program fields
    public void initiateFields() {
        fileSearchField = new JTextField(20);
        saveFileField = new JTextField(20);
        writeFileField = new JTextField(20);
        predictionEntryField = new JTextField(20);

        fileSearchField.setBounds(WIDTH / 2, 0, WIDTH / 5, HEIGHT / 10);
        saveFileField.setBounds(WIDTH / 2, 0, WIDTH / 5, HEIGHT / 10);
        writeFileField.setBounds(WIDTH / 2, 0, WIDTH / 5, HEIGHT / 10);
        predictionEntryField.setBounds(WIDTH / 2, 0, WIDTH / 5, HEIGHT / 10);
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
        initiateWritePage();
    }

    // MODIFIES: this, ui, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage, readButton, writeButton
    // EFFECTS: sets up functionality for startPage
    public void initiateStartPage() {
        ui.add(startPage, "startPage");
        startPage.add(selectMode);
        startPage.add(readButton);
        startPage.add(writeButton);

        readButton.addActionListener(e -> read());
        writeButton.addActionListener(e -> write());
    }

    // MODIFIES: this, ui, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage, fileSearchButton
    // EFFECTS: sets up functionality for startPage
    public void initiateReadPage() {
        ui.add(readPage, "readPage");
        readPage.add(fileName);
        readPage.add(fileSearchField);
        readPage.add(fileSearchButton);
        fileSearchButton.addActionListener(e -> {
            try {
                JsonReader reader = new JsonReader("src/main/data/" + fileSearchField.getText() + ".json");
                Data data = reader.read();
                processData(data);
            } catch (IOException exception) {
                fileName.setText("file not found. Try again");
            }
        });
    }

    // MODIFIES: this, ui, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage, writeFileButton
    // EFFECTS: sets up functionality for writePage
    public void initiateWritePage() {
        ui.add(writePage,"writePage");
        writePage.add(dataEntryText);
        writePage.add(writeFileField);
        writePage.add(writeFileButton);

        writeFileButton.addActionListener(e -> {
            Data data = new Data(writeFileField.getText());
            processData(data);
            //saveData(data);
        });
    }


    // MODIFIES: this, ui, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage, cardLayout
    // EFFECTS: Allows user to choose between reading existing data or to input data manually.
    public void selectMode() {
        cardLayout.show(ui, "startPage");
    }

    // REQUIRES: valid csv file name in ../data/
    // MODIFIES: this, ui, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage, cardLayout, fileName
    // EFFECTS:  reads existing file in ../data/ and loads it into program
    public void read() {
        fileName.setText("enter file name");
        cardLayout.show(ui, "readPage");
    }

    // REQUIRES: valid CSV-like data
    // MODIFIES: this, ui, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage, cardLayout
    // EFFECT: allows user to input data in CSV format and loads it into program
    public void write() {
        cardLayout.show(ui, "writePage");
    }

    // REQUIRES: non-empty Data object with 2 Double columns or a String column + Double column
    // MODIFIES: this, ui, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage
    // EFFECTS: processes data into correct outputs. If data is incompatible, restart program
    public void processData(Data data) {

        try {
            Graph graph = new Graph(data, WIDTH, HEIGHT);
            ui.add(graph, "results");
            cardLayout.show(ui, "results");
            repaint();
        } catch (ClassCastException e) {
            predictionEntryText.setText("incompatible dataframe entered. Try again\n");
            selectMode();
        }
    }

    // REQUIRES: non-empty Data object
    // MODIFIES: this, ui, startPage, readPage, writePage, mixedDataPage, QuantitativeDataPage
    // EFFECTS: Saves raw data written by user into csv file. If selection is invalid, restart this function
//    public void saveData(Data data) {
//        // TODO: turn this into optional overlay
//        b1 = new JButton("save raw data");
//        b1.addActionListener(e -> {
//            resetComponents();
//            text = new JLabel("enter file name:");
//            field = new JTextField();
//            b1 = new JButton("save");
//            b1.addActionListener(e2 -> {
//                JsonWriter writer = new JsonWriter("src/main/data/" + field.getText() + ".json");
//                try {
//                    writer.open();
//                } catch (FileNotFoundException ex) {
//                    ex.printStackTrace();
//                }
//                writer.write(data);
//                writer.close();
//            });
//            ui.add(text);
//            ui.add(field);
//            ui.add(b1);
//        });
//        ui.add(b1);
//    }

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
}
