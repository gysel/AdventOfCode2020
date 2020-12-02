package ch.mgysel.aoc.day02

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day02.txt")

fun solvePart1(): Int = data.count { line ->
    val (rawPolicy, password) = line.split(": ")
    val policy = Policy.parse(rawPolicy)
    password.count { it == policy.char } in policy.first..policy.second
}

fun solvePart2(): Int = data.count(::matchesSecondPolicy)

data class Policy(val first: Int, val second: Int, val char: Char) {
    companion object {
        fun parse(rawPolicy: String): Policy {
            val (policyFrom, policyTo, policyChar) = rawPolicy.split("-", " ")
            return Policy(policyFrom.toInt(), policyTo.toInt(), policyChar[0])
        }
    }
}

fun matchesSecondPolicy(line: String): Boolean {
    val (rawPolicy, password) = line.split(": ")
    val policy = Policy.parse(rawPolicy)
    return with(policy) {
        val firstMatch = password.length >= first && password[first - 1] == char
        val secondMatch = password.length >= second && password[second - 1] == char
        firstMatch.xor(secondMatch)
    }
}
