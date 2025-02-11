package com.tco.misc;

//refactored for low cognitive complexity
public class OneOptimizer extends TourConstruction {

    public OneOptimizer() { }

    @Override
    public void improve() 
    {
        
    }

    // //find the nearest neighbor from a given place
    // private Place findNearestNeighbor(Place fromPlace, Places places, DistanceCalculator calculator, Double radius) 
    // {
    //     Place nearestNeighbor = null;
    //     long minDistance = Long.MAX_VALUE;

    //     for (Place place : places) 
    //     {
    //         if (!place.equals(fromPlace)) 
    //         {
    //             long distance = calculator.between(fromPlace, place, radius);
    //             if (distance < minDistance) 
    //             {
    //                 minDistance = distance;
    //                 nearestNeighbor = place;
    //             }
    //         }
    //     }

    //     return nearestNeighbor;
    // }
}
