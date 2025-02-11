package com.tco.misc;
import java.util.LinkedHashMap;
import java.lang.Math;
import com.tco.misc.GeographicCoordinate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Place extends LinkedHashMap<String,String> implements GeographicCoordinate {

    private static final transient Logger log = LoggerFactory.getLogger(Place.class);

    // General method to get coordinates as radians
    public Double getCoordinateInRadians(String key) {
        String coord = this.get(key); // Retrieve the coordinate from the HashMap
        return convertRad(coord); // Convert to radians
}

    // Convert latitude to radians
    public Double latRadians() {
        return getCoordinateInRadians("latitude");
    }

    // Convert longitude to radians
    public Double lonRadians() {
        return getCoordinateInRadians("longitude");
    }
    
    // Convert to radians if not null
    public Double convertRad(String coord) {
        if (coord != null) {
            try {
                return Math.toRadians(Double.parseDouble(coord)); // Convert to radians
            } catch (NumberFormatException e) {
                log.warn("Invalid coordinate format: {}", coord); // Log invalid coord
                return null;
            }
        }
        return null; // Return null if longitude is not set
    }

// Routines for testing purposes

    // Accepts latitude and longitude as strings
    public Place(String lat, String lon) {
        this.put("latitude", lat); // Store latitude in the HashMap
        this.put("longitude", lon); // Store longitude in the HashMap
    }

    // Default Constructor
    public Place() {}

    // Method to return a unique identifier for the place
    public String id() {
        return this.get("id"); 
    }

}
