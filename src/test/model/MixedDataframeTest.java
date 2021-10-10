package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MixedDataframeTest extends QuantitativeAnalyzerTest{
    MixedDataframe testMixedFrame;
    Data mixedData;

    @BeforeEach
    public void runBefore() throws FileNotFoundException {
        super.runBefore();
        mixedData = new Data(new Scanner("x,y\n" + "1.7,a\n" + "33.2,b\n" + "24.3,a\n" + "23.7,b\n" + "12.3,c\n" + "55.9,b\n" + "9.3,a"));
        testMixedFrame = new MixedDataframe(mixedData);
    }
    @Test
    void testPreOrderedColumns() throws FileNotFoundException {
        mixedData = new Data(new Scanner("x,y\n" + "a,1.9"));
        testMixedFrame = new MixedDataframe(mixedData);
        assertEquals("String", mixedData.getTypeX());
        assertEquals("Double", mixedData.getTypeY());
    }
    @Test
    void testGetMeans() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(11.766666666666666);
        expected.add(37.6);
        expected.add(12.3);
        assertEquals(expected,testMixedFrame.getMeans());
    }
    @Test
    void testGetMedians() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(9.3);
        expected.add(33.2);
        expected.add(12.3);
        assertEquals(expected,testMixedFrame.getMedians());
    }
    @Test
    void testGetSDs() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(9.389829012761036);
        expected.add(13.508762588285673);
        expected.add(0.0);
        assertEquals(expected,testMixedFrame.getSDs());
    }
    @Test
    void testGet1QRs() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(1.7);
        expected.add(23.7);
        expected.add(12.3);
        assertEquals(expected,testMixedFrame.get1QRs());
    }
    @Test
    void testGet3QRs() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(24.3);
        expected.add(55.9);
        expected.add(12.3);
        assertEquals(expected,testMixedFrame.get3QRs());
    }
    @Test
    void testGetIQRs() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(22.6);
        expected.add(32.2);
        expected.add(0.0);
        assertEquals(expected,testMixedFrame.getIQRs());
    }

    @Test
    void testMaximas() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(24.3);
        expected.add(55.9);
        expected.add(12.3);
        assertEquals(expected,testMixedFrame.maxima());
    }
    @Test
    void testMinimas() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(1.7);
        expected.add(23.7);
        expected.add(12.3);
        assertEquals(expected,testMixedFrame.minima());
    }
    @Test
    void testSums() {
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(35.3);
        expected.add(112.80000000000001);
        expected.add(12.3);
        assertEquals(expected,testMixedFrame.sums());
    }
    @Test
    void testGetCategories() {
        ArrayList<Category> expected = new ArrayList<>();
        Category a = new Category("a");
        Category b = new Category("b");
        Category c = new Category("c");
        a.addElement(1.7);
        a.addElement(24.3);
        a.addElement(9.3);
        b.addElement(33.2);
        b.addElement(23.7);
        b.addElement(55.9);
        c.addElement(12.3);
        expected.add(a);
        expected.add(b);
        expected.add(c);
        assertEquals(a.getHeader(), testMixedFrame.getCategories().get(0).getHeader());
        assertEquals(a.getObservation(0), testMixedFrame.getCategories().get(0).getObservation(0));
        assertEquals(a.getObservation(1), testMixedFrame.getCategories().get(0).getObservation(1));
        assertEquals(a.getObservation(2), testMixedFrame.getCategories().get(0).getObservation(2));
        assertEquals(b.getHeader(), testMixedFrame.getCategories().get(1).getHeader());
        assertEquals(b.getObservation(0), testMixedFrame.getCategories().get(1).getObservation(0));
        assertEquals(b.getObservation(1), testMixedFrame.getCategories().get(1).getObservation(1));
        assertEquals(b.getObservation(2), testMixedFrame.getCategories().get(1).getObservation(2));
        assertEquals(c.getHeader(), testMixedFrame.getCategories().get(2).getHeader());
        assertEquals(c.getObservation(0), testMixedFrame.getCategories().get(2).getObservation(0));
    }
}
