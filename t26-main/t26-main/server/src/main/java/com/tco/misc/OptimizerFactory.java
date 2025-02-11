package com.tco.misc;

public class OptimizerFactory {
    
    public static TourConstruction getOptimizer(int N, Double response) {
        if (response == 0.0 || N == 1) {
            return new NoOptimizer();
        }
        // Benchmarking revealed the following values for response times
        if (N <= 100 && response >= 2.0) {
            return new ThreeOptimizer();
        } else if (N <= 300 && response >= 1.0) {
            return new TwoOptimizer();
        } else if (N <= 1000 && response >= 1.0) {
            return new OneOptimizer();
        } else {
            return new OneOptimizer(); // WARNING: Limitations of 1-opt under one second desired response time not tested
        }
    }
}
