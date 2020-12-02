package ch.mgysel.aoc.day01

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.cartesianProduct
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
    printAndMeasureDuration("Part Two (Sequence approach)", ::solvePart2usingSequence)
}

val numbers = InputData.readLines("day01.txt").map { it.toInt() }

fun solvePart1(): Int {
    return findCombinationWithSum(numbers, 2020, 2).product()
}

fun solvePart2(): Int {
    return findCombinationWithSum(numbers, 2020, 3).product()
}

fun solvePart2usingSequence(): Int {
    val combinations = sequence {
        numbers.forEach { a ->
            numbers.forEach { b ->
                numbers.forEach { c ->
                    yield(listOf(a, b, c))
                }
            }
        }
    }
    return combinations.first { it.sum() == 2020 }.product()
}

fun List<Int>.product() = reduce(Int::times)

fun findCombinationWithSum(numbers: List<Int>, sum: Int, size: Int): List<Int> {
    val lists = (1..size).map { numbers }
    return cartesianProduct(lists)
            .first { it.sum() == sum }
}
