package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestNoOptimizer {

    private NoOptimizer noOptimizer;

    @BeforeEach
    public void setUp() 
    {
        noOptimizer = new NoOptimizer();
    }

    @Test
    @DisplayName("c836926049: Test - No tour when no places provided")
    public void testNoTour() 
    {
        
        Places emptyPlaces = new Places();

        //construct tour
        Places result = noOptimizer.construct(emptyPlaces, 1.0, "simple", 0.0);
        assertTrue(result.isEmpty(), "Expected an empty tour with no places.");

    }

    @Test
    @DisplayName("c836926049: Test - Single place remains unchanged")
    public void testSinglePlace() 
    {

        Place placeA = new Place("0.0", "0.0");
        placeA.put("id", "PlaceA");

        //add to places
        Places testPlaces = new Places();
        testPlaces.add(placeA);

        //construct tour
        Places result = noOptimizer.construct(testPlaces, 1.0, "simple", 0.0);

        assertEquals(1, result.size(), "Expected a tour with one place.");
        assertEquals(placeA, result.get(0), "PlaceA should remain unchanged.");
    }

    @Test
    @DisplayName("c836926049: Test - Multiple places remain unchanged")
    public void testMultiplePlaces() 
    {
        //initialize places
        Place placeA = new Place("0.0", "0.0");
        placeA.put("id", "PlaceA");
        Place placeB = new Place("1.0", "1.0");
        placeB.put("id", "PlaceB");

        //add to places
        Places testPlaces = new Places();
        testPlaces.add(placeA);
        testPlaces.add(placeB);

        //construct tour
        Places result = noOptimizer.construct(testPlaces, 1.0, "simple", 0.0);

        assertEquals(2, result.size(), "Expected a tour with two places.");
        assertEquals(placeA, result.get(0), "First place should remain as PlaceA.");
        assertEquals(placeB, result.get(1), "Second place should remain as PlaceB.");
    }

    @Test
    @DisplayName("c836926049: Test - Improve method does nothing")
    public void testImproveDoesNothing() 
    {
      
        //initialize places
        Places places = new Places();
        Place placeA = new Place("1.0", "1.0");
        placeA.put("id", "PlaceA");
        places.add(placeA);


        noOptimizer.improve();

        //verify no changes 
        assertEquals(1, places.size(), "The number of places should remain unchanged.");
        assertEquals(placeA, places.get(0), "The place should remain unchanged.");
    }

    @Test
    @DisplayName("c836926049: Test - Construct returns same object")
    public void testConstructReturnsSameObject() 
    {
        //initialize places
        Places testPlaces = new Places();
        Place placeA = new Place("2.0", "2.0");
        placeA.put("id", "PlaceA");
        testPlaces.add(placeA);

        //construct tour
        Places result = noOptimizer.construct(testPlaces, 1.0, "simple", 0.0);

        //verify original object returned
        assertSame(testPlaces, result, "Expected the same Places object to be returned.");
    }

}
