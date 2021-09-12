package org.gangel.playground.collections;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Fork(value = 3, warmups = 2)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class HashMapAddInRowTest {

    @State(Scope.Benchmark)
    public static class ValueData {
        public static int SIZE = 1000;
        public static String[] randomDataSet = IntStream.range(0, SIZE).mapToObj(i -> UUID.randomUUID().toString()).toArray(String[]::new);
    }

    @State(Scope.Thread)
    public static class ThreadData {
        public HashMap<String, String> underTest = new HashMap<>();
        public int datasetIndex = 0;

        public String getElement() {
            var value = ValueData.randomDataSet[datasetIndex];
            datasetIndex = (datasetIndex + 1) % ValueData.SIZE;
            return value;
        }
    }

    @Benchmark
    public void add100ElementToHashMap(ThreadData td) {
        String element = td.getElement();
        for (int i = 0; i < 100; ++i) {
            td.underTest.put(element, element);
        }
    }

    @Benchmark
    public void add1KElementToHashMap(ThreadData td) {
        String element = td.getElement();
        for (int i = 0; i < 1_000; ++i) {
            td.underTest.put(element, element);
        }
    }

    @Benchmark
    public void add10KElementToHashMap(ThreadData td) {
        String element = td.getElement();
        for (int i = 0; i < 10_000; ++i) {
            td.underTest.put(element, element);
        }
    }

    @Benchmark
    public void add100KElementToHashMap(ThreadData td) {
        String element = td.getElement();
        for (int i = 0; i < 100_000; ++i) {
            td.underTest.put(element, element);
        }
    }

}
