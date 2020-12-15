package ch.mgysel.aoc.day15

import ch.mgysel.aoc.common.printAndMeasureDuration
import kotlin.math.roundToInt

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two") { solvePart2(true) }
}

val input = listOf(7, 12, 1, 0, 16, 2)

fun solvePart1(): Any {
    return runGame(input) // not 2
}

fun solvePart2(printProgress: Boolean = false): Any = runGame(input, 30_000_000, printProgress)

fun runGame(input: List<Int>, turns: Int = 2020, printProgress: Boolean = false): Int {
    val spokenNumbers: MutableMap<Int, SpokenNumberMetadata> = mutableMapOf()
    input.forEachIndexed { index, number ->
        spokenNumbers[number] = SpokenNumberMetadata().also { it.addTurn(index + 1) }
    }
    var turn = input.size
    var currentNumber = 0
    var lastNumber = input.last()
    while (turn < turns) {
        turn++
        val metadata = spokenNumbers[lastNumber]
            ?: throw IllegalStateException("Metadata for $lastNumber missing!")
        currentNumber = if (metadata.isFirstTime()) {
            0
        } else {
            val (a, b) = metadata.getLastTwoTurns()
            b - a
        }
        spokenNumbers.getOrPut(currentNumber) { SpokenNumberMetadata() }.addTurn(turn)
        lastNumber = currentNumber
        if (printProgress && turn % 1_000_000 == 0) {
            val percent = calculatePercent(turn, turns)
            println("Running turn $turn/$turns ($percent%)")
        }
    }
    return currentNumber
}

private fun calculatePercent(turn: Int, turns: Int) = (100.toDouble() * turn / turns).roundToInt()

class SpokenNumberMetadata {
    private val spokenInTurns = mutableListOf<Int>()

    fun addTurn(turn: Int) {
        spokenInTurns += turn
        if (spokenInTurns.size > 2) spokenInTurns.removeFirst()
    }

    fun isFirstTime() = spokenInTurns.size == 1

    fun getLastTwoTurns() = spokenInTurns.takeLast(2)
}