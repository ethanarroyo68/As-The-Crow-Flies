package com.tco.database;

import com.tco.misc.Place;
import com.tco.misc.Places;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {
    
    private static final String TEST_FOUND_QUERY = 
        "SELECT COUNT(*) AS count FROM world w " +
        "INNER JOIN region r ON w.iso_region = r.id " +
        "INNER JOIN country c ON w.iso_country = c.id " +
        "WHERE w.name LIKE '%Airport%' LIMIT 1";

    private static final String TEST_PLACES_QUERY = 
        "SELECT w.id, w.name, w.municipality, r.name AS region, c.name AS country, " +
        "w.latitude, w.longitude, w.altitude, w.type " +
        "FROM world w " +
        "INNER JOIN region r ON w.iso_region = r.id " +
        "INNER JOIN country c ON w.iso_country = c.id " +
        "WHERE w.name LIKE '%International Airport%' LIMIT 5";

    @Test
    @DisplayName("mboin: Database found() should return count")
    void testFound() {
        try {
            Integer count = Database.found(TEST_FOUND_QUERY);
            assertNotNull(count);
            assertTrue(count >= 0, "Count should be non-negative");
        } catch (Exception e) {
            fail("Database found() threw an exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("mboin: Database found() should throw exception for invalid SQL")
    void testFoundInvalidSQL() {
        String invalidSQL = "SELECT COUNT(*) AS count FROM nonexistent_table";
        Exception exception = assertThrows(Exception.class, () -> {
            Database.found(invalidSQL);
        });
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("mboin: Database places() should return Places object")
    void testPlaces() {
        try {
            Places places = Database.places(TEST_PLACES_QUERY);
            assertNotNull(places);
            assertFalse(places.isEmpty(), "Places should not be empty");
            
            Place firstPlace = places.get(0);
            assertNotNull(firstPlace.get("id"));
            assertNotNull(firstPlace.get("name"));
            assertNotNull(firstPlace.get("municipality"));
            assertNotNull(firstPlace.get("region"));
            assertNotNull(firstPlace.get("country"));
            assertNotNull(firstPlace.get("latitude"));
            assertNotNull(firstPlace.get("longitude"));
            assertNotNull(firstPlace.get("type"));
            assertNotNull(firstPlace.get("index"));
            
        } catch (Exception e) {
            fail("Database places() threw an exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("mboin: Database places() should handle empty results")
    void testPlacesEmptyResult() {
        String emptyQuery = 
            "SELECT w.id, w.name, w.municipality, r.name AS region, c.name AS country, " +
            "w.latitude, w.longitude, w.altitude, w.type " +
            "FROM world w " +
            "INNER JOIN region r ON w.iso_region = r.id " +
            "INNER JOIN country c ON w.iso_country = c.id " +
            "WHERE w.name = 'NonexistentPlace'";
        
        try {
            Places places = Database.places(emptyQuery);
            assertNotNull(places);
            assertTrue(places.isEmpty(), "Places should be empty for query with no results");
        } catch (Exception e) {
            fail("Database places() threw an exception for empty results: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("mboin: Database places() should throw exception for invalid SQL")
    void testPlacesInvalidSQL() {
        String invalidSQL = "SELECT * FROM nonexistent_table";
        Exception exception = assertThrows(Exception.class, () -> {
            Database.places(invalidSQL);
        });
        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("mboin: Database places() should correctly set index field")
    void testPlacesIndex() {
        try {
            Places places = Database.places(TEST_PLACES_QUERY);
            assertNotNull(places);
            for (int i = 0; i < places.size(); i++) {
                Place place = places.get(i);
                assertEquals(String.valueOf(i + 1), place.get("index"), 
                    "Index should match position in list");
            }
        } catch (Exception e) {
            fail("Database places() threw an exception: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("mboin: Database should handle database connection issues")
    void testDatabaseConnection() {
        try {
            Places places = Database.places(TEST_PLACES_QUERY);
            assertNotNull(places);
        } catch (Exception e) {
            if (!e.getMessage().contains("connection")) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
    }
}
