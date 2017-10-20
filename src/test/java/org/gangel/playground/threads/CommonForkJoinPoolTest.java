package org.gangel.playground.threads;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class CommonForkJoinPoolTest {

    private static class Job implements Runnable {

        private int maxCount;

        public Job(int count) {
            this.maxCount = count;
        }

        @Override
        public void run() {
            Random rnd = new Random();

            long primesCount = 0;
            long numbersCount = 0;

            // find prime number for timeLimitInSec number of seconds
            while (numbersCount < maxCount) {

                long n = rnd.nextLong();
                long max = (long) Math.sqrt(n);

                boolean isPrime = true;
                for (long divider = 2; divider < max; ++divider) {
                    if (n % divider == 0) {
                        isPrime = false;
                        break;
                    }
                }

                ++numbersCount;
                if (isPrime) {
                    ++primesCount;
                }
            }
            
        }

    }

    @Test
    public void testParallelismLevel() {

        System.out.println("Processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Common pool parallelism: " + ForkJoinPool.commonPool().getParallelism());
        System.out.println("Start...");
        
        final int level = 2 * Runtime.getRuntime().availableProcessors();
        final int numberCount = 100; // seconds
        long startTime = System.currentTimeMillis();
        for (int i=0; i < level; ++i) {
            ForkJoinPool.commonPool().submit(new Job(numberCount));
        }
        
        System.out.println("Waiting...");
        ForkJoinPool.commonPool().awaitQuiescence(numberCount * level, TimeUnit.SECONDS);
        System.out.println("Done.");
        
        long duration = System.currentTimeMillis() - startTime;
        System.out.println(String.format("Job time = %.3f seconds", 1e-3 * duration));

    }
    
    @Test
    public void testSequentialJobs() {

        System.out.println("Start sequential jobs...");
        
        final int level = 2 * Runtime.getRuntime().availableProcessors();
        final int numberCount = 100; // seconds
        long startTime = System.currentTimeMillis();
        
        System.out.println("Waiting...");
        for (int i=0; i < level; ++i) {
            new Job(numberCount).run();
        }        
        long duration = System.currentTimeMillis() - startTime;

        System.out.println("Done.");        
        System.out.println(String.format("Job time = %.3f seconds", 1e-3 * duration));

    }    
        
    
}
