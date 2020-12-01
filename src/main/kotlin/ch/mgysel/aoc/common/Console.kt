package ch.mgysel.aoc.common

import kotlin.system.measureTimeMillis

fun printAndMeasureDuration(title: String, function: () -> Any) {
    val millis = measureTimeMillis {
        print("$title: ${function()}")
    }
    println(" (took ${millis}ms)")
}
