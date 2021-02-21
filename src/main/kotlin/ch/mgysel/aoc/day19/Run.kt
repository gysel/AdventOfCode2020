package ch.mgysel.aoc.day19

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.cartesianProduct
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day19.txt")

fun solvePart1(): Any {
    val (rules, messages) = data.parse()
    val matchingMessages = rules.calculatePossibleMessages("0")
    return messages.count { it in matchingMessages }
}

fun List<String>.parse(): Pair<Map<String, String>, List<String>> {
    val rules = this
        .takeWhile(String::isNotBlank)
        .map { rawRule ->
            val (id, instructions) = rawRule.split(": ", limit = 2)
            id to instructions
        }.toMap()

    val messages = data.drop(rules.size + 1)
    return rules to messages
}

/**
 * Set: Can be any of...
 */
fun Map<String, String>.calculatePossibleMessages(ruleId: String): Set<String> {
    val rule = this[ruleId] ?: throw IllegalStateException("No rule with id $ruleId!")
    val tokens = rule.split(" ")
    val processToken = fun(token: String): Set<String> {
        return if (token.startsWith("\"")) {
            setOf(token.drop(1).dropLast(1))
        } else {
            this.calculatePossibleMessages(token)
        }
    }
    val combine = fun(input: List<String>): Set<String> {
        // can the cartesian product accept an Iterable?
        return cartesianProduct(input.map { processToken(it).toList() })
            .map { it.joinToString("") }.toSet()
    }
    return if (tokens.contains("|")) {
        val left = tokens.takeWhile { it != "|" }
        val right = tokens.drop(left.size + 1)
        combine(left) + combine(right)
    } else {
        combine(tokens)
    }
}

fun solvePart2(): Any = "TODO"