package com.tco.database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class TestSelect {

    @Test
    @DisplayName("mboin: Test searchNearby query construction")
    public void testSearchNearby() {
        String query = Select.searchNearby(39.0, 41.0, -105.0, -103.0, 100.0); // Removed limit
        assertTrue(query.contains("SELECT"));
        assertTrue(query.contains("FROM world w"));
        assertTrue(query.contains("INNER JOIN"));
        assertTrue(query.contains("WHERE w.latitude BETWEEN 39.0 AND 41.0"));
    }

    @Test
    @DisplayName("mboin: Test match query with type and where filters")
    public void testMatchWithFilters() {
        List<String> types = Arrays.asList("heliport", "airport");
        List<String> locations = Arrays.asList("Denver", "Colorado");
        String query = Select.match("test", types, locations, 10);
        
        assertTrue(query.contains("WHERE"));
        assertTrue(query.contains("LIKE"));
        assertTrue(query.contains("heliport"));
        assertTrue(query.contains("airport"));
        assertTrue(query.contains("Denver"));
        assertTrue(query.contains("Colorado"));
    }

    @Test
    @DisplayName("mboin: Test match query with null filters")
    public void testMatchWithNullFilters() {
        String query = Select.match("test", null, null, 10);
        
        assertTrue(query.contains("WHERE"));
        assertTrue(query.contains("LIKE"));
        assertFalse(query.contains("AND"));
        assertTrue(query.contains("LIMIT 10"));
    }

    @Test
    @DisplayName("mboin: Test match query with empty filters")
    public void testMatchWithEmptyFilters() {
        String query = Select.match("test", new ArrayList<>(), new ArrayList<>(), 10);
        
        assertTrue(query.contains("WHERE"));
        assertTrue(query.contains("LIKE"));
        assertFalse(query.contains("AND"));
        assertTrue(query.contains("LIMIT 10"));
    }

    @Test
    @DisplayName("mboin: Test found query construction")
    public void testFound() {
        List<String> types = Arrays.asList("airport");
        List<String> locations = Arrays.asList("Denver");
        String query = Select.found("test", types, locations);
        
        assertTrue(query.contains("SELECT COUNT(*)"));
        assertTrue(query.contains("WHERE"));
        assertTrue(query.contains("airport"));
        assertTrue(query.contains("Denver"));
    }

    @Test
    @DisplayName("mboin: Test SQL injection prevention in match")
    public void testSqlInjectionPrevention() {
        List<String> types = Arrays.asList("type'; DROP TABLE users; --");
        List<String> locations = Arrays.asList("location'; DELETE FROM world; --");
        String query = Select.match("test'; DROP TABLE world; --", types, locations, 10);
        
        assertFalse(query.contains(";--"));
        assertTrue(query.contains("''"));
    }

    @Test
    @DisplayName("mboin: Test proper column selection")
    public void testColumnSelection() {
        String query = Select.searchNearby(39.0, 41.0, -105.0, -103.0, 100.0); // Removed limit
        
        assertTrue(query.contains("w.id"));
        assertTrue(query.contains("w.name"));
        assertTrue(query.contains("w.municipality"));
        assertTrue(query.contains("w.latitude"));
        assertTrue(query.contains("w.longitude"));
        assertTrue(query.contains("w.altitude"));
        assertTrue(query.contains("w.type"));
        assertTrue(query.contains("r.name AS region"));
        assertTrue(query.contains("c.name AS country"));
    }

    @Test
    @DisplayName("mboin: Test zero limit handling")
    public void testZeroLimit() {
        String query = Select.match("test", null, null, 0);
        assertTrue(query.contains("LIMIT 0"));
    }

    @Test
    @DisplayName("mboin: Test case insensitivity in queries")
    public void testCaseInsensitivity() {
        String query = Select.match("TEST", null, null, 10);
        assertTrue(query.contains("LOWER"));
    }
}