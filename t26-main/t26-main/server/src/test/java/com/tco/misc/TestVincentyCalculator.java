package test.java.com.tco.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tco.misc.VincentyCalculator;
import com.tco.misc.GeographicCoordinate;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.toRadians;

//import static com.tco.misc.VincentyCalculator.between; - BREAKS CODE - INSTANTIATE Vincenty DIRECTLY or change static

public class TestVincentyCalculator {

    class Geo implements GeographicCoordinate {

        Double degreesLatitude;
        Double degreesLongitude;

        // Constructor
        public Geo(Double lat, Double lon) {
            this.degreesLatitude = lat;
            this.degreesLongitude = lon;
        }

        // Convert latitude to radians
        public Double latRadians() {
            return Math.toRadians(degreesLatitude);
        }

        // Convert longitude to radians
        public Double lonRadians() {
            return Math.toRadians(degreesLongitude);
        }
    }

    // Ethan Arroyo's test cases:
    final Geo csuOval = new Geo(40.58, -105.08);
    final Geo theBean = new Geo(41.88, -87.62);

    // All these test cases were taken from david115's distances tests entry:
    final Geo tokyo = new Geo(35.682839, 139.759455);
    final Geo newYork = new Geo(40.712776, -74.005974);
    final Geo sydney = new Geo(-33.868820, 151.209290);
    final Geo buenosAires = new Geo(-34.603722, -58.381592);

    // Earth's radius (km)
    double earthRadius = 5000.0;

    // // TESTCASE 1 - Failing Test - Tokyo to New York
    // ***************************************

    @Test
    @DisplayName("c835266433: Failing Test - Incorrect Distance between Tokyo and New York")
    public void testFailingDistanceBetweenTokyoAndNewYork() {
        VincentyCalculator calculator = new VincentyCalculator(); // Instantiate VincentyCalculator
        long incorrectExpectedDistance = 5000L; // Intentionally incorrect expected distance
        long actualDistance = calculator.between(tokyo, newYork, earthRadius);

        // Assert that the actual distance does NOT match the incorrect expected value
        assertNotEquals(incorrectExpectedDistance, actualDistance,
                "The test should fail with incorrect expected distance.");
    }

    // // TESTCASE 2 - Successful Distance Test - Tokyo to New York
    // ***************************

    @Test
    @DisplayName("c835266433: Passing Test - Correct Distance between Tokyo and New York")
    public void testDistanceBetweenTokyoAndNewYork() {
        VincentyCalculator calculator = new VincentyCalculator(); // Instantiate VincentyCalculator
        long expectedDistance = 8513L; // Correct distance (rounded to nearest long) in km
        long actualDistance = calculator.between(tokyo, newYork, earthRadius);

        // Assert that the actual distance matches the correct expected value
        assertEquals(expectedDistance, actualDistance, "The distance should be approximately 8512 km");
    }

    // // TESTCASE 3 - Failing Test - New York to Sydney
    // ***************************************

    @Test
    @DisplayName("c835266433: Failing Test - Incorrect Distance between New York and Sydney")
    public void testFailingDistanceBetweenNewYorkAndSydney() {
        VincentyCalculator calculator = new VincentyCalculator(); // Instantiate VincentyCalculator
        long incorrectExpectedDistance = 7000L; // Intentionally incorrect expected distance
        long actualDistance = calculator.between(newYork, sydney, earthRadius);

        // Assert that the actual distance does NOT match the incorrect expected value
        assertNotEquals(incorrectExpectedDistance, actualDistance,
                "The test should fail with incorrect expected distance.");
    }

    // // TESTCASE 4 - Successful Distance Test - New York to Sydney
    // ***************************

