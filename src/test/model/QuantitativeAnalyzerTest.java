package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public abstract class QuantitativeAnalyzerTest {
    Data data;
    QuantitativeDataframe testFrame;

    @BeforeEach
    public void runBefore() throws FileNotFoundException {
        data = new Data("src/main/data/quantitativeData.csv");
        testFrame = new QuantitativeDataframe(data);
    }

    @Test
    void testGetMean() {
        assertEquals(2, testFrame.getMean(testFrame.colX));
    }

    @Test
    void testGetMedian() {
        assertEquals(2, testFrame.getMedian(testFrame.colX));
    }
    @Test
    void testGetSD() {
        assertEquals(0.816496580927726, testFrame.getSD(testFrame.colX));
    }
    @Test
    void test1QR() {
        assertEquals(1, testFrame.get1QR(testFrame.colX));
    }
    @Test
    void test3QR() {
        assertEquals(3, testFrame.get3QR(testFrame.colX));
    }
    @Test
    void testIQR() {assertEquals(2, testFrame.getIQR(testFrame.colX));}
    @Test
    void testMax() {
        assertEquals(3, testFrame.max(testFrame.colX));
    }
    @Test
    void testMin() {
        assertEquals(1, testFrame.min(testFrame.colX));
    }
    @Test
    void testSum() {
        assertEquals(6, testFrame.sum(testFrame.colX));
    }
    @Test
    void testSort() {
        Object originalCol = testFrame.colX.clone();
        assertEquals(originalCol, testFrame.sort(testFrame.colX));
    }
    @Test
    void testToDouble() {
        ArrayList<Double> doubleCol = new ArrayList<>();
        doubleCol.add(1.0);
        doubleCol.add(2.0);
        doubleCol.add(3.0);
        assertEquals(doubleCol, testFrame.toDouble(data.getColX()));
    }
}
