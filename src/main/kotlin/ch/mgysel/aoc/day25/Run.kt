package ch.mgysel.aoc.day25

import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
}

val publicKeys = listOf(2959251, 4542595).map(Int::toLong)

fun solvePart1(): Any {
    val secretLoopSizes = publicKeys.map(::findSecretLoopSize)
    return handshake(secretLoopSizes.first(), publicKeys.last())
}

fun findSecretLoopSize(publicKey: Long): Int {
    return generateSequence(1L) { next(it, 7) }
        .indexOfFirst { it == publicKey }
}

private fun handshake(loopSize: Int, subject: Long): Long {
    var value = 1L
    repeat(loopSize) {
        value = next(value, subject)
    }
    return value
}

private fun next(value: Long, subject: Long) = (value * subject) % 20201227
