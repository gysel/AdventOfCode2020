package ch.mgysel.aoc.day22

import ch.mgysel.aoc.common.printAndMeasureDuration
import java.util.*

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val player1 = listOf(18, 19, 16, 11, 47, 38, 6, 27, 9, 22, 15, 42, 3, 4, 21, 41, 14, 8, 23, 30, 40, 13, 35, 46, 50)
val player2 = listOf(39, 1, 29, 20, 45, 43, 12, 2, 37, 33, 49, 32, 10, 26, 36, 17, 34, 44, 25, 28, 24, 5, 48, 31, 7)

fun solvePart1(): Any {
    return playCombat(player1, player2)
}

fun solvePart2(): Any {
    return playRecursiveCombat(player1, player2).calculateScore()
}

fun playCombat(deck1: List<Int>, deck2: List<Int>): Long {
    val stack1 = LinkedList(deck1)
    val stack2 = LinkedList(deck2)
    while (stack1.isNotEmpty() and stack2.isNotEmpty()) {
        val card1 = stack1.poll()
        val card2 = stack2.poll()
        if (card1 > card2) {
            stack1.addLast(card1)
            stack1.addLast(card2)
        } else {
            stack2.addLast(card2)
            stack2.addLast(card1)
        }
    }
    val winner = if (stack1.isNotEmpty()) "1" else "2"
    println("Player $winner won!")
    val winningStack = if (stack1.isNotEmpty()) stack1 else stack2
    return winningStack.reversed()
        .mapIndexed { index, card -> card.toLong() * (index + 1) }
        .sum()
}

fun playRecursiveCombat(deck1: List<Int>, deck2: List<Int>): GameResult {
    val stack1 = LinkedList(deck1)
    val stack2 = LinkedList(deck2)

    val previousDecks1 = mutableSetOf<List<Int>>()
    val previousDecks2 = mutableSetOf<List<Int>>()

    var winner: String? = null
    while (winner == null && stack1.isNotEmpty() && stack2.isNotEmpty()) {
        val card1 = stack1.poll()
        val card2 = stack2.poll()
        if (stack1.size >= card1 && stack2.size >= card2) {
            // play Recursive Combat
            val subgame = playRecursiveCombat(stack1.take(card1), stack2.take(card2))
            if (subgame.winner == "1") {
                stack1.addLast(card1)
                stack1.addLast(card2)
            } else {
                stack2.addLast(card2)
                stack2.addLast(card1)
            }
        } else {
            // play normal combat
            if (card1 > card2) {
                stack1.addLast(card1)
                stack1.addLast(card2)
            } else {
                stack2.addLast(card2)
                stack2.addLast(card1)
            }
        }
        if (previousDecks1.contains(stack1) || previousDecks2.contains(stack2)) {
            winner = "1"
        } else {
            previousDecks1.add(stack1.toList())
            previousDecks2.add(stack2.toList())
        }
    }
    if (winner == null) {
        winner = if (stack1.isNotEmpty()) "1" else "2"
    }
    val winningStack = if (stack1.isNotEmpty()) stack1 else stack2
    return GameResult(winner, winningStack)
}

data class GameResult(val winner: String, val stack: List<Int>) {
    fun calculateScore() = stack
        .reversed()
        .mapIndexed { index, card -> card.toLong() * (index + 1) }
        .sum()
}
