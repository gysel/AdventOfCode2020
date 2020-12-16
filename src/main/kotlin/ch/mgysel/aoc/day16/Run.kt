package ch.mgysel.aoc.day16

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val rulesInput = InputData.readLines("day16-rules.txt")

val myTicket = listOf(151, 71, 67, 113, 127, 163, 131, 59, 137, 103, 73, 139, 107, 101, 97, 149, 157, 53, 109, 61)
val nearbyTicketsInput = InputData.readLines("day16-tickets.txt")

fun solvePart1(): Any {
    val nearbyTickets = parseTickets()
    val rules = parseRules(rulesInput)
        .flatMap { it.second }

    return countErrors(nearbyTickets, rules) // not 51
}

private fun parseTickets() = nearbyTicketsInput
    .map { it.split(",").map(String::toInt) }

private fun parseRules(lines: List<String>) = lines
    .map { line ->
        val (name, rangesRaw) = line.split(": ")
        val ranges = parseRanges(rangesRaw)
        name to ranges
    }

private fun parseRanges(rangesRaw: String): List<IntRange> {
    return rangesRaw.split(" or ").map { range ->
        val (from, to) = range.split("-").map(String::toInt)
        from..to
    }
}

fun countErrors(tickets: List<List<Int>>, rules: List<IntRange>): Int {
    return tickets.flatMap { ticket ->
        ticket.mapNotNull { number ->
            if (rules.none { number in it }) number else null
        }
    }.sum()
}

fun solvePart2(): Any {
    val nearbyTickets = parseTickets()
    val rules = parseRules(rulesInput)
    val ruleRangesFlat = rules.flatMap { it.second }

    val validTickets = nearbyTickets.filter { ticket ->
        ticket.all { number -> ruleRangesFlat.any { number in it } }
    }
    val rulePositions: Map<Int, String> = findRulePositions(rules, validTickets)

    return mapTicketToPositions(myTicket, rulePositions)
        .filter { "departure" in it.key }
        .map { it.value.toLong() }
        .reduce(Long::times)
}

fun findRulePositions(rules: List<Pair<String, List<IntRange>>>, tickets: List<List<Int>>): Map<Int, String> {
    val ruleNames = rules.map { it.first }
    val numbersByPosition = (tickets.first().indices).map { position ->
        tickets.map { it[position] }
    }
    val matches = rules.map { (ruleName, ruleRanges) ->
        val matches = numbersByPosition.mapIndexedNotNull { position, numbers ->
            if (numbers.all { number -> ruleRanges.any { number in it } })
                position
            else null
        }
        println("found potential matches for $ruleName: $matches")
        ruleName to matches.toMutableSet()
    }.toMap()

    val result = mutableMapOf<Int, String>()
    while (result.size < rules.size) {
        // put the ones with only one match into the result...
        val foundMatches = matches
            .filter { it.value.size == 1 && it.key !in result.values }
            .map { it.key to it.value.first() }

        foundMatches
            .forEach { (name, position) ->
                result[position] = name
                // ...and remove this position from all other matches
                ruleNames.forEach { matches[it]?.remove(position) }
                println("found match for $name: $position")
            }
    }
    return result
}

fun mapTicketToPositions(ticket: List<Int>, positions: Map<Int, String>): Map<String, Int> {
    return positions.map { (position, name) ->
        name to ticket[position]
    }.toMap()
}
