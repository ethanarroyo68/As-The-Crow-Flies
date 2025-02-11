package com.tco.misc;

import com.tco.misc.GeographicCoordinate;

public class HaversineCalculator implements DistanceCalculator {

    public HaversineCalculator() {
    }

    @Override
    public long between(GeographicCoordinate from, GeographicCoordinate to, double earthRadius) {
        // Convert coordinates to radians
        double lat1 = from.latRadians();
        double lat2 = to.latRadians();
        double lon1 = from.lonRadians();
        double lon2 = to.lonRadians();

        // Calculate differences
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        // Compute Haversine components using sin(x / 2) for numerical stability
        double sinDeltaLat = Math.sin(deltaLat / 2);
        double sinDeltaLon = Math.sin(deltaLon / 2);

        // Haversine formula
        double a = sinDeltaLat * sinDeltaLat +
                Math.cos(lat1) * Math.cos(lat2) * sinDeltaLon * sinDeltaLon;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Final distance
        double distance = earthRadius * c;

        // Round to nearest long
        return Math.round(distance);
    }
}