package ch.mgysel.aoc.day03

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day03.txt")
val landscape = Landscape.parse(data)

fun solvePart1(): Int {
    return moveAndCountTrees(landscape, Step(3, 1))
}

fun solvePart2(): Long {
    val paths = arrayOf(
        1 to 1,
        3 to 1,
        5 to 1,
        7 to 1,
        1 to 2
    ).map { (x, y) -> Step(x, y) }

    return paths.map { step ->
        moveAndCountTrees(landscape, step).toLong()
    }.reduce(Long::times)
}

fun moveAndCountTrees(landscape: Landscape, step: Step): Int {
    // 0,0 is top left
    val position = Position(0, 0)
    var treeCounter = 0
    do {
        position.move(step)
        if (landscape.getObstacle(position) == Obstacle.TREE) {
            treeCounter++
        }
    } while (position.y < landscape.getHeight() - 1)
    return treeCounter
}

enum class Obstacle {
    NONE, TREE
}

data class Step(val x: Int, val y: Int)

class Position(var x: Int, var y: Int) {
    fun move(step: Step) {
        x += step.x
        y += step.y
    }
}

class Landscape(
    private val fields: Array<Array<Obstacle>>,
    private val width: Int
) {

    fun getHeight() = fields.size

    fun getObstacle(position: Position): Obstacle {
        return fields[position.y][position.x.rem(width)]
    }

    companion object {
        fun parse(lines: List<String>): Landscape {
            val fields = lines.map { line ->
                line.map { char ->
                    when (char) {
                        '#' -> Obstacle.TREE
                        '.' -> Obstacle.NONE
                        else -> throw IllegalStateException("Unexpected character: $char")
                    }
                }.toTypedArray()
            }.toTypedArray()
            return Landscape(fields, lines.first().length)
        }
    }
}