package net.ml.schema.benchmark;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public final class BenchmarkRunner {
    public static void main(String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
            .include(GetterBenchmark.class.getName())
            .include(SetterBenchmark.class.getName())
            .include(AccessorBenchmark.class.getName())
            .include(SchemaBenchmark.class.getName())
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .build();

        new Runner(options).run();
    }
}
