package com.tco.misc;

import java.util.List;
import java.util.ArrayList;

public class Distances extends ArrayList<Long>  {

    // for testing purposes

    public long total() {
        long sum = 0;
        for(long distance : this){
            sum += distance;
        }
        return sum;
    }

}
