package com.tco.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tco.misc.CosinesCalculator;
import com.tco.misc.GeographicCoordinate;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.toRadians;

//import static com.tco.misc.CosinesCalculator.between; - BREAKS CODE - INSTANTIATE COSINES DIRECTLY or change static


public class TestCosinesCalculator {

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
    final Geo sanFrancisco = new Geo(37.7749, -122.4194);
    final Geo losAngeles = new Geo(34.0522, -118.2437);
    final Geo denver = new Geo(39.7392, 104.9903);
    final Geo fortCollins = new Geo(40.5853, 105.0844);
    final Geo equator = new Geo(0.0, 0.0);
    final Geo northPole = new Geo(0.0, 90.0);
    final Geo delhi = new Geo(28.6139, 77.2090);
    final Geo raleigh_NC = new Geo(35.7796, -78.6382);
    final Geo rio_brazil = new Geo(-22.9068, -43.1729);
    final Geo paris_france = new Geo(48.8575, 2.3514);


    // Earth's radius (km)
    final static double earthRadius = 6371.0;

    // TESTCASE 1 - Failing Test - San Francisco to Los Angeles ***************************************

    @Test
    @DisplayName("c836926049: Failing Test - Incorrect Distance between San Francisco and Los Angeles")
    public void testFailingDistanceBetweenSanFranciscoAndLosAngeles()
    {
        CosinesCalculator calculator = new CosinesCalculator();  // Instantiate CosinesCalculator
        long incorrectExpectedDistance = 1000L;  // Intentionally incorrect expected distance
        long actualDistance = calculator.between(sanFrancisco, losAngeles, earthRadius);

        // Assert that the actual distance does NOT match the incorrect expected value
        assertNotEquals(incorrectExpectedDistance, actualDistance, "The test should fail with incorrect expected distance.");
    }

    // END - TESTCASE 1 *******************************************************************************

    // TESTCASE 2 - Successful Distance Test - San Francisco to Los Angeles ***************************

    @Test
    @DisplayName("c836926049: Passing Test - Correct Distance between San Francisco and Los Angeles")
    public void testDistanceBetweenSanFranciscoAndLosAngeles()
    {
        CosinesCalculator calculator = new CosinesCalculator();  // Instantiate CosinesCalculator
        long expectedDistance = 559L;  // Correct distance rounded to nearest long
        long actualDistance = calculator.between(sanFrancisco, losAngeles, earthRadius);

        // Assert that the actual distance matches the correct expected value
        assertEquals(expectedDistance, actualDistance, "The distance should be approximately 559 km");
    }

    // END - TESTCASE 2 ********************************************************************************

    // TESTCASE 3 - Distance to Itself - San Francisco to San Francisco ********************************
    @Test
    @DisplayName("c836926049: Point to itself Test - Distance from a point to itself should be zero")
    public void testDistanceToItself()
    {
        CosinesCalculator calculator = new CosinesCalculator();  // Instantiate CosinesCalculator
        long expectedDistance = 0L;  // Distance to itself should be zero
        long actualDistance = calculator.between(sanFrancisco, sanFrancisco, earthRadius);

        assertEquals(expectedDistance, actualDistance, "The distance from a point to itself should be 0 km");
    }

    // END - TESTCASE 3 ********************************************************************************

    // TESTCASE 4 - Correct Distance - Denver to Fort Collins ******************************************
    @Test
    @DisplayName("c836926049: Second Short Distance - Denver to Fort Collins")
    public void testDistanceBetweenDenverandFortCollins()
        {
            CosinesCalculator calculator = new CosinesCalculator();  // Instantiate CosinesCalculator
            long expectedDistance = 94L;  // Distance to itself should be zero
            long actualDistance = calculator.between(denver, fortCollins, earthRadius);

            assertEquals(expectedDistance, actualDistance, "The distance from a Denver to Fort Collins should be 94 km");
        }

    // END - TESTCASE 4 ********************************************************************************

    // TESTCASE 5 - Correct Distance - North Pole to Equator *******************************************
    @Test
    @DisplayName("c836926049: Correct Distance Test - North Pole to Equator")
    public void testDistanceBetweenNorthPoleAndEquator()
        {
            CosinesCalculator calculator = new CosinesCalculator();  // Instantiate CosinesCalculator
            long expectedDistance = 10008L;  // Distance to itself should be zero
            long actualDistance = calculator.between(northPole, equator, earthRadius);

            assertEquals(expectedDistance, actualDistance, "The distance from the North Pole to the Equator should be 10008 km");
        }

