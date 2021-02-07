package ch.mgysel.aoc.day18

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day18Test : StringSpec({

    "verify example of part 1" {
        "1 + 2 * 3 + 4 * 5 + 6".evaluateSamePrecedence() shouldBe 71L
        "1 + (2 * 3) + (4 * (5 + 6))".evaluateSamePrecedence() shouldBe 51L
    }

    "verify solution of part 1" {
        solvePart1() shouldBe 75592527415659L
    }

    "verify examples of part 2" {
        listOf(
            "1 + 2 * 3 + 4 * 5 + 6" to 231,
            "1 + (2 * 3) + (4 * (5 + 6))" to 51,
            "2 * 3 + (4 * 5)" to 46,
            "5 + (8 * 3 + 9 + 3 * 4 * 3)" to 1445,
            "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))" to 669060,
            "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2" to 23340
        ).forEach { (expression, result) ->
            expression.evaluateAdditionPrecedence() shouldBe result.toLong()
        }
    }

    "debug" {
        val equation = "(8 + 8 * 2) * 9 * 5 + (2 + 2 + 3 * 7 * 5 + 6) + 5"
        equation.evaluateAdditionPrecedence() shouldBe 158112L
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 360029542265462L
    }

    "compare algorithm of part 2 with correct solution" {
        data.forEach { equation ->
            println("testing $equation")
            equation.evaluateAdditionPrecedence() shouldBe solvePart2WithNiceImplementation(equation.iterator())
        }
    }

})

/**
 * Borrowed from https://todd.ginsberg.com/post/advent-of-code/2020/day18/#d18p2
 */
fun solvePart2WithNiceImplementation(equation: CharIterator): Long {
    val multiplyThese = mutableListOf<Long>()
    var added = 0L
    while (equation.hasNext()) {
        val next = equation.nextChar()
        when {
            next == '(' -> added += solvePart2WithNiceImplementation(equation)
            next == ')' -> break
            next == '*' -> {
                multiplyThese += added
                added = 0L
            }
            next.isDigit() -> added += next.asLong()
        }
    }
    return (multiplyThese + added).product()
}

fun Char.asLong(): Long =
    this.toString().toLong()

fun Iterable<Long>.product(): Long =
    this.reduce { a, b -> a * b }
