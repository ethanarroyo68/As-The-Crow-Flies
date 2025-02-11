package com.tco.misc;
import com.tco.misc.GeographicCoordinate;

public class CosinesCalculator implements DistanceCalculator {

    public CosinesCalculator() {
    }

    @Override
    public long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius)
    {

        // Initialize lat and lon variables needed for distance calculation
        double lat1 = from.latRadians();
        double lon1 = from.lonRadians();
        double lat2 = to.latRadians();
        double lon2 = to.lonRadians();

        // Cosine formula implementation
        double distance = earthRadius * Math.acos(
            Math.sin(lat1) * Math.sin(lat2) +
            Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)
        );

        return Math.round(distance);  // Return distance rounded to the nearest long

       }

}