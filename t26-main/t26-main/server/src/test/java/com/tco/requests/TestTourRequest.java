package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.Places;
import com.tco.misc.BadRequestException;

public class TestTourRequest {

    private static final Logger log = LoggerFactory.getLogger(TestTourRequest.class);

    @Test
    @DisplayName("mboin: Test buildResponse method")
    public void testBuildResponse() throws BadRequestException {
        Places testPlaces = new Places();
        double testEarthRadius = 6371.0;
        String testFormula = "cosines";
        double testResponse = 10.0;

        TourRequest request = new TourRequest(testEarthRadius, testFormula, testPlaces);
        request.buildResponse();

        assertNotNull(request.getplaces());

        log.info("buildResponse executed successfully.");
    }
    
    @Test
    @DisplayName("mboin: Test constructor and getters")
    public void testConstructorAndGetters() {
        Places testPlaces = new Places();
        double testEarthRadius = 6371.0;
        String testFormula = "cosines";

        TourRequest request = new TourRequest(testEarthRadius, testFormula, testPlaces);

        assertEquals(testEarthRadius, request.getearthRadius());
        assertEquals(testFormula, request.getformula());
        assertEquals(testPlaces, request.getplaces());
    }

    @Test
    @DisplayName("mboin: Test default constructor")
    public void testDefaultConstructor() {
        TourRequest request = new TourRequest();

        assertEquals(0.0, request.getearthRadius());
        assertEquals(null, request.getformula());
        assertNull(request.getplaces());
    }
}