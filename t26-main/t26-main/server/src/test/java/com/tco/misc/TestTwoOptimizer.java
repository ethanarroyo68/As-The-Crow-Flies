package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;
import java.util.HashMap;

import java.lang.reflect.Field;
import java.util.List;

public class TestTwoOptimizer {

    private TwoOptimizer twoOptimizer;
    private SimpleCalculator simpleCalculator;

    @BeforeEach
    public void setUp() {
        twoOptimizer = new TwoOptimizer();
        simpleCalculator = new SimpleCalculator();
    }

    @Test
    @DisplayName("quintenr: Empty tour")
    public void testEmptyTour() {
        //Add to places
        Places emptyPlaces = new Places();
        Places result = twoOptimizer.construct(emptyPlaces, 1.0, "simple", 0.0);
        assertTrue(result.isEmpty(), "Expected empty tour");
    }

    @Test
    @DisplayName("quintenr: Nearest Neighbor")
    public void testNearestNeighbor() throws Exception {
        //Initialize place(s)
        Place placeA = new Place("0.0", "0.0");
        placeA.put("id", "PlaceA");
        Place placeB = new Place("1.0", "1.0");
        placeB.put("id", "PlaceB");
        Place placeC = new Place("2.0", "2.0");
        placeC.put("id", "PlaceC");

        //Add to places        
        Places places = new Places();
        places.add(placeA);
        places.add(placeB);
        places.add(placeC);

        //Set Distance
        simpleCalculator.setDistance(placeA, placeB, 1);
        simpleCalculator.setDistance(placeB, placeC, 1);
        simpleCalculator.setDistance(placeC, placeA, 2); 

        //Construct tour
        Places result = twoOptimizer.construct(places, 1.0, "simple", 0.5);
        assertEquals(3, result.size(), "Expected 3 place tour.");

        assertEquals(placeA, result.get(0), "PlaceA.");
        assertEquals(placeB, result.get(1), "PlaceB.");
        assertEquals(placeC, result.get(2), "PlaceC.");
    }

    @Test
    @DisplayName("dampierj - Test: Improve method base case")
    public void testImproveMethod() throws Exception {
        //Initialize place(s)
        Place placeA = new Place("1.0", "1.0");
        placeA.put("id", "PlaceA");
        Place placeB = new Place("10.0", "10.0");
        placeB.put("id", "PlaceB");
        Place placeC = new Place("20.0", "20.0");
        placeC.put("id", "PlaceC");
        Place placeD = new Place("30.0", "30.0");
        placeD.put("id", "PlaceD");
        
        //Add to places
        Places places = new Places();
        places.add(placeA);
        places.add(placeB);
        places.add(placeC);
        places.add(placeD);

        HaversineCalculator calculator = new HaversineCalculator();

        //Call Optimizer
        TwoOptimizer twoOptimizer = new TwoOptimizer();

        //Construct tour
        Places optimizedTour = twoOptimizer.construct(places, 1.0, "haversine", 5.0);

        assertEquals(4, optimizedTour.size(), "Expected a tour with 4 places.");
        assertEquals(placeA, optimizedTour.get(0), "First place should be PlaceA.");
        assertEquals(placeB, optimizedTour.get(1), "Second place should be PlaceB.");
        assertEquals(placeD, optimizedTour.get(2), "Third place should be PlaceD.");
        assertEquals(placeC, optimizedTour.get(3), "Fourth place should be PlaceC.");
    }

    private static class SimpleCalculator implements DistanceCalculator 
    {
       
        private final HashMap<String, HashMap<String, Long>> distances = new HashMap<>();

        public void setDistance(Place from, Place to, long distance) 
        { 
             distances.putIfAbsent(from.id(), new HashMap<>());
             distances.get(from.id()).put(to.id(), distance);
        }

        @Override
        public long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius) 
        {
            if (from instanceof Place && to instanceof Place) 
            {
                 Place fromPlace = (Place) from;
                 Place toPlace = (Place) to;
                 return distances.getOrDefault(fromPlace.id(), new HashMap<>()).getOrDefault(toPlace.id(), Long.MAX_VALUE);      
            }
            return Long.MAX_VALUE; 
        }
    }
}

