package ch.mgysel.aoc.day23

import ch.mgysel.aoc.common.printAndMeasureDuration
import java.util.*

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

fun solvePart1(): Any {
    return playCrabCups("137826495".toCups(), 100).labelsAfterOne()
}

fun solvePart2(): Any {
    val cups = "137826495".toCups() + (10..1_000_000).toList()
    val result = playCrabCups(cups, 10_000_000)
    val indexOf1 = result.indexOf(1)
    return result[indexOf1 + 1].toLong() * result[indexOf1 + 2].toLong()
}

fun playCrabCups(startingCups: List<Int>, moves: Int, printMoves: Boolean = false): List<Int> {
    val highestCup = startingCups.maxOrNull()!!
    var currentCup = Cup(startingCups.first(), null)
    currentCup.next = startingCups.foldRight(currentCup) { number, next ->
        Cup(number, next)
    }.next
    val cupIndex: Map<Int, Cup> = currentCup.nexts().map { it.number to it }.toMap()

    (1..moves).forEach { move ->
        if (printMoves) {
            println("-- move $move --")
            println("cups: ${currentCup.nexts().joinToString { c -> if (c == currentCup) "($c)" else " $c " }}")
        }
        val pickUp = currentCup.pickUp()
        if (printMoves) println("pick up: ${pickUp.joinToString(", ")}")

        val destination = currentCup.findDestination(pickUp.map { it.number }, highestCup, cupIndex)
        if (printMoves) println("destination: $destination\n")
        destination.insertAfter(pickUp)
        currentCup = currentCup.next
    }
    return currentCup.nexts().map(Cup::number).toList()
}

class Cup(
    val number: Int,
    next: Cup?
) {
    lateinit var next: Cup

    init {
        if (next != null) {
            this.next = next
        }
    }

    fun pickUp(): List<Cup> = listOf(next, next.next, next.next.next).also {
        next = it.last().next
    }

    fun nexts(): Sequence<Cup> = sequence {
        val first = this@Cup
        var current = first
        do {
            yield(current)
            current = current.next
        } while (current != first)
    }

    fun insertAfter(cups: List<Cup>) {
        val temp = next
        next = cups.first()
        cups.last().next = temp
    }

    fun findDestination(cupsToIgnore: List<Int>, highestCup: Int, cupIndex: Map<Int, Cup>): Cup {
        val destination = sequence {
            var n = number
            while (true) {
                n--
                if (n == 0) n = highestCup
                yield(n)
            }
        }.dropWhile { it in cupsToIgnore }.first()
        return cupIndex[destination]!!
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Cup
        if (number != other.number) return false
        return true
    }

    override fun hashCode(): Int {
        return number
    }

    override fun toString(): String {
        return number.toString()
    }

}

fun String.toCups() = this.chunked(1).map { it.toInt() }

fun List<Int>.labelsAfterOne(): String {
    val index = indexOf(1)
    return (subList(index + 1, size) + subList(0, index)).joinToString("")
}
