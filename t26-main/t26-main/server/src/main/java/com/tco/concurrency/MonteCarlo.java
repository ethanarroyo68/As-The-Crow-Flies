package com.tco.misc;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

public class MonteCarlo {

//stable base logic for coin class as seen on slide 52 of sprint 3 pdf - adjust implimentation after further concurency development

    private final static long flips = 3500000000L;
    private final static long cores = Runtime.getRuntime().availableProcessors();

    public static void main(String[] argv) {

        long total = 0;

        try {

            Set<Callable<Long>> threads = new HashSet<>();

            for (long i = 0; i < cores; i++)
            {
                threads.add(new Coin(flips / cores, i));
            }

            // ExecutorService executorService = Executors.newFixedThreadPool(cores); original logic from slide but newFixedThreadPool requires int
            ExecutorService executorService = Executors.newFixedThreadPool((int) cores); //cast long to int to avoid lossy compilation error
            List<Future<Long>> results = executorService.invokeAll(threads);
            executorService.shutdown();

            for (Future<Long> result : results)
            {
                total += result.get();
            }

        } catch (Exception e) {} //add loging of exception

        System.out.printf("Heads: %d/%d\n", total, flips);
    }


}