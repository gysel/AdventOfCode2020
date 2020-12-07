package ch.mgysel.aoc.day07

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day07.txt")

fun solvePart1(): Any {
    val bags = parseBags(data)
    return findSources(bags, "shiny gold").count()
}

fun solvePart2(): Any {
    val bags = parseBags(data)
    return calculateRequiredBags(bags, "shiny gold") - 1
}

data class Bag(val color: String, val containedColors: Map<String, Long>)

fun parseBags(data: List<String>): List<Bag> {
    return data.map { line ->
        val (bagRaw, containedBagsRaw) = line.split(" contain ")
        val bag = bagRaw.dropLast(" bags".length)
        val containedColors =
            if (containedBagsRaw != "no other bags.") {
                containedBagsRaw.split(", ")
                    .map { it ->
                        val (amount, colorA, colorB, _) = it.split(" ")
                        "$colorA $colorB" to amount.toLong()
                    }
            } else emptyList()
        Bag(bag, containedColors.toMap())
    }
}

fun findSources(bags: List<Bag>, color: String): Set<String> {
    return bags
        .filter { bag -> bag.containedColors.contains(color) }
        .flatMap { bag ->
            if (bag.containedColors.isEmpty()) {
                emptyList()
            } else {
                findSources(bags, bag.color) + bag.color
            }
        }.toSet()
}

fun calculateRequiredBags(bags: List<Bag>, color: String): Long {
    val bag = bags.first { it.color == color }
    return if (bag.containedColors.isEmpty()) {
        1L
    } else {
        1 + bag.containedColors.map { it.value * calculateRequiredBags(bags, it.key) }.sum()
    }
}

