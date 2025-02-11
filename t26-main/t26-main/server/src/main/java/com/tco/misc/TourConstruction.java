package com.tco.misc;

public abstract class TourConstruction {
    public void improve(){ }

    public Places construct(Places places, Double radius, String formula, Double response) {
        Places tour = new Places();

        if (places.isEmpty()) {
            return tour;
        }

        Place currentPlace = places.get(0);
        tour.add(currentPlace);
        places.remove(currentPlace);

        DistanceCalculator calculator = CalculatorFactory.getCalculator(formula);

        while (!places.isEmpty()) {
            Place nearestPlace = findNearestNeighbor(currentPlace, places, calculator, radius);
            long minDistance = calculator.between(currentPlace, nearestPlace, radius);

            // Check for equidistant neighbors
            nearestPlace = findEquidistantNeighbor(currentPlace, nearestPlace, places, calculator, radius, minDistance);

            // Add nearest place to the tour and remove from the unvisited places
            if (nearestPlace != null) {
                tour.add(nearestPlace);
                places.remove(nearestPlace);
                currentPlace = nearestPlace;
            }
        }

        return tour;
    }

    // Find equidistant neighbors for the current place and determine next best
    // place
    private Place findEquidistantNeighbor(Place currentPlace, Place nearestPlace, Places places,
            DistanceCalculator calculator, Double radius, long minDistance) {
        for (Place place : places) {
            if (isEquidistant(currentPlace, nearestPlace, place, calculator, radius, minDistance)) {
                nearestPlace = resolveNextPlace(currentPlace, nearestPlace, place, places, calculator, radius);
            }
        }
        return nearestPlace;
    }

    // refactored equidistant check
    private boolean isEquidistant(Place currentPlace, Place nearestPlace, Place place, DistanceCalculator calculator,
            Double radius, long minDistance) {
        return !place.equals(nearestPlace) && calculator.between(currentPlace, place, radius) == minDistance;
    }

    // renamed and isolated logic for deciding between equidistant places
    private Place resolveNextPlace(Place currentPlace, Place nearestPlace, Place candidatePlace, Places places,
            DistanceCalculator calculator, Double radius) {
        return decideNextPlace(nearestPlace, candidatePlace, places, calculator, radius);
    }

    // simplified decision logic for equidistant places
    private Place decideNextPlace(Place nearestPlace, Place candidatePlace, Places places,
            DistanceCalculator calculator, Double radius) {
        Place nextForPlace = findNextNeighbor(candidatePlace, places, calculator, radius);
        Place nextForNearest = findNextNeighbor(nearestPlace, places, calculator, radius);

        // if either has no next neighbor, return the nearestPlace as a fallback
        if (nextForPlace == null || nextForNearest == null) {
            return nearestPlace;
        }

        // compare next distances and choose the best
        return compareNextDistances(candidatePlace, nearestPlace, nextForPlace, nextForNearest, calculator, radius);
    }

    // find the nearest neighbor for the given place
    private Place findNextNeighbor(Place place, Places places, DistanceCalculator calculator, Double radius) {
        return findNearestNeighbor(place, places, calculator, radius);
    }

    // compare the distances of the next neighbors and return the closest
    private Place compareNextDistances(Place candidatePlace, Place nearestPlace, Place nextForPlace,
            Place nextForNearest, DistanceCalculator calculator, Double radius) {
        long nextDistanceForPlace = calculator.between(candidatePlace, nextForPlace, radius);
        long nextDistanceForNearest = calculator.between(nearestPlace, nextForNearest, radius);

        return nextDistanceForPlace < nextDistanceForNearest ? candidatePlace : nearestPlace;
    }

    protected Place findNearestNeighbor(Place fromPlace, Places places, DistanceCalculator calculator, Double radius) {
        Place nearestNeighbor = null;
        long minDistance = Long.MAX_VALUE;

        for (Place place : places) {
            if (!place.equals(fromPlace)) {
                long distance = calculator.between(fromPlace, place, radius);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestNeighbor = place;
                }
            }
        }

        return nearestNeighbor;
    }

}
