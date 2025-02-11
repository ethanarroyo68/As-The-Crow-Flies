package com.tco.misc;

//refactored for reduced cognitive complexity
public class TwoOptimizer extends TourConstruction {

    public TwoOptimizer() {}

    @Override
    public Places construct(Places places, Double radius, String formula, Double response) 
    {
        Places tour = super.construct(places, radius, formula, response);
        improve(tour, radius, formula, response);
        return tour;
    }

    public void improve(Places tour, Double radius, String formula, Double response) 
    {
        long timeLimitMillis = convertResponseToMillis(response);
        DistanceCalculator calculator = CalculatorFactory.getCalculator(formula);
        optimizeWithinTimeLimit(tour, radius, timeLimitMillis, calculator);
    }

    private long convertResponseToMillis(Double response) 
    {
        return (long) (response * 1000);
    }

    private void optimizeWithinTimeLimit(Places tour, Double radius, long timeLimitMillis, DistanceCalculator calculator) 
    {
        long startTime = System.currentTimeMillis();

        if (tour.size() < 3) return; //if places in tour < 3 return as optimization not possible and avoid possible indexing errors in performTourOptimization

        while (timeRemaining(startTime, timeLimitMillis) && performTourOptimization(tour, radius, calculator)) 
        {
            //logic handled externally - loop continues until no improvement or time limit reached 
        }
    }

    private boolean timeRemaining(long startTime, long timeLimitMillis) 
    {
        return System.currentTimeMillis() - startTime <= timeLimitMillis;
    }

    private boolean performTourOptimization(Places tour, Double radius, DistanceCalculator calculator) 
    {
        boolean improvement = false;
        int n = tour.size();

        for (int i = 1; i < n - 1; i++) 
        {
            for (int j = i + 1; j < n; j++) 
            {
                if (isImproved(tour, i, j, radius, calculator)) 
                {
                    reverseSegment(tour, i, j);
                    improvement = true;
                }
            }
        }
        return improvement;
    }

    private boolean isImproved(Places tour, int i, int j, Double radius, DistanceCalculator calculator) 
    {
        Place A = tour.get(i - 1);
        Place B = tour.get(i);
        Place C = tour.get(j);
        Place D = tour.get((j + 1) % tour.size());

        return newDistanceIsShorter(A, B, C, D, radius, calculator);
    }

    private boolean newDistanceIsShorter(Place A, Place B, Place C, Place D, Double radius, DistanceCalculator calculator) 
    {
        long currentDistance = calculator.between(A, B, radius) + calculator.between(C, D, radius);
        long newDistance = calculator.between(A, C, radius) + calculator.between(B, D, radius);
        return newDistance < currentDistance;
    }

    private void reverseSegment(Places tour, int i, int j) 
    {
        while (i < j) swapPlaces(tour, i++, j--);
    }

    private void swapPlaces(Places tour, int i, int j) 
    {
        Place temp = tour.get(i);
        tour.set(i, tour.get(j));
        tour.set(j, temp);
    }
}