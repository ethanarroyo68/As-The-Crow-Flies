package com.tco.misc;

import static java.lang.Math.*;
import com.tco.misc.GeographicCoordinate;

public class VincentyCalculator implements DistanceCalculator {
    // constructor
    public VincentyCalculator() {
    }

    @Override
    public long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius) {
        // initialize coordinate variables
        double lat1 = from.latRadians();
        double lon1 = from.lonRadians();
        double lat2 = to.latRadians();
        double lon2 = to.lonRadians();

        final double sphereDegrees = 360;

        // Differences in latitudes and longitudes
        double deltaLambda = lon2 - lon1;
        double deltaLambdaOpposite = sphereDegrees - abs(deltaLambda);

        if(deltaLambda > deltaLambdaOpposite){
            deltaLambda = deltaLambdaOpposite;
        }
        
        // Apply Vincenty's formula
        double numeratorPart1 = cos(lat2) * sin(deltaLambda);
        double numeratorPart2 = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(deltaLambda);
        double numerator = sqrt(pow(numeratorPart1, 2) + pow(numeratorPart2, 2));

        double denominator = sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(deltaLambda);

        // Calculate the angular distance in radians
        double deltaSigma = atan2(numerator, denominator);

        // Distance in kilometers
        return Math.round(earthRadius * deltaSigma);
    }
}