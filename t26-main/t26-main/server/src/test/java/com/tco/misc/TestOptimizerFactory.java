package com.tco.misc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestOptimizerFactory {

    private OptimizerFactory optimizerFactory;

    @BeforeEach
    public void setUp() 
    {
        optimizerFactory = new OptimizerFactory();
    }

    @Test
    @DisplayName("c836926049 - Test: Get NoOptimizer for N=1 and response=0.0")
    public void testGetNoOptimizerForN1AndResponseZero() 
    {
        TourConstruction optimizer = OptimizerFactory.getOptimizer(1, 0.0);
        assertTrue(optimizer instanceof NoOptimizer, "Expected NoOptimizer for N=1 and response=0.0");
    }

    @Test
    @DisplayName("c836926049 - Test: Get ThreeOptimizer for N<=100 and response>=2.0")
    public void testGetThreeOptimizerForNLessThanEqual100AndResponseGreaterThanEqual2() 
    {
        TourConstruction optimizer = OptimizerFactory.getOptimizer(100, 2.0);
        assertTrue(optimizer instanceof ThreeOptimizer, "Expected ThreeOptimizer for N <= 100 and response >= 2.0");
    }

    @Test
    @DisplayName("c836926049 - Test: Get TwoOptimizer for N<=300 and response>=1.0")
    public void testGetTwoOptimizerForNLessThanEqual300AndResponseGreaterThanEqual1() 
    {
        TourConstruction optimizer = OptimizerFactory.getOptimizer(300, 1.0);
        assertTrue(optimizer instanceof TwoOptimizer, "Expected TwoOptimizer for N <= 300 and response >= 1.0");
    }

    @Test
    @DisplayName("c836926049 - Test: Get OneOptimizer for N<=1000 and response>=1.0")
    public void testGetOneOptimizerForNLessThanEqual1000AndResponseGreaterThanEqual1() 
    {
        TourConstruction optimizer = OptimizerFactory.getOptimizer(1000, 1.0);
        assertTrue(optimizer instanceof OneOptimizer, "Expected OneOptimizer for N <= 1000 and response >= 1.0");
    }

    @Test
    @DisplayName("c836926049 - Test: Get OneOptimizer for N>1000 and response<1.0")
    public void testGetOneOptimizerForNGreaterThan1000AndResponseLessThan1() 
    {
        TourConstruction optimizer = OptimizerFactory.getOptimizer(1500, 0.5);
        assertTrue(optimizer instanceof OneOptimizer, "Expected OneOptimizer for N > 1000 and response < 1.0");
    }
}
