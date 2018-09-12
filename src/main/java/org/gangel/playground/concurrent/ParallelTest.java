package org.gangel.playground.concurrent;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

public class ParallelTest {

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private static <T> T worker(String msg, long sleepTime, T result) {
        if (sleepTime > 0) {
            System.out.println(String.format("Time: %d, Thread: %s, Worker start: %s", System.nanoTime(), Thread.currentThread().getName(), msg));
            sleep(1000);
            System.out.println(String.format("Time: %d, Thread: %s, Worker done: %s", System.nanoTime(), Thread.currentThread().getName(), msg));
        } else {
            System.out.println(String.format("Time: %d, Thread: %s, %s", System.nanoTime(), Thread.currentThread().getName(), msg));
        }
        return result;
    }

    @Test
    public void completableFutureTest1() throws ExecutionException, InterruptedException {

        worker("Starting test", 0, null);

        String output = CompletableFuture.supplyAsync(() -> {
            return worker("cf1", 1000, "Welcome");
        })
        .thenApplyAsync((s) -> {
            return worker("cf2", 1000, s + " to");
        })
        .thenApplyAsync((s) -> {
            return worker("cf3", 1000, s + " the jungle.");
        }).get();

        worker("Output value: " + output, 0, null);

    }


    @Test
    public void completableFutureTest2() throws ExecutionException, InterruptedException {

        worker("Starting test", 0, null);

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            return worker("cf1", 4000, "Welcome");
        }).thenApply((s) -> {
            worker("cf1.thenApply", 0, null);
            return s;
        });
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
                return worker("cf2", 2000, "to");
        });
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
                return worker("cf3", 500, "the jungle");
        });

        BiFunction<String, String, String> combiner = (x, y) -> { return String.join(" ", x, y); };

        String output = cf1.thenCombine(cf2, combiner)
                            .thenCombine(cf3, combiner)
                            .get();

        worker("Output value: " + output, 0, null);

    }


    /**
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void completableFutureGetTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            worker("Starting cf1", 0, null);
            return "Welcome to the jungle";
        });
        String value1 = cf1.get();
        String value2 = cf1.get();
        String value3 = cf1.get();
        // checking if we get same instances
        assertThat(value1).isSameAs(value2);
        assertThat(value2).isSameAs(value3);
    }

    @Test
    public void thenApplyMultipleTimesTest() {

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            worker("Starting cf1", 1000, null);
            return "Welcome to the jungle";
        });

        cf1.thenApply((s) -> worker("Apply 1", 0, null));
        cf1.thenApply((s) -> worker("Apply 2", 0, null));
        cf1.thenApply((s) -> worker("Apply 3", 0, null));

    }
}
