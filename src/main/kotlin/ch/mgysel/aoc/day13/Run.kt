package ch.mgysel.aoc.day13

import ch.mgysel.aoc.common.printAndMeasureDuration
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

fun solvePart1(): Any {
    var time = 1000067L
    val buses =
        "17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,439,x,29,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,787,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19"
            .split(",").filter { it != "x" }.map { it.toLong() }

    while (true) {
        val bus = buses.firstOrNull { bus -> time % bus == 0L }
        if (bus != null) {
            return bus * (time - 1000067L)
        }
        time++
    }
}

fun solvePart2(): Any {
    return "17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,439,x,29,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,787,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19"
        .parseBusLines()
        .let(::calculatePart2)
}

/**
 * With a bit of inspiration from https://todd.ginsberg.com/post/advent-of-code/2020/day13/
 */
fun calculatePart2(buses: List<Pair<Int, Long>>): Long {
    var timestamp = 1L
    var step = 1L
    buses.forEach { (offset, interval) ->
        while ((timestamp + offset) % interval != 0L) {
            timestamp += step
        }
        step *= interval
    }
    return timestamp
}

fun String.parseBusLines() = this.split(",")
    .mapIndexedNotNull { i, bus ->
        if (bus != "x")
            i to bus.toLong()
        else
            null
    }