package ch.mgysel.aoc.day18

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration
import java.util.*

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day18.txt")

fun solvePart1(): Any {
    return data.map { it.evaluateSamePrecedence() }.sum()
}

fun LinkedList<String>.evaluateSamePrecedence(): Long {
    var result = 0L
    var operation: ((Long, Long) -> Long)? = null

    while (this.isNotEmpty()) {
        when (val current = this.pop()) {
            "(" -> {
                val number = this.evaluateSamePrecedence()
                result = operation?.invoke(result, number) ?: number
            }
            ")" -> break // is there a nicer solution than this?
            "+" -> operation = Long::plus
            "-" -> operation = Long::minus
            "*" -> operation = Long::times
            else -> {
                val number = current.toLong()
                result = operation?.invoke(result, number)
                    ?: number
            }
        }
    }
    return result
}

fun String.evaluateSamePrecedence(): Long {
    val chars = this.chunked(1)
        .filter { it.isNotBlank() }
        .let { LinkedList(it) }
    return chars.evaluateSamePrecedence()
}

fun solvePart2(): Any {
    return data.map { it.evaluateAdditionPrecedence() }.sum()
}

/**
 * This solution is ridiculous but it calculates the correct result for the input data.
 *
 * A much better algorithm can be seen in `Day18Test.solvePart2WithNiceImplementation`
 */
fun String.evaluateAdditionPrecedence(): Long {
    val expression = this.chunked(1)
        .filter { it.isNotBlank() }
        .let { LinkedList(it) }

    val print = fun() {
        println("expression is: ${expression.joinToString(" ")}")
    }
    print()

    do {
        var calculationResult = expression.findParentheses()

        if (calculationResult == null) {
            calculationResult = expression.findOperationInParentheses()
        }
        if (calculationResult == null) {
            calculationResult = expression.findAddition()
        }
        if (calculationResult == null) {
            calculationResult = expression.findLongOperationInParentheses()
        }
        if (calculationResult == null) {
            calculationResult = expression.findVeryLongOperationInParentheses()
        }
        if (calculationResult == null) {
            calculationResult = expression.findVeryVeryLongOperationInParentheses()
        }
        if (calculationResult == null) {
            calculationResult = expression.findVeryVeryVeryLongOperationInParentheses()
        }
        if (calculationResult == null) {
            calculationResult = expression.findMultiplication()
        }

        calculationResult?.let { (leftIndex, result, length) ->
            repeat(length) {
                expression.removeAt(leftIndex)
            }
            expression.add(leftIndex, result.toString())
        }
        print()
    } while (expression.size > 1 && calculationResult != null)

    if (expression.size > 1) {
        throw IllegalStateException("Evaluation did not finish!")
    }

    return expression.first().toLong()
}

data class Result(val index: Int, val result: Long, val length: Int)

fun List<String>.findAddition(): Result? {
    return this.asSequence()
        .withIndex()
        // find an addition to evaluation
        .windowed(3) { (left, middle, right) ->
            if (middle.value == "+" && left.isNumber() && right.isNumber()) {
                val result = left.value.toLong() + right.value.toLong()
                Result(left.index, result, 3)
            } else null
        }
        .filterNotNull()
        .firstOrNull()
}

fun List<String>.findMultiplication(): Result? {
    return this.asSequence()
        .withIndex()
        // find an addition to evaluation
        .windowed(3) { (left, middle, right) ->
            if (middle.value == "*" && left.isNumber() && right.isNumber()) {
                val result = left.value.toLong() * right.value.toLong()
                Result(left.index, result, 3)
            } else null
        }
        .filterNotNull()
        .firstOrNull()
}

fun List<String>.findParentheses(): Result? {
    return this.asSequence()
        .withIndex()
        // find an addition to evaluation
        .windowed(3) { (left, middle, right) ->
            if (left.value == "(" && middle.isNumber() && right.value == ")") {
                val result = middle.value
                Result(left.index, result.toLong(), 3)
            } else null
        }
        .filterNotNull()
        .firstOrNull()
}

fun List<String>.findOperationInParentheses(): Result? {
    return this.asSequence()
        .withIndex()
        .windowed(5) { (open, left, middle, right, close) ->
            if (open.value == "(" && left.isNumber() && right.isNumber() && close.value == ")") {
                val result = when (middle.value) {
                    "+" -> left.value.toLong() + right.value.toLong()
                    "*" -> left.value.toLong() * right.value.toLong()
                    else -> throw IllegalStateException("Unexpected operation!")
                }
                Result(open.index, result, 5)
            } else null
        }
        .filterNotNull()
        .firstOrNull()
}

fun List<String>.findLongOperationInParentheses(): Result? {
    val windowSize = 7
    return this.asSequence()
        .withIndex()
        .windowed(windowSize) { window ->
            if (window[0].value == "(" && window[1].isNumber() && window[2].value == "*" && window[3].isNumber() && window[4].value == "*" && window[5].isNumber() && window[6].value == ")") {
                val result = window[1].value.toLong() * window[3].value.toLong() * window[5].value.toLong()
                Result(window[0].index, result, windowSize)
            } else null
        }
        .filterNotNull()
        .firstOrNull()
}

fun List<String>.findVeryLongOperationInParentheses(): Result? {
    val windowSize = 9
    return this.asSequence()
        .withIndex()
        .windowed(windowSize) { window ->
            if (window[0].value == "(" && window[1].isNumber() && window[2].value == "*" && window[3].isNumber() && window[4].value == "*" && window[5].isNumber() && window[6].value == "*" && window[7].isNumber() && window[8].value == ")") {
                val result =
                    window[1].value.toLong() * window[3].value.toLong() * window[5].value.toLong() * window[7].value.toLong()
                Result(window[0].index, result, windowSize)
            } else null
        }
        .filterNotNull()
        .firstOrNull()
}

fun List<String>.findVeryVeryLongOperationInParentheses(): Result? {
    val windowSize = 11
    return this.asSequence()
        .withIndex()
        .windowed(windowSize) { window ->
            if (window[0].value == "(" && window[1].isNumber() && window[2].value == "*" && window[3].isNumber() && window[4].value == "*" && window[5].isNumber() && window[6].value == "*" && window[7].isNumber()&& window[8].value == "*" && window[9].isNumber() && window[10].value == ")") {
                val result =
                    window[1].value.toLong() * window[3].value.toLong() * window[5].value.toLong() * window[7].value.toLong() * window[9].value.toLong()
                Result(window[0].index, result, windowSize)
            } else null
        }
        .filterNotNull()
        .firstOrNull()
}


fun List<String>.findVeryVeryVeryLongOperationInParentheses(): Result? {
    val windowSize = 13
    return this.asSequence()
        .withIndex()
        .windowed(windowSize) { window ->
            if (window[0].value == "(" && window[1].isNumber() && window[2].value == "*" && window[3].isNumber() && window[4].value == "*" && window[5].isNumber() && window[6].value == "*" && window[7].isNumber() && window[8].value == "*" && window[9].isNumber()&& window[10].value == "*" && window[11].isNumber() && window[12].value == ")") {
                val result =
                    window[1].value.toLong() * window[3].value.toLong() * window[5].value.toLong() * window[7].value.toLong() * window[9].value.toLong() * window[11].value.toLong()
                Result(window[0].index, result, windowSize)
            } else null
        }
        .filterNotNull()
        .firstOrNull()
}
val isNumber = Regex("[0-9]+")

fun IndexedValue<String>.isNumber(): Boolean {
    return this.value.matches(isNumber)
}
