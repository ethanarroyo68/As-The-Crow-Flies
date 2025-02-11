package com.tco.misc;

public class NoOptimizer extends TourConstruction {

    public NoOptimizer() { }

    @Override
    public void improve() 
    {
        //do nothing
    }

    @Override
    public Places construct(Places places, Double radius, String formula, Double response) {
        return places;
    }
}
