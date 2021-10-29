package persistence;

import model.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class is modelled off the JsonTest class found in the JsonSerializationDemo repository
public class JsonTest {
    // Modelled off the checkObservations test found in the JsonSerializationDemo repository
    protected void checkObservations(Object[] row, Data data, int rowIndex) {
        assertEquals(row[0], data.getColX().get(rowIndex));
        assertEquals(row[1], data.getColY().get(rowIndex));
    }
}
