package org.dripto.example.spring.snippets.benchmarking.jmh

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Threads
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.results.format.ResultFormatType
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.Throughput) // Benchmark mode, using overall throughput mode
@Warmup(iterations = 3) // Preheating times
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS) // test parameter, iterations = 10 means 10 rounds of testing
@Threads(8) // number of test threads per process
@Fork(2) // The number of forks is executed, indicating that JMH will fork out two processes for testing.
@OutputTimeUnit(TimeUnit.MILLISECONDS) // Time type of benchmark results
open class SampleBenchmarking {
    @Benchmark
    fun testSequence(): Int = sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .map { it * 2 }
            .filter { it % 3 == 0 }
            .map { it + 1 }
            .sum()

    @Benchmark
    fun testList(): Int = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .map { it * 2 }
            .filter { it % 3 == 0 }
            .map { it + 1 }
            .sum()
}
fun main(args: Array<String>) {
    val options = OptionsBuilder()
            .include(SampleBenchmarking::class.java.simpleName)
            .resultFormat(ResultFormatType.JSON)
            .result("benchmark_sequence.json")
            .output("benchmark_sequence.log")
            .build()
    Runner(options).run()
}
