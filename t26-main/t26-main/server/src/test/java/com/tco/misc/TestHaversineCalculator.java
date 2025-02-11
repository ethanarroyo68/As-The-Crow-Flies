package com.tco.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tco.misc.HaversineCalculator;
import com.tco.misc.GeographicCoordinate;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.toRadians;

//import static com.tco.misc.HaversineCalculator.between; - BREAKS CODE - INSTANTIATE HAVERSINE DIRECTLY or change static

public class TestHaversineCalculator {

    class Geo implements GeographicCoordinate {

        Double degreesLatitude;
        Double degreesLongitude;

        // Constructor
        public Geo(Double lat, Double lon)
        {
            this.degreesLatitude = lat;
            this.degreesLongitude = lon;
        }

        // Convert latitude to radians
        public Double latRadians()
        {
            return Math.toRadians(degreesLatitude);
        }

        // Convert longitude to radians
        public Double lonRadians()
        {
            return Math.toRadians(degreesLongitude);
        }
    }

    // Latitude and Longitude for the test locations
    final Geo aurora = new Geo(39.7294, -104.8319);
    final Geo losAngeles = new Geo(34.0522, -118.2437);
    final Geo fortCollins = new Geo(40.5853, -105.0844);
    final Geo sanFrancisco = new Geo(37.7749, -122.4194);
    final Geo gunungKerinci = new Geo(1.6967, 101.2642);
    final Geo cayambe = new Geo(0.0425, -78.1459);

    // Earth's radius (km)
    final static double earthRadius = 6371.0;

    // For just the final test
    double bigEarthRadius = 1e+17;

    final Geo place1 = new Geo(-10.0, 0.0);
    final Geo place2 = new Geo(10.0, 0.0);

    // TESTCASE 1 - Failing Test - San Francisco to Fort Collins ***************************************

    @Test
    @DisplayName("dampierj: Failing Test - Incorrect Distance between Aurora and Fort Collins")
    public void testFailingDistanceBetweenAuroraAndFortCollins()
    {
        HaversineCalculator calculator = new HaversineCalculator();  // Instantiate HaversineCalculator
        long incorrectExpectedDistance = 1000L;  // Intentionally incorrect expected distance
        long actualDistance = calculator.between(aurora, fortCollins, earthRadius);

        // Assert that the actual distance does NOT match the incorrect expected value
        assertNotEquals(incorrectExpectedDistance, actualDistance, "The test should fail with incorrect expected distance.");
    }

    // END - TESTCASE 1 *******************************************************************************

    // TESTCASE 2 - Successful Distance Test - Aurora to Fort Collins ***************************

    @Test
    @DisplayName("dampierj: Passing Test - Correct Distance between Aurora and Fort Collins")
    public void testDistanceBetweenAuroraAndFortCollins()
    {
        HaversineCalculator calculator = new HaversineCalculator();  // Instantiate HaversineCalculator
        long expectedDistance = 98L;  // Correct distance rounded to nearest long
        long actualDistance = calculator.between(aurora, fortCollins, earthRadius);

        // Assert that the actual distance matches the correct expected value
        assertEquals(expectedDistance, actualDistance, "The distance should be approximately 98 km");
    }

    // END - TESTCASE 2 ********************************************************************************        

    // TESTCASE 3 - Distance to Itself - Aurora to Aurora ********************************        
    @Test
    @DisplayName("dampierj: Point to itself Test - Distance from a point to itself should be zero")
    public void testDistanceToItself()
    {
        HaversineCalculator calculator = new HaversineCalculator();  // Instantiate HaversineCalculator
        long expectedDistance = 0L;  // Distance to itself should be zero
        long actualDistance = calculator.between(aurora, aurora, earthRadius);

        assertEquals(expectedDistance, actualDistance, "The distance from a point to itself should be 0 km");   
    }

    // END - TESTCASE 3 ********************************************************************************        

    // TESTCASE 4 - Failing Test - Med Range ***********************************************************

    @Test
    @DisplayName("dampierj: Failing Test - Incorrect Distance between Aurora and Sam Fransisco")
    public void testFailingDistanceBetweenAuroraAndSanFransisco()
    {
        HaversineCalculator calculator = new HaversineCalculator();  // Instantiate HaversineCalculator
        long incorrectExpectedDistance = 1000L;                      // Intentionally incorrect expected distance
        long actualDistance = calculator.between(aurora, sanFrancisco, earthRadius);

        // Assert that the actual distance does NOT match the incorrect expected value
        assertNotEquals(incorrectExpectedDistance, actualDistance, "The test should fail with incorrect expected distance.");
    }

    // END - TESTCASE 4 *******************************************************************************

    // TESTCASE 5 - Successful Distance Test - Med Range **********************************************

    @Test
    @DisplayName("dampierj: Passing Test - Correct Distance between Aurora and San Fransisco")
    public void testDistanceBetweenAuroraAndSanFransisco()
    {
        HaversineCalculator calculator = new HaversineCalculator();  // Instantiate HaversineCalculator
        long expectedDistance = 1538L;  // Correct distance rounded to nearest long
        long actualDistance = calculator.between(aurora, sanFrancisco, earthRadius);

        // Assert that the actual distance matches the correct expected value
        assertEquals(expectedDistance, actualDistance, "The distance should be approximately 1538 km");
    }

    // END - TESTCASE 5 ********************************************************************************  

    // TESTCASE 4 - Failing Test - Med Range ***********************************************************

    @Test
    @DisplayName("dampierj: Failing Test - Incorrect Distance between Cayambe and Gunung Kerinci")
    public void testFailingDistanceBetweenCayambeAndGunungKerinci()
    {
        HaversineCalculator calculator = new HaversineCalculator();  // Instantiate HaversineCalculator
        long incorrectExpectedDistance = 1000L;                      // Intentionally incorrect expected distance
        long actualDistance = calculator.between(cayambe, gunungKerinci, earthRadius);

        // Assert that the actual distance does NOT match the incorrect expected value
        assertNotEquals(incorrectExpectedDistance, actualDistance, "The test should fail with incorrect expected distance.");
    }

    // END - TESTCASE 4 *******************************************************************************

    // TESTCASE 5 - Successful Distance Test - Med Range **********************************************

    @Test
    @DisplayName("dampierj: Passing Test - Correct Distance between Cayambe and Gunung Kerinci")
    public void testDistanceBetweenCayambeAndGunungKerinci()
    {
        HaversineCalculator calculator = new HaversineCalculator();  // Instantiate HaversineCalculator
        long expectedDistance = 19811L;  // Correct distance rounded to nearest long
        long actualDistance = calculator.between(cayambe, gunungKerinci, earthRadius);

        // Assert that the actual distance matches the correct expected value
        assertEquals(expectedDistance, actualDistance, "The distance should be approximately 19811 km");
    }

    // END - TESTCASE 5 ********************************************************************************

    // TESTCASE 6 - Successful Distance Test - Ultra Long Range **********************************************

    @Test
    @DisplayName("c835266433: Passing Test - Correct Distance Bewteen two arbitrary places on a massive earth")
    public void testDistanceBetweenArbitraryPlaces()
    {
        HaversineCalculator calculator = new HaversineCalculator();  // Instantiate HaversineCalculator
        long expectedDistance = 34906585039886588L;  // Correct distance rounded to nearest long
        long actualDistance = calculator.between(place1, place2, bigEarthRadius);

        // Assert that the actual distance matches the correct expected value
        assertEquals(expectedDistance, actualDistance, "The distance should be approximately 34906585039886588 km");
    }

    // END - TESTCASE 6 ********************************************************************************
}
