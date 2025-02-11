package com.tco.misc;
import java.util.concurrent.Callable;
import java.util.Random;

//stable base logic for coin class as seen on slide 51 of sprint 3 pdf - adjust implimentation after further concurency development

public class Coin implements Callable<Long> 
{

    private final static int HEADS = 1;
    private long flips, thread; 
    private Random random = new Random(); // One instance

    //constructor to initialize the number of flips and thread ID   
    public Coin(long f, long t) 
    {
        flips = f;
        thread = t;
        //random = new Random(); // instance per thread
    }

    @Override
    public Long call() 
    {
        // Random random = new Random(); // instance per thread
        // ThreadLocalRandom random = ThreadLocalRandom.current(); // instance per thread
        long heads = 0;
        for (long i = 0; i < flips; i++) 
        {
            if (random.nextInt(2) == HEADS) heads++;
            // heads += (int) (java.util.Math.random() * 2); // instance per call
        }
        return heads;
    }

}