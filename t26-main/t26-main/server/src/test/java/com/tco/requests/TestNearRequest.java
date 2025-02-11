package com.tco.requests;

import com.tco.misc.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.Places;
import com.tco.misc.BadRequestException;

public class TestNearRequest {

    private static final Logger log = LoggerFactory.getLogger(TestTourRequest.class);

    @Test
    @DisplayName("dampierj: Test constructor and getters")
    public void testConstructorAndGetters() {
        Place testPlace = new Place();
        double testEarthRadius = 6371.0;
        String testFormula = "cosines";
        Integer distance = 0;
        Integer limit = 5;

        NearRequest request = new NearRequest(testPlace, distance, testEarthRadius, testFormula, limit);

        assertEquals(testPlace, request.getPlace());
        assertEquals(distance, request.getDistance());
        assertEquals(testEarthRadius, request.getEarthRadius());
        assertEquals(testFormula, request.getFormula());
        assertEquals(limit, request.getLimit());
    }

    @Test
    @DisplayName("dampierj: Test buildResponse method")
    public void testBuildResponse() throws BadRequestException {
        Place testPlace = new Place("38.532798767089844", "-105.98999786376953");
        double testEarthRadius = 6371.0;
        String testFormula = "cosines";
        Integer distance = 0;
        Integer limit = 5;

        NearRequest request = new NearRequest(testPlace, distance, testEarthRadius, testFormula, limit);
        request.buildResponse();

        assertNotNull(request.getPlaces());
        log.info("buildResponse executed successfully.");
    }

    @Test
    @DisplayName("c836926049: Test default constructor")
    public void testDefaultConstructor() {
        NearRequest request = new NearRequest();

        assertNull(request.getPlace());
        assertNull(request.getDistance());
        assertNull(request.getEarthRadius());
        assertNull(request.getFormula());
        assertNull(request.getLimit());
        assertNull(request.getPlaces());
        assertNull(request.getDistances());
    }

    @Test
    @DisplayName("c836926049: Test places integrity after buildResponse")
    public void testPlacesIntegrity() throws BadRequestException {
        Place testPlace = new Place("38.532798767089844", "-105.98999786376953");
        Integer distance = 10;
        double testEarthRadius = 6371.0;
        String testFormula = "cosines";
        Integer limit = 5;

    NearRequest request = new NearRequest(testPlace, distance, testEarthRadius, testFormula, limit);
    request.buildResponse();

    //check that places populated
    assertNotNull(request.getPlaces(), "Places should not be null after buildResponse.");
    assertFalse(request.getPlaces().isEmpty(), "Places should not be empty after buildResponse.");
    
    log.info("Places integrity test executed successfully.");

    }

    @Test
    @DisplayName("c836926049: Test distances integrity after buildResponse")
    public void testDistancesIntegrity() throws BadRequestException {
    Place testPlace = new Place("38.532798767089844", "-105.98999786376953");
    Integer distance = 10;
    double testEarthRadius = 6371.0;
    String testFormula = "cosines";
    Integer limit = 5;

    NearRequest request = new NearRequest(testPlace, distance, testEarthRadius, testFormula, limit);
    request.buildResponse();

    //check distances correspond to the populated places
    assertNotNull(request.getDistances(), "Distances should not be null after buildResponse.");
    assertEquals(request.getPlaces().size(), request.getDistances().size(), "Number of distances should match the number of places.");
    
    log.info("Distances integrity test executed successfully.");
    
    }

}

