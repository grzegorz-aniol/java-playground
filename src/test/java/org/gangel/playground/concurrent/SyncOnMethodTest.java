package org.gangel.playground.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Test synchronization on static and non-static methods 
 * 
 * @author Grzegorz_Aniol
 *
 */
public class SyncOnMethodTest {

    public static class Data {
        
        private volatile int counter = 0; 
        
        public synchronized void count() {
            ++counter;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        
        public int getCounter() {
            return this.counter;
        }
    }
        
   public static class StaticData {
       
        private static volatile int staticCounter = 0;
        
        public static synchronized void staticCount() {
            ++staticCounter; 
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        
        public static int getStaticCount() {
            return staticCounter; 
        }
        
    }
   
    private final int NUM_THREADS = 5;
    
    
    @Test
    public void testSyncMethod() {
        
        final Data[] objects = new Data[NUM_THREADS];
        
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_THREADS; ++i) {
            Data obj = objects[i] = new Data();
            executor.execute(() -> { obj.count(); });            
        }
        executor.shutdown();
        try {
            executor.awaitTermination(NUM_THREADS + 2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        long time = System.currentTimeMillis() - startTime; 
        
        for (int i=0; i < NUM_THREADS; ++i) {
            assert(objects[i].getCounter() == 1);            
        }
        
        assert(time >= 1000 && time < 2000);
        
    }
    
    @Test
    public void testSyncStaticMethod() {
        
        final StaticData[] objects = new StaticData[NUM_THREADS];
        
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_THREADS; ++i) {
            StaticData obj = objects[i] = new StaticData();
            executor.execute(() -> { obj.staticCount(); });            
        }
        executor.shutdown();
        try {
            executor.awaitTermination(NUM_THREADS + 2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        long time = System.currentTimeMillis() - startTime; 
        
        assert(StaticData.getStaticCount() == NUM_THREADS);
        assert(time >= 1000 * NUM_THREADS && time < 1000 * (NUM_THREADS + 1));        
    }    
    
}