    // END - TESTCASE 5 ********************************************************************************

    // TESTCASE 6 - Correct Distance - Raleigh_NC to Delhi India ***************************************
    @Test
    @DisplayName("c836926049: Correct Long Distance Test - Raleigh_NC to Delhi India")
    public void testDistanceBetween_Raleigh_NC_to_Delhi_India()
        {
            CosinesCalculator calculator = new CosinesCalculator();  // Instantiate CosinesCalculator
            long expectedDistance = 12421L;  // Distance to itself should be zero
            long actualDistance = calculator.between(raleigh_NC, delhi, earthRadius);

            assertEquals(expectedDistance, actualDistance, "The distance from Raleigh NC to Delhi India should be 12421 km");
        }

    // END - TESTCASE 6 ********************************************************************************

     // TESTCASE 7 - Correct Distance - Rio Brazil to Paris France *************************************
     @Test
     @DisplayName("c836926049: Correct Long Distance Test - Rio Brazil to Paris France")
     public void testDistanceBetween_Paris_France_to_Rio_Brazil()
         {
             CosinesCalculator calculator = new CosinesCalculator();  // Instantiate CosinesCalculator
             long expectedDistance = 9167L;  // Distance to itself should be zero
             long actualDistance = calculator.between(rio_brazil, paris_france, earthRadius);

             assertEquals(expectedDistance, actualDistance, "The distance from Rio Brazil to Paris France should be 9167 km");
         }

     // END - TESTCASE 7 ********************************************************************************


     // TESTCASE 8 - Correct Distance - Denver to Fort Collins ******************************************
    @Test
    @DisplayName("c836926049: Incorrect Long Distance Test - Rio Brazil to Paris France")
    public void testDistanceBetween_Paris_France_to_Rio_Brazil_Incorrect()
    {
        CosinesCalculator calculator = new CosinesCalculator();  // Instantiate CosinesCalculator
        long expectedDistance = 600L;  // This is the incorrect expected distance
        long actualDistance = calculator.between(rio_brazil, paris_france, earthRadius);

    assertFalse(expectedDistance == actualDistance, "The distance from Rio Brazil to Paris France should not be 600 km");
    }

     // END - TESTCASE 8 ********************************************************************************


     // TESTCASE 9 - Distance Calculation based on JSON ***************************************
    @Test
    @DisplayName("troncosv: Distance Calculation between Seoul, London, Santiago, and Sydney")
    public void testMultiplePlacesDistancesSeoulLondonSantiagoSydney() {
        CosinesCalculator calculator = new CosinesCalculator(); // Instantiate CosinesCalculator

        // Define the places
        Geo seoul = new Geo(37.5665, 126.9780);
        Geo london = new Geo(51.5074, -0.1278);
        Geo santiago = new Geo(-33.4489, -70.6693);
        Geo sydney = new Geo(-33.8688, 151.2093);

        // Earth's radius (km)
        double earthRadius2 = 10000.0;

        // Define the expected distances (in km)
        double[] expectedDistances = {13903, 18322, 17810, 13074};

        // Calculate actual distances using the CosinesCalculator
        long actualDistanceSeoulToLondon = calculator.between(seoul, london, earthRadius2);
        long actualDistanceLondonToSantiago = calculator.between(london, santiago, earthRadius2);
        long actualDistanceSantiagoToSydney = calculator.between(santiago, sydney, earthRadius2);
        long actualDistanceSydneyToSeoul = calculator.between(sydney, seoul, earthRadius2);

        // Compare actual and expected distances
        assertEquals((long) expectedDistances[0], actualDistanceSeoulToLondon, "Distance from Seoul to London should match.");
        assertEquals((long) expectedDistances[1], actualDistanceLondonToSantiago, "Distance from London to Santiago should match.");
        assertEquals((long) expectedDistances[2], actualDistanceSantiagoToSydney, "Distance from Santiago to Sydney should match.");
        assertEquals((long) expectedDistances[3], actualDistanceSydneyToSeoul, "Distance from Sydney to Seoul should match.");
    }
    // END - TESTCASE 9 ********************************************************************************

}