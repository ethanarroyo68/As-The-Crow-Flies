package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlace {

    private Place place;

    @BeforeEach
    public void setUp() {
        // Set up a Place object with valid latitude and longitude for Fort Collins, Colorado
        place = new Place("40.5853", "-105.0844");
    }

    @Test
    @DisplayName("mboin: Test latitude conversion to radians")
    public void testLatRadians() {
        // Expected latitude in radians for Fort Collins, Colorado
        Double expectedLatRadians = Math.toRadians(40.5853);
        assertEquals(expectedLatRadians, place.latRadians(), 0.000001); // Check if latRadians() gives correct result
    }

    @Test
    @DisplayName("mboin: Test longitude conversion to radians")
    public void testLonRadians() {
        // Expected longitude in radians for Fort Collins, Colorado
        Double expectedLonRadians = Math.toRadians(-105.0844);
        assertEquals(expectedLonRadians, place.lonRadians(), 0.000001); // Check if lonRadians() gives correct result
    }

    @Test
    @DisplayName("mboin: Test invalid coordinate handling")
    public void testInvalidCoordinate() {
        // Create a Place with invalid latitude and longitude
        Place invalidPlace = new Place("invalid_lat", "invalid_lon");

        // Check that the conversion returns null for invalid input
        assertNull(invalidPlace.latRadians());
        assertNull(invalidPlace.lonRadians());
    }

    @Test
    @DisplayName("mboin: Test handling of null coordinates")
    public void testNullCoordinate() {
        // Create a Place with null latitude and longitude
        Place nullPlace = new Place(null, null);

        // Check that the conversion returns null for null input
        assertNull(nullPlace.latRadians());
        assertNull(nullPlace.lonRadians());
    }

    @Test
    @DisplayName("mboin: Test ID retrieval")
    public void testId() {
        // Test setting and retrieving the ID
        place.put("id", "1234"); // Set an ID
        assertEquals("1234", place.id()); // Check if id() returns the correct ID
    }
}
