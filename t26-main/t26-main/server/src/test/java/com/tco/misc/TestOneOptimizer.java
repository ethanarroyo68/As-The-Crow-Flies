package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.HashMap;

public class TestOneOptimizer {

    private TourConstruction TourConstruction;
    private NoOptimizer noOptimizer;
    private OneOptimizer oneOptimizer;
    private SimpleCalculator simpleCalculator;

    @BeforeEach
    public void setUp() 
    {
        oneOptimizer = new OneOptimizer();
        simpleCalculator = new SimpleCalculator();
    }

    //c836926049 - Testing

    @Test
    @DisplayName("c836926049 - Test: No tour when no places provided")
    public void testNoTour()
    {
        //Add to places
        Places emptyPlaces = new Places();

        //Construct tour
        Places result = oneOptimizer.construct(emptyPlaces, 1.0, "simple", 0.0);
        assertTrue(result.isEmpty(), "Expected an empty tour with no places.");
    }

    @Test
    @DisplayName("quintenr: one place tour")
    public void singletour() 
    { 
        //Initialize place(s)
        Place placeA = new Place("0.0", "0.0");
        placeA.put("id", "PlaceA");

        //Add to places
        Places testPlaces = new Places();
        testPlaces.add(placeA);

        //Construct tour
        Places result = oneOptimizer.construct(testPlaces, 1.0, "simple", 0.0);

        assertEquals(placeA, result.get(0), "PlaceA.");
    }

    @Test
    @DisplayName("c836926049 - Test: Find nearest neighbor")
    public void testFindNearestNeighbor() throws Exception
    {
        //Initialize place(s)
        Place placeA = new Place("0.0", "0.0");
        placeA.put("id", "PlaceA");
        Place placeB = new Place("1.0", "1.0");
        placeB.put("id", "PlaceB");
        Place placeC = new Place("2.0", "2.0");
        placeC.put("id", "PlaceC");

        //Add to places
        Places testPlaces = new Places();
        testPlaces.add(placeA);
        testPlaces.add(placeB);
        testPlaces.add(placeC);

        //set distances
        simpleCalculator.setDistance(placeA, placeB, 1);
        simpleCalculator.setDistance(placeA, placeC, 3);

        //Construct tour
        Places result = oneOptimizer.construct(testPlaces, 1.0, "simple", 0.5);
        assertEquals(3, result.size(), "Expected 3 place tour.");
        assertEquals(placeA, result.get(0), "PlaceA.");
        assertEquals(placeB, result.get(1), "PlaceB.");
        assertEquals(placeC, result.get(2), "PlaceC.");
    }

    @Test
    @DisplayName("c836926049 - Test: Equidistant neighbors")
    public void testEquidistantNeighbors() throws Exception 
    {
        //Initialize place(s)
        Place placeX = new Place("0.0", "0.0");
        placeX.put("id", "PlaceX");
        Place placeY = new Place("1.0", "1.0");
        placeY.put("id", "PlaceY");
        Place placeZ = new Place("2.0", "2.0");
        placeZ.put("id", "PlaceZ");

        //Add to places
        Places testPlaces = new Places();
        testPlaces.add(placeX);
        testPlaces.add(placeY);
        testPlaces.add(placeZ);

        //set distances
        long expectedDistance = 1;
        simpleCalculator.setDistance(placeX, placeY, expectedDistance);
        simpleCalculator.setDistance(placeX, placeZ, expectedDistance);     

        // //invoke method
        Places result = oneOptimizer.construct(testPlaces, 1.0, "simple", 0.5);
        assertEquals(3, result.size(), "Expected 3 place tour.");
        assertEquals(placeX, result.get(0), "PlaceX.");
        assertEquals(placeY, result.get(1), "PlaceY.");
        assertEquals(placeZ, result.get(2), "PlaceZ.");
    }

    @Test
    @DisplayName("dampierj - Test: Improve does Nothing")
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
        OneOptimizer oneOptimizer = new OneOptimizer();

        //Construct tour
        Places optimizedTour = oneOptimizer.construct(places, 1.0, "haversine", 5.0);

        assertEquals(4, optimizedTour.size(), "Expected a tour with 4 places.");
        assertEquals(placeA, optimizedTour.get(0), "First place should be PlaceA.");
        assertEquals(placeB, optimizedTour.get(1), "Second place should be PlaceB.");
        assertEquals(placeC, optimizedTour.get(2), "Third place should be PlaceC.");
        assertEquals(placeD, optimizedTour.get(3), "Fourth place should be PlaceD.");
    }

    @Test
    @DisplayName("dampierj - Test: Improve does even more nothing cus test coverage still bad")
    void testImproveMethodExists() {
        OneOptimizer optimizer = new OneOptimizer();
        assertDoesNotThrow(optimizer::improve, "The improve method should exist and not throw exceptions.");
    }

    // SimpleCalculator class implementation for unit testing NearestNeighbor
    private static class SimpleCalculator implements DistanceCalculator 
    {
       
        private final HashMap<String, HashMap<String, Long>> distances = new HashMap<>();

        // Set distance between two places
        public void setDistance(Place from, Place to, long distance) 
        { 
            distances.putIfAbsent(from.id(), new HashMap<>());
            distances.get(from.id()).put(to.id(), distance);
        }

        // Implement the distance calculation method from DistanceCalculator
        @Override
        public long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius) 
        {
            if (from instanceof Place && to instanceof Place) 
            {
                Place fromPlace = (Place) from;
                Place toPlace = (Place) to;
                return distances.getOrDefault(fromPlace.id(), new HashMap<>()).getOrDefault(toPlace.id(), Long.MAX_VALUE);      
            }
            return Long.MAX_VALUE; // Default to a very large value if no distance is set
        }
    }
    //c836926049 - Testing END

}
