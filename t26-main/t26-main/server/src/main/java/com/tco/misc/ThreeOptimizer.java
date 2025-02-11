package com.tco.misc;

//centralized time checks with method
public class ThreeOptimizer extends TourConstruction {

    private Places tour;
    private DistanceCalculator calculator;
    private Double radius;
    private Double response;
    private long startTime;

    public ThreeOptimizer() {}

    @Override
    public Places construct(Places places, Double radius, String formula, Double response) 
    {
        this.tour = new Places();
        this.radius = radius;
        this.response = response;
        this.calculator = CalculatorFactory.getCalculator(formula);

        if (places.isEmpty()) 
        {
            return this.tour;
        }

        Place currentPlace = places.get(0);
        this.tour.add(currentPlace);
        places.remove(currentPlace);

        while (!places.isEmpty()) 
        {
            Place nearestPlace = findNearestNeighbor(currentPlace, places, calculator, radius);
            if (nearestPlace != null) 
            {
                this.tour.add(nearestPlace);
                places.remove(nearestPlace);
                currentPlace = nearestPlace;
            }
        }

        this.startTime = System.currentTimeMillis();
        improve();

        return this.tour;
    }

    public void improve() 
    {
        int n = tour.size();
        long timeLimitMillis = (long) (response * 1000);

        while (attemptImprovement(n, timeLimitMillis)) {}
    }

    private boolean attemptImprovement(int n, long timeLimitMillis) 
    {
        boolean improvement = false;
        for (int i = 0; i <= n - 3; i++) 
        {
            if (isTimeExceeded(timeLimitMillis)) return false; 
            for (int j = i + 1; j < n - 2; j++) 
            {
                for (int k = j + 1; k < n; k++) 
                {
                    if (processReversal(i, j, k)) 
                    {
                        improvement = true;
                    }
                    if (isTimeExceeded(timeLimitMillis)) return false; 
                }
            }
        }

        return improvement;
    }

    private boolean processReversal(int i, int j, int k) 
    {
        int reversalIndex = threeOptReversal(i, j, k);
        boolean improved = false;

        //check which segments to reverse based on the direct index
        if (reversalIndex == 1) 
        {
            reverseSegment(i + 1, j);
            improved = true;
        } 
        else if (reversalIndex == 2) 
        {
            reverseSegment(j + 1, k);
            improved = true;
        } 
        else if (reversalIndex == 3) 
        {
            reverseSegment(i + 1, k);
            improved = true;
        }

        return improved;
    }

    private boolean isTimeExceeded(long timeLimitMillis) 
    {
        return System.currentTimeMillis() - startTime > timeLimitMillis;
    }

    private int threeOptReversal(int i, int j, int k) 
    {
        Place A = tour.get(i);
        Place B = tour.get(i + 1);
        Place C = tour.get(j);
        Place D = tour.get(j + 1);
        Place E = tour.get(k);
        Place F = tour.get((k + 1) % tour.size());

        //array to hold calculated distances
        long[] distances = new long[7];
        distances[0] = calculateDistance(A, B, C, D, E, F);
        distances[1] = calculateDistance(A, C, B, D, E, F);
        distances[2] = calculateDistance(A, B, C, E, D, F);
        distances[3] = calculateDistance(A, D, E, B, C, F);
        distances[4] = calculateDistance(A, C, D, B, E, F);
        distances[5] = calculateDistance(A, E, D, C, B, F);
        distances[6] = calculateDistance(A, D, C, B, E, F);

        long minDistance = Long.MAX_VALUE;
        int reversalIndex = 0;

        for (int index = 0; index < distances.length; index++) 
        {
            if (distances[index] < minDistance) 
            {
                minDistance = distances[index];
                reversalIndex = index + 1; //use index + 1 to differentiate reversal 
            }
        }

        return reversalIndex; 
    }

    private long calculateDistance(Place A, Place B, Place C, Place D, Place E, Place F) 
    {
        return calculator.between(A, B, radius) + calculator.between(C, D, radius) + calculator.between(E, F, radius);
    }

    // Same as 2-opt
    private void reverseSegment(int i, int j) 
    {
        while (i < j) 
        {
            Place temp = tour.get(i);
            tour.set(i, tour.get(j));
            tour.set(j, temp);
            i++;
            j--;
        }
    }

    public Places getTour() {
        return this.tour;
    }

}