    @Test
    @DisplayName("c835266433: Passing Test - Correct Distance between New York and Sydney")
    public void testDistanceBetweenNewYorkAndSydney() {
        VincentyCalculator calculator = new VincentyCalculator(); // Instantiate VincentyCalculator
        long expectedDistance = 12548L; // Correct distance (rounded to nearest long) in km
        long actualDistance = calculator.between(newYork, sydney, earthRadius);

        // Assert that the actual distance matches the correct expected value
        assertEquals(expectedDistance, actualDistance, "The distance should be approximately 12548 km");
    }

    // // TESTCASE 5 - Failing Test - Sydney to Buenos Aires
    // ***************************************

    @Test
    @DisplayName("c835266433: Failing Test - Incorrect Distance between Sydney and Buenos Aires")
    public void testFailingDistanceBetweenSydneyAndBuenosAires() {
        VincentyCalculator calculator = new VincentyCalculator(); // Instantiate VincentyCalculator
        long incorrectExpectedDistance = 8000L; // Intentionally incorrect expected distance
        long actualDistance = calculator.between(sydney, buenosAires, earthRadius);

        // Assert that the actual distance does NOT match the incorrect expected value
        assertNotEquals(incorrectExpectedDistance, actualDistance,
                "The test should fail with incorrect expected distance.");
    }

    // // TESTCASE 6 - Successful Distance Test - Sydney to Buenos Aires
    // ***************************

    @Test
    @DisplayName("c835266433: Passing Test - Correct Distance between Sydney and Buenos Aires")
    public void testDistanceBetweenSydneyAndBuenosAires() {
        VincentyCalculator calculator = new VincentyCalculator(); // Instantiate VincentyCalculator
        long expectedDistance = 9262L; // Correct distance (rounded to nearest long) in km
        long actualDistance = calculator.between(sydney, buenosAires, earthRadius);

        // Assert that the actual distance matches the correct expected value
        assertEquals(expectedDistance, actualDistance, "The distance should be approximately 9261 km");
    }

    // // TESTCASE 7 - Failing Test - Buenos Aires to Tokyo
    // ***************************************

    @Test
    @DisplayName("c835266433: Failing Test - Incorrect Distance between Buenos Aires and Tokyo")
    public void testFailingDistanceBetweenBuenosAiresAndTokyo() {
        VincentyCalculator calculator = new VincentyCalculator(); // Instantiate VincentyCalculator
        long incorrectExpectedDistance = 9000L; // Intentionally incorrect expected distance
        long actualDistance = calculator.between(buenosAires, tokyo, earthRadius);

        // Assert that the actual distance does NOT match the incorrect expected value
        assertNotEquals(incorrectExpectedDistance, actualDistance,
                "The test should fail with incorrect expected distance.");
    }

    // // TESTCASE 8 - Successful Distance Test - Buenos Aires to Tokyo
    // ***************************

    @Test
    @DisplayName("c835266433: Passing Test - Correct Distance between Buenos Aires and Tokyo")
    public void testDistanceBetweenBuenosAiresAndTokyo() {
        VincentyCalculator calculator = new VincentyCalculator(); // Instantiate VincentyCalculator
        long expectedDistance = 14412L; // Correct distance (rounded to nearest long) in km
        long actualDistance = calculator.between(buenosAires, tokyo, earthRadius);

        // Assert that the actual distance matches the correct expected value
        assertEquals(expectedDistance, actualDistance, "The distance should be approximately 14411 km");
    
    }
    // // TESTCASE 9 - Successful Distance Test - Tokyo to Tokyo
    // *

    @Test
    @DisplayName("c835266433: Passing Test - Distance from Tokyo to itself")
    public void testDistanceBetweenTokyoAndTokyo() {
        VincentyCalculator calculator = new VincentyCalculator(); // Instantiate VincentyCalculator
        long expectedDistance = 0L; // Correct distance (rounded to nearest long) in km
        long actualDistance = calculator.between(tokyo, tokyo, earthRadius);

        // Assert that the actual distance matches the correct expected value
        assertEquals(expectedDistance, actualDistance, "The distance should be approximately 0 km");
    }
}
