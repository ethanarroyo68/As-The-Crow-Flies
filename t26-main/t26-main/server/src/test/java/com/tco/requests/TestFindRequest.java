package com.tco.requests;

import com.tco.misc.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.Places;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Field;


public class TestFindRequest {

    private static final Logger log = LoggerFactory.getLogger(TestTourRequest.class);

    @Test
    @DisplayName("dampierj: Test constructor and getters")
    public void testConstructorAndGetters() {
        Integer limit = 5;
        List<String> type = new ArrayList<>(Arrays.asList(""));
        List<String> where = new ArrayList<>(Arrays.asList(""));
        String match = "";

        FindRequest request = new FindRequest(match, type, where, limit);

        assertEquals(limit, request.getLimit());
        assertEquals(type, request.getType());
        assertEquals(where, request.getWhere());
        assertEquals(match, request.getMatch());
    }

    @Test
    @DisplayName("dampierj: Test country limiter")
    public void testCountryLimiter() {
        String match = "airport";
        List<String> type = new ArrayList<>();
        List<String> where = new ArrayList<>();
        type.add("small_airport");
        type.add("large_airport");
        where.add("United States");
        FindRequest request = new FindRequest(match, type, where, 30);
        request.buildResponse();
        Places places = request.getPlaces();
        assertNotNull(places);

        for (Place place : places) {
            assertTrue(type.contains(place.get("type")),
                "Expected type to be either 'small_airport' or 'large_airport'");
            assertEquals("United States", place.get("country"),
                "Expected country to be 'United States'");
        }
    }

    @Test
    @DisplayName("dampierj: Test entity limiter")
    public void testEntityLimiter() {
        String match = "airport";
        List<String> type = new ArrayList<>();
        List<String> where = new ArrayList<>();
        type.add("small_airport");
        type.add("large_airport");
        where.add("United States");
        where.add("Canada");
        FindRequest request = new FindRequest(match, type, where, 30);
        request.buildResponse();
        Places places = request.getPlaces();
        assertNotNull(places);

        for (Place place : places) {
            assertTrue(type.contains(place.get("type")),
                "Expected type to be either 'small_airport' or 'large_airport'");
            assertTrue(where.contains(place.get("country")),
                "Expected country to be 'United States' or 'Canada'");
        }
    }

    @Test
    @DisplayName("quintenr: Test buildResponse")
    public void testBuildResponse() {
        // Setup
        String match = "test";
        List<String> type = new ArrayList<>(Arrays.asList("type"));
        List<String> where = new ArrayList<>(Arrays.asList("where"));
        Integer limit = 5;

        FindRequest request = new FindRequest(match, type, where, limit);

        // Call buildResponse
        request.buildResponse();

        // Verify places not null
        assertNotNull(request.getPlaces(), "Places should not be null after buildResponse is called.");

        // Verify found is not null and set 0
        assertNotNull(request.getFound(), "Found should not be null after buildResponse is called.");
        assertEquals(0, request.getFound(), "Found should be intially set to 0");
    }


    @Test
    @DisplayName("c836926049: Test results are properly match filtered")
    public void testMatchFiltering() 
    {
        String match = "airport";
        List<String> type = new ArrayList<>(Arrays.asList("small_airport", "large_airport"));
        List<String> where = new ArrayList<>(Arrays.asList("United States"));
        Integer limit = 20;

        FindRequest request = new FindRequest(match, type, where, limit);
        request.buildResponse();

    Places places = request.getPlaces();
    assertNotNull(places, "Places should not be null after buildResponse call");

        for (Place place : places) //wow easy to read naming conventions
        {
        String name = (String) place.get("name");
        assertNotNull(name, "Place name should not be null.");
        assertTrue(name.toLowerCase().contains(match.toLowerCase()), "Place name should contain the match string.");
        }

    }


}
