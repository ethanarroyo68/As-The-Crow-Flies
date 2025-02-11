package com.tco.misc;

import com.tco.database.*;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeographicLocations {
    private static final Logger logger = LoggerFactory.getLogger(GeographicLocations.class);
    Place place;
    Integer distance;
    Double earthRadius;
    String formula;
    Integer limit;
    Places filteredPlaces;
    private int totalFound = 0;

    private class BoundingBox {
        double latMin, latMax, lonMin, lonMax;
        BoundingBox(double latMin, double latMax, double lonMin, double lonMax) {
            this.latMin = latMin;
            this.latMax = latMax;
            this.lonMin = lonMin;
            this.lonMax = lonMax;
        }
    }

    public Places find(String match, List<String> type, List<String> where, Integer limit) {
        Places places = new Places();
        try {
            String sql = Select.match(match, type, where, limit);
            Places rawPlaces = Database.places(sql);
            places.addAll(rawPlaces);
            return places;
        } catch (Exception e) {
            logger.error("Error in find query with match: {} and where: {}: {}", match, where, e.getMessage(), e);
            return places;
        }
    }

    public Integer found(String match, List<String> type, List<String> where) {
        try {
            String sql = Select.found(match, type, where);
            return this.totalFound = Database.found(sql);
        } catch (Exception e) {
            logger.error("Error in count query with match: {} and where: {}: {}", match, where, e.getMessage(), e);
            return this.totalFound;
        }
    }

    public Places near(Place place, Integer distance, Double earthRadius, String formula) {
        this.place = place;
        this.distance = distance;
        this.earthRadius = earthRadius;
        this.formula = (formula != null) ? formula : "Vincenty";
     
        try {
            double lat = Double.parseDouble(place.get("latitude"));
            double lon = normalizeLongitude(Double.parseDouble(place.get("longitude")));
            BoundingBox box = calculateBoundingBox(lat, lon, distance);
            
            Places uniquePlaces = performPlaceSearch(box, lat, lon);
            List<PlaceDistance> placeDistances = calculateDistances(uniquePlaces);
            placeDistances.sort(Comparator.comparingDouble(PlaceDistance::getDistance));
            
            return this.filteredPlaces = convertToPlaces(placeDistances);
     
        } catch (Exception e) {
            logger.error("Error in near query: {}", e.getMessage(), e);
            return this.filteredPlaces = new Places();
        }
     }
     
     public Places limitPlaces(Places places, int limit) {
        Places limitedPlaces = new Places();
        for (int i = 0; i < Math.min(limit, places.size()); i++) {
            limitedPlaces.add(places.get(i));
        }
        return limitedPlaces;
     }
     
     private Places convertToPlaces(List<PlaceDistance> placeDistances) {
        Places places = new Places();
        for (PlaceDistance pd : placeDistances) {
            places.add(pd.getPlace());
        }
        return places;
     }

    private BoundingBox calculateBoundingBox(double lat, double lon, int distance) {
        double latDelta = distance / 69.0;
        double latMin = Math.max(lat - latDelta, -90);
        double latMax = Math.min(lat + latDelta, 90);
        double boundingLat = Math.abs(latMin) < Math.abs(latMax) ? latMin : latMax;
        double lonDelta = distance / (69.172 * Math.cos(Math.toRadians(boundingLat)));
        
        if (isNearDateLine(lon) || isNearPole(lat) || 
            searchRadiusCrossesPole(place, distance, earthRadius, formula, lat) || 
            searchDiameterExceedsCircum(boundingLat, earthRadius, distance)) {
            return new BoundingBox(latMin, latMax, -180, 180);
        }
        
        return new BoundingBox(latMin, latMax, 
                              normalizeLongitude(lon - lonDelta), 
                              normalizeLongitude(lon + lonDelta));
    }

    private Places performPlaceSearch(BoundingBox box, double lat, double lon) throws Exception {
        if (box.lonMin == -180 && box.lonMax == 180) {
            String sql = Select.searchNearby(box.latMin, box.latMax, box.lonMin, box.lonMax, distance);
            return Database.places(sql);
        }
        
        if (isNearDateLine(lon)) {
            Places uniquePlaces = new Places();
            handleDatelineSearch(box.latMin, box.latMax, 
                               Math.abs(box.lonMax - box.lonMin) / 2, lon, uniquePlaces);
            return uniquePlaces;
        }
        
        String sql = Select.searchNearby(box.latMin, box.latMax, box.lonMin, box.lonMax, distance);
        return Database.places(sql);
    }

    private double normalizeLongitude(double lon) {
        lon = ((lon + 180) % 360 + 360) % 360 - 180;
        return lon;
    }

    private void handleDatelineSearch(double latMin, double latMax, double lonDelta, double lon, Places uniquePlaces) throws Exception {
        String sql1 = Select.searchNearby(latMin, latMax, -180, -180 + lonDelta, distance);
        addUniquePlaces(uniquePlaces, Database.places(sql1));

        String sql2 = Select.searchNearby(latMin, latMax, 180 - lonDelta, 180, distance);
        addUniquePlaces(uniquePlaces, Database.places(sql2));

        if (lon >= 0) {
            String sql3 = Select.searchNearby(latMin, latMax, lon - lonDelta, 180, distance);
            addUniquePlaces(uniquePlaces, Database.places(sql3));
        } else {
            String sql3 = Select.searchNearby(latMin, latMax, -180, lon + lonDelta, distance);
            addUniquePlaces(uniquePlaces, Database.places(sql3));
        }
    }

    private List<PlaceDistance> calculateDistances(Places uniquePlaces) {
        DistanceCalculator calculator = CalculatorFactory.getCalculator(formula);
        List<PlaceDistance> placeDistances = new ArrayList<>();

        for (Place p : uniquePlaces) {
            double actualDistance = calculator.between(place, p, earthRadius);
            if (actualDistance <= distance) {
                placeDistances.add(new PlaceDistance(p, actualDistance));
            }
        }
        return placeDistances;
    }

    private void addUniquePlaces(Places uniquePlaces, Places newPlaces) {
        Set<String> seenIds = new HashSet<>();
        for (Place existing : uniquePlaces) {
            seenIds.add(existing.get("id"));
        }

        for (Place place : newPlaces) {
            if (!seenIds.contains(place.get("id"))) {
                uniquePlaces.add(place);
                seenIds.add(place.get("id"));
            }
        }
    }

    private boolean isNearDateLine(double longitude) {
        return Math.abs(Math.abs(longitude) - 180) < 30;
    }

    public static boolean isNearPole(double lat) {
        return Math.abs(Math.abs(lat) - 90) <= 1.0;
    }

    public static boolean searchRadiusCrossesPole(Place place, Integer distance, Double earthRadius, String formula, double lat) {
        DistanceCalculator calculator = CalculatorFactory.getCalculator(formula);
        if (lat >= 0) {
            Place northPole = new Place("90", "0");
            double distToNorthPole = calculator.between(place, northPole, earthRadius);
            return distToNorthPole < distance;
        } else {
            Place southPole = new Place("-90", "0");
            double distToSouthPole = calculator.between(place, southPole, earthRadius);
            return distToSouthPole < distance;
        }
    }

    public static boolean searchDiameterExceedsCircum(double boundingLat, double earthRadius, double distance) {
        double circumference = 2 * Math.PI * earthRadius * Math.cos(Math.toRadians(boundingLat));
        return (distance * 2) > circumference;
    }

    public Distances distances(Place place, Places places) {
        Distances distances = new Distances();
        DistanceCalculator calculator = CalculatorFactory.getCalculator(formula);
        for (Place p : places) {
            distances.add((long)calculator.between(place, p, earthRadius));
        }
        return distances;
    }

    private static class PlaceDistance {
        private final Place place;
        private final double distance;

        public PlaceDistance(Place place, double distance) {
            this.place = place;
            this.distance = distance;
        }

        public Place getPlace() {
            return place;
        }

        public double getDistance() {
            return distance;
        }
    }

    public Place getPlace() {
        return this.place;
    }

    public Places getPlaces() {
        return this.filteredPlaces;
    }
}