package org.gangel.playground.singleton.doublecheck;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class DoubleCheckSingletonTest {

    public static final int NUM_THREADS = 10;
    
    @Test
    public void singletonTest() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        ArrayList<Future<Object>> instances = new ArrayList<>();
        CyclicBarrier barrier = new CyclicBarrier(NUM_THREADS); 
                
        // initialize all threads
        for (int i=0; i < NUM_THREADS; ++i) {
            instances.add( executor.submit(
                 new Callable<Object>() {

                    @Override
                    public Object call() throws Exception {
                        // wait for initialization of all workers
                        System.out.println("Starting thread..." + Thread.currentThread().getId());
                        barrier.await(); 
                        // get instance of singleton
                        Object instance = SingletonObject.getInstance();
                        System.out.println("Finishing thread " + Thread.currentThread().getId());
                        return instance; 
                    }
                     
                 }
            ));
        }
        
        // wait for threads termination
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        
        // verify all instances are same
        Object instance = SingletonObject.getInstance();
        for (int i=0; i< NUM_THREADS; ++i) {
            Assert.assertEquals(instance, instances.get(i).get());
        }
        
        // everything is correct :)
    }
}
