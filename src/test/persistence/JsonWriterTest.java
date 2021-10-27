package persistence;

import model.Category;
import model.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    ArrayList<String> testData;

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyData() {
        try {
            testData = new ArrayList<>();
            testData.add("x,y");
            Data data = new Data(testData);
            fail();
        } catch (IndexOutOfBoundsException e) {
            //expected behaviour
        }
    }

    @Test
    void testWriterQuantitativeData() {
        try {
            testData = new ArrayList<>();
            testData.add("x,y");
            testData.add("1.0,2.0");
            testData.add("3.0,4.0");

            Data data = new Data(testData);
            JsonWriter writer = new JsonWriter("./data/testWriterQuantitativeData.json");
            writer.open();
            writer.write(data);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterQuantitativeData.json");
            data = reader.read();
            assertEquals("x", data.getHeaderX());
            assertEquals("y", data.getHeaderY());
            assertEquals(2, data.getColX().size());
            assertEquals(2, data.getColY().size());
            checkObservations(new Object[]{1.0,2.0}, data, 0);
            checkObservations(new Object[]{3.0,4.0}, data, 1);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    @Test
    void testWriterMixedData() {
        try {
            testData = new ArrayList<>();
            testData.add("x,y");
            testData.add("a,2.0");
            testData.add("b,4.0");

            Data data = new Data(testData);
            JsonWriter writer = new JsonWriter("./data/testWriterMixedData.json");
            writer.open();
            writer.write(data);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterMixedData.json");
            data = reader.read();
            assertEquals("x", data.getHeaderX());
            assertEquals("y", data.getHeaderY());
            assertEquals(2, data.getColX().size());
            assertEquals(2, data.getColY().size());
            checkObservations(new Object[]{"a",2.0}, data, 0);
            checkObservations(new Object[]{"b",4.0}, data, 1);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
