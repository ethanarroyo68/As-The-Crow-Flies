package com.tco.misc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCalculatorFactory {

    @Test
    @DisplayName("mboin: Test with null formula")
    public void testGetCalculatorWithNullFormula() {
        DistanceCalculator calculator = CalculatorFactory.getCalculator(null);
        assertTrue(calculator instanceof VincentyCalculator);
    }

    @Test
    @DisplayName("mboin: Test with empty formula")
    public void testGetCalculatorWithEmptyFormula() {
        DistanceCalculator calculator = CalculatorFactory.getCalculator("");
        assertTrue(calculator instanceof VincentyCalculator);
    }

    @Test
    @DisplayName("mboin: Test with 'cosines' formula")
    public void testGetCalculatorWithCosinesFormula() {
        DistanceCalculator calculator = CalculatorFactory.getCalculator("cosines");
        assertTrue(calculator instanceof CosinesCalculator);
    }

    @Test
    @DisplayName("mboin: Test with 'haversine' formula")
    public void testGetCalculatorWithHaversineFormula() {
        DistanceCalculator calculator = CalculatorFactory.getCalculator("haversine");
        assertTrue(calculator instanceof HaversineCalculator);
    }

    @Test
    @DisplayName("mboin: Test with 'vincenty' formula")
    public void testGetCalculatorWithVincentyFormula() {
        DistanceCalculator calculator = CalculatorFactory.getCalculator("vincenty");
        assertTrue(calculator instanceof VincentyCalculator);
    }

    @Test
    @DisplayName("mboin: Test with unknown formula")
    public void testGetCalculatorWithUnknownFormula() {
        DistanceCalculator calculator = CalculatorFactory.getCalculator("unknown");
        assertTrue(calculator instanceof VincentyCalculator); // Default case
    }
}
