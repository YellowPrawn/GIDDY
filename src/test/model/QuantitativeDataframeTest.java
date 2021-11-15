package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class QuantitativeDataframeTest extends QuantitativeAnalyzerTest {
    @Test
    void testGetColX() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(1.0);
        expected.add(2.0);
        expected.add(3.0);
        assertEquals(expected, testFrame.getColX());
    }
    @Test
    void testGetColY() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(10.3);
        expected.add(12.7);
        expected.add(20.2);
        assertEquals(expected, testFrame.getColY());
    }
    @Test
    void testGetColXMean(){
        assertEquals(2,testFrame.getColXMean());
    }
    @Test
    void testGetColYMean(){
        assertEquals(14.4,testFrame.getColYMean());
    }
    @Test
    void testGetColXMedian() {
        assertEquals(2, testFrame.getColXMedian());
    }
    @Test
    void testGetColYMedian() {
        assertEquals(12.7, testFrame.getColYMedian());
    }
    @Test
    void testGetColXSD() {
        assertEquals(0.816496580927726, testFrame.getColXSD());
    }
    @Test
    void testGetColYSD() {
        assertEquals(4.216633728461603, testFrame.getColYSD());
    }
    @Test
    void testGetColX1QR() {
        assertEquals(1, testFrame.getColX1QR());
    }
    @Test
    void testGetColY1QR() {
        assertEquals(10.3, testFrame.getColY1QR());
    }
    @Test
    void testGetColX3QR() {
        assertEquals(3, testFrame.getColX3QR());
    }
    @Test
    void testGetColY3QR() {
        assertEquals(20.2, testFrame.getColY3QR());
    }
    @Test
    void testGetColXIQR() {
        assertEquals(2, testFrame.getColXIqR());
    }
    @Test
    void testGetColYIQR() {
        assertEquals(9.899999999999999, testFrame.getColYIqR());
    }
    @Test
    void testColXMax() {
        assertEquals(3, testFrame.getColXMax());
    }
    @Test
    void testColYMax() {
        assertEquals(20.2, testFrame.getColYMax());
    }
    @Test
    void testColXMin() {
        assertEquals(1, testFrame.getColXMin());
    }
    @Test
    void testColYMin() {
        assertEquals(10.3, testFrame.getColYMin());
    }
    @Test
    void testColXSum() {
        assertEquals(6, testFrame.getColXSum());
    }
    @Test
    void testColYSum() {
        assertEquals(43.2, testFrame.getColYSum());
    }
    @Test
    void testRegressionFunctions() {
        assertEquals(22.857182241227424,testFrame.linearRegression(1));
    }
}
