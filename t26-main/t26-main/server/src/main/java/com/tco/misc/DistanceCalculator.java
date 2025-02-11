package com.tco.misc;
import com.tco.misc.GeographicCoordinate;
//Distance method interface
public interface DistanceCalculator
{
    //Abstract method for calculation implimentations
    long between(GeographicCoordinate to, GeographicCoordinate from, double earthRadius);
}

