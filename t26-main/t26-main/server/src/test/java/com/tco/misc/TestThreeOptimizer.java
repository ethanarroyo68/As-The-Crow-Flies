package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class TestThreeOptimizer {

    private ThreeOptimizer threeOptimizer;
    private SimpleCalculator simpleCalculator;
    private Places testingPlaces;

    @BeforeEach
    public void setUp() 
    {
        threeOptimizer = new ThreeOptimizer();
        simpleCalculator = new SimpleCalculator();
        testingPlaces = new Places();

        Place place1 = new Place("30.0", "40.0");
        Place place2 = new Place("35.0", "45.0");
        Place place3 = new Place("40.0", "50.0");

        simpleCalculator.setDistance(place1, place2, 10);
        simpleCalculator.setDistance(place2, place1, 10);
        simpleCalculator.setDistance(place2, place3, 20);
        simpleCalculator.setDistance(place3, place2, 20);
        simpleCalculator.setDistance(place1, place3, 15);
        simpleCalculator.setDistance(place3, place1, 15);

        testingPlaces.add(place1);
        testingPlaces.add(place2);
        testingPlaces.add(place3);
    }

    @Test
    @DisplayName("c836926049 - Test: Construct with empty places")
    public void testConstructWithEmptyPlaces() 
    {
        Places emptyPlaces = new Places();
        Places result = threeOptimizer.construct(emptyPlaces, 6371.0, "simple", 1.0);
        assertTrue(result.isEmpty(), "Expected empty tour when input places are empty.");
    }

    @Test
    @DisplayName("c836926049 - Test: Construct with single place - Size Check")
    public void testConstructWithSinglePlace_SizeCheck() {
        Places places = new Places();
        Place singlePlace = new Place("0.0", "0.0");
        singlePlace.put("id", "SizeCheckID");
        places.add(singlePlace);
    
        //set distance to itself
        simpleCalculator.setDistance(singlePlace, singlePlace, 0);
    
        Places result = threeOptimizer.construct(places, 1.0, "simple", 1.0); 
        assertEquals(1, result.size(), "Expected tour to contain exactly one place.");
    }
    
    @Test
    @DisplayName("c836926049 - Test: Construct with different single place - ID Check")
    public void testConstructWithDifferentSinglePlace_IDCheck() 
    {
        Places places = new Places();
        Place anotherPlace = new Place("1.0", "1.0");
        anotherPlace.put("id", "IDCheckID");
        places.add(anotherPlace);
    
        //set distance to itself
        simpleCalculator.setDistance(anotherPlace, anotherPlace, 0);
    
        Places result = threeOptimizer.construct(places, 1.0, "simple", 1.0); 
        assertEquals("IDCheckID", result.get(0).id(), "Expected place ID to match the input place.");
    }

    @Test
    @DisplayName("dampierj - Test: Improve")
    void testImproveWithSimpleCalculator() {
        Places result = threeOptimizer.construct(testingPlaces, 6371.0, "Simple", 1.0);
        threeOptimizer.improve();

        assertNotNull(result, "Improved tour should not be null.");
        assertEquals(3, result.size(), "Improved tour should still include all places.");
    }

    @Test
    @DisplayName("dampierj - Test: Reverse Segment")
    void testReverseSegmentUsingSimpleCalculator() throws Exception {
        Place place1 = testingPlaces.get(0);
        Place place2 = testingPlaces.get(1);
        Place place3 = testingPlaces.get(2);

        Field tourField = ThreeOptimizer.class.getDeclaredField("tour");
        tourField.setAccessible(true);

        Places tour = new Places();
        tour.add(place1);
        tour.add(place2);
        tour.add(place3);
        tourField.set(threeOptimizer, tour);

        Method reverseSegmentMethod = ThreeOptimizer.class.getDeclaredMethod("reverseSegment", int.class, int.class);
        reverseSegmentMethod.setAccessible(true);

        reverseSegmentMethod.invoke(threeOptimizer, 0, 2);

        Places updatedTour = (Places) tourField.get(threeOptimizer);
        assertEquals(place3, updatedTour.get(0), "Reversed segment should correctly reverse the order.");
        assertEquals(place1, updatedTour.get(2), "Reversed segment should correctly reverse the order.");
    }

    //simple calc for unit testing
    private static class SimpleCalculator implements DistanceCalculator 
    {
        private final HashMap<String, HashMap<String, Long>> distances = new HashMap<>();

        public void setDistance(Place from, Place to, long distance) 
        {
            distances.putIfAbsent(from.id(), new HashMap<>());
            distances.get(from.id()).put(to.id(), distance);
        }

        @Override
        public long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius) {
            if (from instanceof Place && to instanceof Place) 
            {
                Place fromPlace = (Place) from;
                Place toPlace = (Place) to;
                return distances.getOrDefault(fromPlace.id(), new HashMap<>())
                        .getOrDefault(toPlace.id(), Long.MAX_VALUE);
            }
            return Long.MAX_VALUE;
        }
    }
}
