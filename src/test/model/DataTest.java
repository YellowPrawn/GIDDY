package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    Data quantitativeData;
    Data mixedData;
    Data mixedData2;
    Data categoricalData;
    Data uniqueHeaderData;
    @BeforeEach
    void runBefore() throws FileNotFoundException {
        quantitativeData = new Data(new Scanner("x,y\n" + "1,10.3\n" + "2,12.7\n" + "3,20.2\n" + ":"));
        mixedData = new Data(new Scanner("x,y\n" + "1,a\n" + "2,b\n" + "3,c"));
        mixedData2 = new Data(new Scanner("x,y\n" + "a,1\n" + "b,2\n" + "c,3"));
        categoricalData = new Data(new Scanner("x,y\n" + "a,d\n" + "b,e\n" + "c,f"));
        uniqueHeaderData = new Data(new Scanner("foo,bar\n" + "11.7,10.3\n" + "9.2,12.7\n" + "4.8,20.2"));
    }
    @Test
    void testSwapColumns() throws FileNotFoundException {
        Data unswappedMixedData = new Data(new Scanner("x,y\n" + "1,a\n" + "2,b\n" + "3,c"));
        mixedData.swapColumns();
        assertEquals(unswappedMixedData.getColX(), mixedData.getColY());
        assertEquals(unswappedMixedData.getColY(), mixedData.getColX());

    }
    @Test
    void testGetHeaderX() {
        assertEquals("x",quantitativeData.getHeaderX());
        assertEquals("foo",uniqueHeaderData.getHeaderX());
    }
    @Test
    void testGetHeaderY() {
        assertEquals("y", quantitativeData.getHeaderY());
        assertEquals("bar", uniqueHeaderData.getHeaderY());
    }
    @Test
    void testGetColXOnMixedData() {
        ArrayList<Object> mixedDataX = new ArrayList<>();
        mixedDataX.add(1.0);
        mixedDataX.add(2.0);
        mixedDataX.add(3.0);
        assertEquals(mixedDataX, mixedData.getColX());
        ArrayList<Object> mixedDataX2 = new ArrayList<>();
        mixedDataX2.add("a");
        mixedDataX2.add("b");
        mixedDataX2.add("c");
        assertEquals(mixedDataX2, mixedData2.getColX());
    }
    @Test
    void testGetColYOnMixedData() {
        ArrayList<Object> mixedDataY = new ArrayList<>();
        mixedDataY.add("a");
        mixedDataY.add("b");
        mixedDataY.add("c");
        assertEquals(mixedDataY, mixedData.getColY());
        ArrayList<Object> mixedDataY2 = new ArrayList<>();
        mixedDataY2.add(1.0);
        mixedDataY2.add(2.0);
        mixedDataY2.add(3.0);
        assertEquals(mixedDataY2, mixedData2.getColY());
    }
    @Test
    void testGetColXOnQuantitativeData() {
        ArrayList<Object> quantitativeDataX = new ArrayList<>();
        quantitativeDataX.add(1.0);
        quantitativeDataX.add(2.0);
        quantitativeDataX.add(3.0);
        assertEquals(quantitativeDataX, quantitativeData.getColX());
    }
    @Test
    void testGetColYOnQuantitativeData() {
        ArrayList<Object> quantitativeDataY = new ArrayList<>();
        quantitativeDataY.add(10.3);
        quantitativeDataY.add(12.7);
        quantitativeDataY.add(20.2);
        assertEquals(quantitativeDataY, quantitativeData.getColY());
    }
    @Test
    void testGetColXOnCategoricalData() {
        ArrayList<Object> categoricalDataX = new ArrayList<>();
        categoricalDataX.add("a");
        categoricalDataX.add("b");
        categoricalDataX.add("c");
        assertEquals(categoricalDataX, categoricalData.getColX());
    }
    @Test
    void testGetColYOnCategoricalData() {
        ArrayList<Object> categoricalDataY = new ArrayList<>();
        categoricalDataY.add("d");
        categoricalDataY.add("e");
        categoricalDataY.add("f");
        assertEquals(categoricalDataY, categoricalData.getColY());
    }
    @Test
    void testGetDataOnMixedData() {
        ArrayList<Object> colX = new ArrayList<>();
        ArrayList<Object> colY = new ArrayList<>();
        colX.add(1.0);
        colX.add(2.0);
        colX.add(3.0);
        colY.add("a");
        colY.add("b");
        colY.add("c");
        ArrayList<Object>[] dataframe = new ArrayList[]{colX, colY};
        assertTrue(dataframe[0].equals(mixedData.getData()[0]) && dataframe[1].equals(mixedData.getData()[1]));
        ArrayList<Object> colX2 = new ArrayList<>();
        ArrayList<Object> colY2 = new ArrayList<>();
        colX2.add("a");
        colX2.add("b");
        colX2.add("c");
        colY2.add(1.0);
        colY2.add(2.0);
        colY2.add(3.0);
        ArrayList<Object>[] dataframe2 = new ArrayList[]{colX2, colY2};
        assertTrue(dataframe2[0].equals(mixedData2.getData()[0]) && dataframe2[1].equals(mixedData2.getData()[1]));
    }
    @Test
    void testGetDataOnQuantitativeData() {
        ArrayList<Object> colX = new ArrayList<>();
        ArrayList<Object> colY = new ArrayList<>();
        colX.add(1.0);
        colX.add(2.0);
        colX.add(3.0);
        colY.add(10.3);
        colY.add(12.7);
        colY.add(20.2);
        ArrayList<Object>[] dataframe = new ArrayList[]{colX, colY};
        assertTrue(dataframe[0].equals(quantitativeData.getData()[0]) && dataframe[1].equals(quantitativeData.getData()[1]));
    }
    @Test
    void testGetDataOnCategoricalData() {
        ArrayList<Object> colX = new ArrayList<>();
        ArrayList<Object> colY = new ArrayList<>();
        colX.add("a");
        colX.add("b");
        colX.add("c");
        colY.add("d");
        colY.add("e");
        colY.add("f");
        ArrayList<Object>[] dataframe = new ArrayList[]{colX, colY};
        assertTrue(dataframe[0].equals(categoricalData.getData()[0]) && dataframe[1].equals(categoricalData.getData()[1]));
    }
    @Test
    void testGetTypeX() {
        assertEquals("Double", quantitativeData.getTypeX());
        assertEquals("Double", mixedData.getTypeX());
        assertEquals("String", mixedData2.getTypeX());
        assertEquals("String", categoricalData.getTypeX());
    }
    @Test
    void testGetTypeY() {
        assertEquals("Double", quantitativeData.getTypeY());
        assertEquals("String", mixedData.getTypeY());
        assertEquals("Double", mixedData2.getTypeY());
        assertEquals("String", categoricalData.getTypeY());
    }
}