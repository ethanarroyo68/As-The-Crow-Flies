package com.tco.requests;

import java.lang.Math;

import com.tco.misc.*;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import com.tco.misc.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDistancesRequest {
    double radius = 6371;
    String formula = "haversine";
    Places places;
    DistancesRequest request;
    Distances distances;

    @BeforeEach
    public void beforeEach() {
        this.places = new Places();
        this.request = new DistancesRequest(radius, formula, places);
    }

    @Test
    @DisplayName("quintenr: no places")
    public void testNoPlaces() {
        try {
            request.buildResponse();
        } catch (BadRequestException e) {
            fail("BadRequestException");
        }
        assertEquals("[]", request.earthdistances().toString());
    }

    @Test
    @DisplayName("quintenr: one places")
    public void testOnePlaces() {
        places.add(new Place("0", "0"));
        try {
            request.buildResponse();
        } catch (BadRequestException e) {
            fail("BadRequestException");
        }
        assertEquals("[0]", request.earthdistances().toString());
    }

    @Test
    @DisplayName("quintenr: two places")
    public void testTwoPlaces() {
        places.add(new Place("0", "0"));
        places.add(new Place("0", "180"));
        try {
            request.buildResponse();
        } catch (BadRequestException e) {
            fail("BadRequestException");
        }
        assertEquals("[20015, 20015]", request.earthdistances().toString());
    }

    @Test
    @DisplayName("quintenr: more places")
    public void testMorePlaces() {
        places.add(new Place("0", "0"));
        places.add(new Place("0", "90"));
        places.add(new Place("90", "0"));
        places.add(new Place("90", "90"));
        try {
            request.buildResponse();
        } catch (BadRequestException e) {
            fail("BadRequestException");
        }
        assertEquals("[10008, 10008, 0, 10008]", request.earthdistances().toString());
    }

    @Test
    @DisplayName("c836926049: Test - Point repeated to verify handling of identicle places")
    public void testSamePointRepeated() 
    {
        places.add(new Place("45", "45"));
        places.add(new Place("45", "45"));
        places.add(new Place("45", "45"));
        try 
        {
            request.buildResponse();
        } 
        catch (BadRequestException e) 
        {
        fail("BadRequestException");
        }
        assertEquals("[0, 0, 0]", request.earthdistances().toString());
    }

    @Test
    @DisplayName("c836926049: Test - Large Tour")
    public void testLargeNumberOfPlaces() 
    {
    
        for (int i = 0; i < 500; i++) 
        {
        places.add(new Place(String.valueOf(i % 90), String.valueOf(i % 180)));
        }

    try 
    {
        request.buildResponse();
    } 
    catch (BadRequestException e) 
    {
        fail("BadRequestException");
    }
    assertEquals(500, request.earthdistances().size());
    }

    @Test
    @DisplayName("c836926049: Test - Full circle trip edge case")
    public void testCompleteCircleTrip() 
    {
        places.add(new Place("0", "0"));
        places.add(new Place("0", "90"));
        places.add(new Place("0", "180"));
        places.add(new Place("0", "-90"));
    try 
    {
        request.buildResponse();
    } 
    catch (BadRequestException e) 
    {
        fail("BadRequestException");
    }
    assertEquals("[10008, 10008, 10008, 10008]", request.earthdistances().toString());
    }
    
}
