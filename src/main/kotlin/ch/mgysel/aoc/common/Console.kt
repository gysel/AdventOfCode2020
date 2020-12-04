package ch.mgysel.aoc.common

import kotlin.system.measureNanoTime

fun printAndMeasureDuration(title: String, function: () -> Any) {
    val nanos = measureNanoTime {
        print("$title: ${function()}")
    }
    println(" (took ${nanos}ns / ${nanos / 1000000}ms)")
}
