package ch.mgysel.aoc.day10

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day10.txt")

fun solvePart1(): Any {
    val adapters = data.map { it.toInt() }
    return calculatePartOne(adapters)
}

fun calculatePartOne(adapters: List<Int>): Int {
    val builtIn = adapters.maxOrNull()!! + 3
    val grouped = (listOf(0) + adapters + listOf(builtIn))
        .sorted()
        .windowed(2) { (first, next) ->
            next - first
        }.groupBy { it }

    return grouped[1]!!.count() * grouped[3]!!.count()
}

fun solvePart2(): Any {
    val adapters = data.map { it.toInt() }
    return calculatePartTwo(adapters)
}

fun calculatePartTwo(adapters: List<Int>): Long {
    val builtIn = adapters.maxOrNull()!! + 3
    val jolts = (listOf(0) + adapters + listOf(builtIn))
        .sorted()

    val connections = jolts
        .windowed(size = 4, partialWindows = true) { window ->
            val value = window.first()
            window
                .drop(1)
                .filter { it in value + 1..value + 3 }
                .map { Connection(value, it) }
        }
        .flatten()

    // key: jolt
    // value: number of paths to the device
    val transitivePaths = mutableMapOf<Int, Long>()

    jolts.reversed().forEach { jolt ->
        val countOfPathsFromJolt = connections
            .filter { it.from == jolt }
            .map { connection ->
                if (connection.to == builtIn) {
                    1
                } else {
                    transitivePaths[connection.to]
                        ?: throw IllegalStateException("Missing data for jolt ${connection.to}, calculation is incorrect.")
                }
            }
            .sum()
        transitivePaths[jolt] = countOfPathsFromJolt
    }
    return transitivePaths[0] ?: throw IllegalStateException("Missing result, calculation is incorrect.")
}

data class Connection(
    val from: Int,
    val to: Int
)
