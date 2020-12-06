package ch.mgysel.aoc.day06

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.read("day06.txt")

fun solvePart1(): Any = data.splitToSequence("\n\n")
    .map { answersInGroup ->
        answersInGroup.asSequence()
            .filter { char -> char != '\n' }
            .distinct()
            .count()
    }
    .sum()

fun solvePart2(): Any {
    return data.splitToSequence("\n\n")
        .map { it.split("\n") }
        .map { it.map(String::toSet) }
        .map { answers -> answers.reduce(Set<Char>::intersect).count() }
        .sum()
}