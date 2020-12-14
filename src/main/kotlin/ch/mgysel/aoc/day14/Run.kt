package ch.mgysel.aoc.day14

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day14.txt")
const val maskPrefix = "mask = "
val assignment = Regex("mem.([0-9]+). = ([0-9]+)")
const val radix = 2

fun solvePart1(): Any {
    val memory: MutableMap<Long, Long> = mutableMapOf()
    data.runProgram { mask: String, index: String, number: String ->
        memory[index.toLong()] = number.toLong()
            .toString(radix)
            .padStart(36, '0')
            .toCharArray()
            .zip(mask.toCharArray())
            .map { (number, mask) ->
                if (mask == 'X') {
                    number
                } else {
                    mask
                }
            }
            .joinToString("")
            .toLong(radix)
    }
    return memory.values.sum()
}

fun solvePart2(): Any {
    return runPartTwo(data)
}

private fun List<String>.runProgram(runAssignment: (mask: String, index: String, number: String) -> Unit) {
    var mask = ""
    this.forEach { line ->
        if (line.startsWith(maskPrefix)) {
            mask = line.removePrefix(maskPrefix)
        } else {
            val match = assignment.find(line)!!
            val (_, index, number) = match.groupValues
            runAssignment(mask, index, number)
        }
    }
}

fun runPartTwo(data: List<String>): Long {
    val memory: MutableMap<Long, Long> = mutableMapOf()
    data.runProgram { mask: String, index: String, number: String ->
        val indexes: List<Long> = index
            .toLong()
            .toString(radix)
            .padStart(36, '0')
            .toCharArray()
            .zip(mask.toCharArray())
            .map { (index, mask) ->
                when (mask) {
                    'X' -> 'X'
                    '1' -> '1'
                    '0' -> index
                    else -> throw IllegalStateException("Unexpected mask char $mask!")
                }
            }
            .joinToString("")
            .let { indexWithFloating ->
                val count = indexWithFloating.count { it == 'X' }
                if (count > 0) {
                    findAllBitCombinations(count)
                        .map { floatingValues ->
                            replace(indexWithFloating, floatingValues)
                        }
                } else listOf(indexWithFloating)
            }
            .map { it.toLong(radix) }
            .sorted()

        indexes.forEach { memory[it] = number.toLong() }
    }
    return memory.values.sum()
}

private fun findAllBitCombinations(count: Int): List<List<Char>> {
    var combinations = listOf(listOf('0'), listOf('1'))
    repeat(count - 1) {
        combinations = combinations.map { it + '0' } + combinations.map { it + '1' }
    }
    return combinations
}

private fun replace(
    indexWithFloating: String,
    floatingValues: List<Char>
): String {
    val valuesToFill = floatingValues.toMutableList()
    return indexWithFloating.map {
        if (it == 'X') {
            valuesToFill.removeFirst()
        } else
            it
    }.joinToString("")
}
