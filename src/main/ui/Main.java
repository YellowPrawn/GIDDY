package ui;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Category;
import model.Data;
import model.MixedDataframe;
import model.QuantitativeDataframe;
import persistence.JsonReader;
import persistence.JsonWriter;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        selectMode(scanner);
    }

    // EFFECTS: Allows user to choose between reading existing data or to input data manually.
    public static void selectMode(Scanner scanner) throws IOException {
        System.out.println("select mode:\n (r) read existing file\n (w) write new data");
        switch (scanner.nextLine()) {
            case "r":
                read(scanner);
            case "w":
                write(scanner);
            default:
                System.out.println("invalid selection. Try again");
                scanner.nextLine();
                selectMode(scanner);
        }
    }

    // REQUIRES: valid csv file name in ../data/
    // EFFECTS:  reads existing file in ../data/ and loads it into program
    public static void read(Scanner scanner) throws IOException {
        System.out.println("enter file name");
        try {
            JsonReader reader = new JsonReader("src/main/data/" + scanner.nextLine() + ".json");
            Data data = reader.read();
            processData(data, scanner);
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("file not found. Try again\n");
            read(scanner);
        }
    }

    // REQUIRES: valid CSV-like data
    // EFFECT: allows user to input data in CSV format and loads it into program
    public static void write(Scanner scanner) throws IOException {
        System.out.println("enter data:\n(type \":\" on a new line to exit input mode)");
        Data data = new Data(scanner);
        processData(data, scanner);
        scanner.nextLine();
        saveData(data, scanner);
    }

    // REQUIRES: non-empty Data object
    // EFFECTS: Saves raw data written by user into csv file. If selection is invalid, restart this function
    public static void saveData(Data data, Scanner scanner) throws IOException {
        System.out.println("\nsave raw data to system?: \n (y) yes\n (n) no");
        switch (scanner.nextLine()) {
            case "y":
                System.out.println("enter file name:");
                JsonWriter writer = new JsonWriter("src/main/data/" + scanner.nextLine() + ".json");
                writer.open();
                writer.write(data);
                writer.close();
                System.exit(0);
            case "n":
                System.out.println("ending program...");
                System.exit(0);
            default:
                System.out.println("invalid selection. Try again");
                saveData(data, scanner);
        }
    }

    // REQUIRES: non-empty Data object with 2 Double columns or a String column + Double column
    // EFFECTS: processes data into correct outputs. If data is incompatible, restart program
    public static void processData(Data data, Scanner scanner) throws IOException {
        try {
            if (data.getTypeX().equals("Double") && data.getTypeY().equals("Double")) {
                getQuantitativeSummary(new QuantitativeDataframe(data), scanner);
            } else {
                getMixedSummary(new MixedDataframe(data));
            }
        } catch (ClassCastException e) {
            System.out.println("incompatible dataframe entered. Try again\n");
            scanner.nextLine();
            selectMode(scanner);
        }
    }

    // REQUIRES: non-empty Data object
    // EFFECTS: prints all summary statistics in dataframe
    public static void getQuantitativeSummary(QuantitativeDataframe df, Scanner scanner) {
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
        System.out.println("prediction: " + df.linearRegression(scanner.nextDouble()));
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
}
