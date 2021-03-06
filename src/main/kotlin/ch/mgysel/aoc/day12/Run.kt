package ch.mgysel.aoc.day12

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.Vector
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day12.txt")

fun solvePart1(): Any {
    val actions = data.parseActions()
    val east = Vector(1, 0)
    val shipStart = Vector(0, 0)
    val (end, _) = calculatePartOne(actions, shipStart, east)
    return shipStart.distanceTo(end)
}

fun solvePart2(): Any {
    val actions = data.parseActions()
    val shipStart = Vector(0, 0)
    val waypoint = Vector(10, 1)
    val (end, _) = calculatePartTwo(actions, shipStart, waypoint)
    return shipStart.distanceTo(end)
}

fun calculatePartOne(
    actions: List<Pair<Char, Long>>,
    shipStart: Vector,
    direction: Vector
) = actions.fold(shipStart to direction) { (ship, direction), (action, distance) ->
    when (action) {
        'N' -> ship + Vector(0, distance) to direction
        'S' -> ship + Vector(0, -distance) to direction
        'E' -> ship + Vector(distance, 0) to direction
        'W' -> ship + Vector(-distance, 0) to direction
        'L' -> ship to direction.rotateLeft(distance)
        'R' -> ship to direction.rotateRight(distance)
        'F' -> ship + direction * distance to direction
        else -> throw IllegalStateException("Unexpected action $action!")
    }
}

fun calculatePartTwo(
    actions: List<Pair<Char, Long>>,
    shipStart: Vector,
    waypoint: Vector
) = actions.fold(shipStart to waypoint) { (ship, waypoint), (action, distance) ->
    when (action) {
        'N' -> ship to waypoint + Vector(0, distance)
        'S' -> ship to waypoint + Vector(0, -distance)
        'E' -> ship to waypoint + Vector(distance, 0)
        'W' -> ship to waypoint + Vector(-distance, 0)
        'L' -> ship to waypoint.rotateLeft(distance)
        'R' -> ship to waypoint.rotateRight(distance)
        'F' -> ship + waypoint * distance to waypoint
        else -> throw IllegalStateException("Unexpected action $action!")
    }
}

fun List<String>.parseActions() = this.map { it.first() to it.drop(1).toLong() }
