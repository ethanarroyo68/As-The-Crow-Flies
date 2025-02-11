package com.tco.misc;

public class CalculatorFactory {

    public static DistanceCalculator getCalculator(String formula) {
        if (formula == null || formula.isEmpty()) {
            return new VincentyCalculator();
        }

        switch (formula.toLowerCase()) {
            case "cosines":
                return new CosinesCalculator();
            case "haversine":
                return new HaversineCalculator();
            case "vincenty":
                return new VincentyCalculator();
            default:
                return new VincentyCalculator();
        }
    }
}
