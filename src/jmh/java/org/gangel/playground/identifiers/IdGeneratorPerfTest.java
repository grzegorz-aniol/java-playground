package org.gangel.playground.identifiers;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Warmup(iterations=2, time=1, timeUnit=TimeUnit.SECONDS)
@Measurement(iterations=2, time=1, timeUnit=TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class IdGeneratorPerfTest {

    @State(Scope.Benchmark)
    public static class RandomState {
        public static Random rnd = new Random();
        public static SecureRandom secRnd = new SecureRandom();
    }
    
    @Benchmark
    public void testFullUUIDKey(Blackhole bh) {
        String key = IdGenerator.getNewId();
        bh.consume(key);
    }
    
    @Benchmark
    public void testShortedUUIDKey(Blackhole bh) {
        String key = IdGenerator.getNewId(12);
        bh.consume(key);
    }
    
    @Benchmark
    public void testRandomId(Blackhole bh) {
        bh.consume(RandomState.rnd.nextLong());
    }

    @Benchmark
    public void testSecureRandomId(Blackhole bh) {
        bh.consume(RandomState.secRnd.nextLong());
    }

    @Benchmark
    public void testShortId(Blackhole bh) {
        String key = IdGenerator.getShortId();
        bh.consume(key);
    }
    
//    public static void main(String[] args) throws RunnerException {
//        // 
//        for(String s : args) {
//            System.out.println(" > " + s);
//        }
//        
//        Options opt = new OptionsBuilder()
//                .include(IdGeneratorPerfTest.class.getSimpleName())
//                .build();
//
//        new Runner(opt).run();
//    }      
}
