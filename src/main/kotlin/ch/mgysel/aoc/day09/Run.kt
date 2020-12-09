package ch.mgysel.aoc.day09

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.cartesianProduct
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day09.txt")

fun solvePart1(): Long {
    val numbers = data.map(String::toLong)
    return numbers.windowed(26).first { window ->
        val previousNumbers = window.take(25)
        val number = window.last()
        cartesianProduct(listOf(previousNumbers, previousNumbers))
            .none { (first, second) ->
                first + second == number
            }
    }.last()
}

fun solvePart2(): Any {
    val numbers = data.map(String::toLong)
    val invalidNumber = solvePart1()
    val allContiguousSets = sequence<Set<Long>> {
        // a sequence emitting mutable sets and lists is a bit dangerous but it works in this case
        val contiguousSets = mutableListOf(mutableSetOf<Long>())
        numbers.forEach { number ->
            contiguousSets.forEach { it.add(number) }
            contiguousSets += mutableSetOf(number)
            contiguousSets.removeIf { it.sum() > invalidNumber }
            yieldAll(contiguousSets)
        }
    }

    return allContiguousSets
        .first { set -> set.sum() == invalidNumber }
        .sorted()
        .let { it.first() + it.last() }
}