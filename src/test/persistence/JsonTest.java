package persistence;

import model.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkObservations(Object[] row, Data data, int rowIndex) {
        assertEquals(row[0], data.getColX().get(rowIndex));
        assertEquals(row[1], data.getColY().get(rowIndex));
    }
}
