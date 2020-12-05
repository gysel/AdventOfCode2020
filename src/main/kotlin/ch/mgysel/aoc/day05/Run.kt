package ch.mgysel.aoc.day05

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration
import kotlin.math.pow

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day05.txt")

fun solvePart1(): Any = data
    .map(::parseSeat)
    .maxOf(Seat::calculateSeatId)

fun solvePart2(): Any {
    val occupiedSeats = data.map(::parseSeat).toSet()
    val allSeats = (0..127).flatMap { row ->
        (0..7).map { column -> Seat(row, column) }
    }.toSet()

    val freeSeats = (allSeats - occupiedSeats)
        .filter { it.row in 11..109 } // drop first and last rows
    return freeSeats.first().calculateSeatId()
}

data class Seat(val row: Int, val column: Int) {
    fun calculateSeatId(): Int = row * 8 + column
}

fun parseSeat(line: String): Seat {
    val row = line.subSequence(0, 6 + 1).calculateBinaryPartition('B')
    val column = line.subSequence(7, 9 + 1).calculateBinaryPartition('R')
    return Seat(row, column)
}

private fun CharSequence.calculateBinaryPartition(higherChar: Char): Int {
    return reversed()
        .mapIndexed { index, char ->
            if (char == higherChar) 2.0.pow(index).toInt() else 0
        }.sum()
}
