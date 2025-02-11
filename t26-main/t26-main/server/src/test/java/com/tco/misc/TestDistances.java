package com.tco.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class TestDistances {

    @Test
    @DisplayName("mboin: Test empty Distances total")
    public void testEmptyTotal() {
        Distances distances = new Distances();
        assertEquals(0, distances.total(), "Total of empty distances should be 0");
    }

    @Test
    @DisplayName("mboin: Test total with values")
    public void testTotalWithValues() {
        Distances distances = new Distances();
        distances.addAll(Arrays.asList(10L, 20L, 30L));
        assertEquals(60, distances.total(), "Total should be the sum of the distances");
    }

    @Test
    @DisplayName("mboin: Test total with large values")
    public void testTotalWithLargeValues() {
        Distances distances = new Distances();
        distances.addAll(Arrays.asList(10000000000L, 20000000000L, 30000000000L));
        assertEquals(60000000000L, distances.total(), "Total should handle large distance values correctly");
    }
}



