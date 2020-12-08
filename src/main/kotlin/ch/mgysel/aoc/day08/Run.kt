package ch.mgysel.aoc.day08

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration
import ch.mgysel.aoc.day08.ProgramTermination.CORRECT
import ch.mgysel.aoc.day08.ProgramTermination.INFINITE_LOOP

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day08.txt")

fun solvePart1(): Any {
    val instructions = parseProgram()
    return runProgram(instructions).accumulator
}

fun solvePart2(): Any {
    val instructions = parseProgram()
    val jumpInstructions = instructions
        .mapIndexed { index, instruction ->
            instruction to index
        }
        .filter { (instruction, _) -> instruction.operation == "jmp" }

    val result = jumpInstructions.mapNotNull { (instruction, index) ->
        val modifiedProgram = instructions.toMutableList()
        modifiedProgram[index] = instruction.copy(operation = "nop")
        runProgram(modifiedProgram)
            .takeIf { it.termination == CORRECT }?.accumulator
    }
    if (result.size != 1) {
        throw IllegalStateException("Only one result expected, got ${result.size}")
    }
    return result.first()
}

data class Instruction(val operation: String, val argument: Int)
data class ProgramResult(val accumulator: Int, val termination: ProgramTermination)
enum class ProgramTermination {
    INFINITE_LOOP, CORRECT
}

fun parseProgram() = data.map { line ->
    val (operation, argument) = line.split(" ")
    Instruction(operation, argument.toInt())
}

fun runProgram(instructions: List<Instruction>): ProgramResult {
    val visitedIndexes = mutableSetOf<Int>()
    var accumulator = 0
    var position = 0
    while (!visitedIndexes.contains(position) && position < instructions.size) {
        visitedIndexes.add(position)
        val instruction = instructions[position]
        when (instruction.operation) {
            "acc" -> {
                accumulator += instruction.argument
                position++
            }
            "jmp" -> {
                position += instruction.argument
            }
            "nop" -> {
                position++
            }
            else -> {
                throw IllegalStateException("Unexpected operation ${instruction.operation}!")
            }
        }
    }
    val termination = if (position == instructions.size) CORRECT else INFINITE_LOOP
    return ProgramResult(accumulator, termination)
}
