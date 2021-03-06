package ch.mgysel.aoc.day04

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.read("day04.txt")
val mandatoryFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
val validEyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
val validHaircolor = Regex("#[0-9a-f]{6}")
val validPassport = Regex("[0-9]{9}")

data class Passport(
    val byr: Int,
    val iyr: Int,
    val eyr: Int,
    val hgt: Int,
    val hcl: Int,
    val ecl: String,
    val pid: String,
    val cid: String?
)

fun solvePart1(): Any {
    return parsePassports(data)
        .filter { passport ->
            passportHasMandatoryFields(passport, mandatoryFields)
        }
        .count()
}

fun solvePart2(): Any {
    return parsePassports(data)
        .filter { passportHasMandatoryFields(it, mandatoryFields) }
        .filter { passport -> passportIsValid(passport) }
        .count()
}

fun passportIsValid(passport: Map<String, String>): Boolean {
    val validateHeight = fun(height: String?): Boolean {
        if (height.isNullOrBlank()) return false
        val heightNumber = height.dropLast(2).toInt()
        return when (height.takeLast(2)) {
            "cm" -> heightNumber in 150..193
            "in" -> heightNumber in 59..76
            else -> false
        }
    }
    return (passport["byr"]?.toInt() in 1920..2002
            &&
            passport["iyr"]?.toInt() in 2010..2020
            &&
            passport["eyr"]?.toInt() in 2020..2030
            &&
            validateHeight(passport["hgt"])
            &&
            passport["hcl"]?.matches(validHaircolor) ?: false
            &&
            passport["ecl"] in validEyeColors
            &&
            passport["pid"]?.matches(validPassport) ?: false)
}

fun parsePassports(data: String): Sequence<Map<String, String>> {
    return data
        .splitToSequence("\n\n")
        .map(::parsePassport)
}

fun parsePassport(unparsedPassport: String): Map<String, String> {
    return unparsedPassport
        .replace("\n", " ")
        .split(' ')
        .map {
            val (key, value) = it.split(':')
            key to value
        }.toMap()
}

fun passportHasMandatoryFields(passport: Map<String, String>, mandatoryFields: List<String>) =
    passport.keys.containsAll(mandatoryFields)
