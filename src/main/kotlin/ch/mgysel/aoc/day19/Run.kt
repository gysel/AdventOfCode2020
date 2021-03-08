package ch.mgysel.aoc.day19

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day19.txt")
val modifications = """
            8: 42 | 42 8
            11: 42 31 | 42 11 31
        """.trimIndent().lines().parseRules()

fun solvePart1(): Any {
    val (rules, messages) = data.parse()
    return messages.count { message -> rules.ruleMatch("0", message) }
}

fun solvePart2(): Any {
    val (rules, messages) = data.parse()
    val newRules = rules + modifications
    return messages.count { message -> newRules.ruleMatch("0", message) }
}

fun List<String>.parse(): Pair<Map<String, String>, List<String>> {
    val rules = parseRules()
    val messages = drop(rules.size + 1)
    return rules to messages
}

private fun List<String>.parseRules() = this
        .takeWhile(String::isNotBlank)
        .map { rawRule ->
            val (id, instructions) = rawRule.split(": ", limit = 2)
            id to instructions
        }.toMap()

fun Map<String, String>.ruleMatch(ruleId: String, message: String) =
        this.ruleMatch(ruleId, message, 0).any { it == message.length }

/**
 * Adjusted version of https://todd.ginsberg.com/post/advent-of-code/2020/day19/
 */
private fun Map<String, String>.ruleMatch(ruleId: String, message: String, position: Int): List<Int> =
        getValue(ruleId).split(" ").splitOr().flatMap { listOfRules -> // OR Rule
            var positions = listOf(position)
            listOfRules.forEach { rule ->  // AND Rule
                positions = positions.mapNotNull { idx ->
                    when {
                        rule.startsWith("\"") && message.getOrNull(idx).toString() == rule.removeQuotes() ->
                            listOf(idx + 1) // End condition
                        !rule.startsWith("\"") ->
                            ruleMatch(rule, message, idx)
                        else ->
                            null
                    }
                }.flatten()
            }
            positions
        }

private fun List<String>.splitOr(): List<List<String>> {
    return if (this.contains("|")) {
        val left = this.takeWhile { it != "|" }
        listOf(left, drop(left.size + 1))
    } else listOf(this)
}

private fun String.removeQuotes(): String {
    return drop(1).dropLast(1)
}
