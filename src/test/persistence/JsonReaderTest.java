package persistence;

import model.Data;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("src/main/data/noSuchFile.json");
        try {
            Data data = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderQuantitativeData() {
        JsonReader reader = new JsonReader("src/main/data/testReaderQuantitativeData.json");
        try {
            Data data = reader.read();
            assertEquals("x", data.getHeaderX());
            assertEquals("y", data.getHeaderY());
            assertEquals(2, data.getColX().size());
            assertEquals(2, data.getColY().size());
            checkObservations(new Object[]{1.0,2.0}, data, 0);
            checkObservations(new Object[]{3.0,4.0}, data, 1);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
    void testReaderMixedData() {
        JsonReader reader = new JsonReader("src/main/data/testReaderMixedData.json");
        try {
            Data data = reader.read();
            assertEquals("x", data.getHeaderX());
            assertEquals("y", data.getHeaderY());
            assertEquals(2, data.getColX().size());
            assertEquals(2, data.getColY().size());
            checkObservations(new Object[]{"a",2.0}, data, 0);
            checkObservations(new Object[]{"b",4.0}, data, 1);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
