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
    void testReaderGeneralData() {
        JsonReader reader = new JsonReader("src/main/data/testReaderGeneralData.json");
        try {
            Data data = reader.read();
            assertEquals("x", data.getHeaderX());
            assertEquals("y", data.getHeaderY());
            assertEquals(2, data.getColX().size());
            assertEquals(2, data.getColY().size());
            checkObservations(new double[]{1,2}, data, 0);
            checkObservations(new double[]{3,4}, data, 1);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
