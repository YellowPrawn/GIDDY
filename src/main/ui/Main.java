package ui;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import model.Category;
import model.Data;
import model.MixedDataframe;
import model.QuantitativeDataframe;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("enter data:");
        System.out.println("(type \":\" on a new line to exit input mode)");
        Scanner scanner = new Scanner(System.in);
        Data data = new Data(scanner);
        if (data.getTypeX().equals("Double") && data.getTypeY().equals("Double")) {
            getQuantitativeSummary(new QuantitativeDataframe(data), scanner);
        } else {
            MixedDataframe mixedDataframe = new MixedDataframe(data);
            getMixedSummary(new MixedDataframe(data));
        }
    }

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

    // EFFECTS: prints all summary statistics in dataframe
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
