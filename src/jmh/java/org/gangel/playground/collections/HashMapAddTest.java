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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Fork(value = 3, warmups = 2)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class HashMapAddTest {

    @State(Scope.Benchmark)
    public static class ValueData {
        public static String[] dataSet100 = IntStream.range(0, 100).mapToObj(i -> UUID.randomUUID().toString()).toArray(String[]::new);
        public static String[] dataSet1k = IntStream.range(0, 1_000).mapToObj(i -> UUID.randomUUID().toString()).toArray(String[]::new);
        public static String[] dataSet10k = IntStream.range(0, 10_000).mapToObj(i -> UUID.randomUUID().toString()).toArray(String[]::new);
        public static String[] dataSet100k = IntStream.range(0, 100_000).mapToObj(i -> UUID.randomUUID().toString()).toArray(String[]::new);
        public static int SIZE = 1000;
        public static String[] randomDataSet = IntStream.range(0, SIZE).mapToObj(i -> UUID.randomUUID().toString()).toArray(String[]::new);
    }

    @State(Scope.Thread)
    public static class ThreadData {
        public Map<String, String> map100 = Arrays.stream(ValueData.dataSet100).collect(Collectors.toMap(Function.identity(), Function.identity()));
        public Map<String, String> map1k = Arrays.stream(ValueData.dataSet1k).collect(Collectors.toMap(Function.identity(), Function.identity()));
        public Map<String, String> map10k = Arrays.stream(ValueData.dataSet10k).collect(Collectors.toMap(Function.identity(), Function.identity()));
        public Map<String, String> map100k = Arrays.stream(ValueData.dataSet100k).collect(Collectors.toMap(Function.identity(), Function.identity()));
        public int datasetIndex = 0;
        public String getElement() {
            var value = ValueData.randomDataSet[datasetIndex];
            datasetIndex = (datasetIndex + 1) % ValueData.SIZE;
            return value;
        }
    }

    @Benchmark
    public void addElementToMapOf100(ThreadData td) {
        String element = td.getElement();
        td.map100.put(element, element);
    }

    @Benchmark
    public void addElementToMapOf1k(ThreadData td) {
        String element = td.getElement();
        td.map1k.put(element, element);
    }

    @Benchmark
    public void addElementToMapOf10k(ThreadData td) {
        String element = td.getElement();
        td.map10k.put(element, element);
    }

    @Benchmark
    public void addElementToMapOf100k(ThreadData td) {
        String element = td.getElement();
        td.map100k.put(element, element);
    }

}
