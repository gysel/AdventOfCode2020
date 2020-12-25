package ch.mgysel.aoc.day24

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.Vector
import ch.mgysel.aoc.common.printAndMeasureDuration
import java.util.*

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day24.txt")

fun solvePart1() = calculateInitialFloor(data).countBlackTiles()

fun solvePart2(): Any {
    var floor = calculateInitialFloor(data)
    repeat(100) {
        floor = floor.flipTiles()
    }
    return floor.countBlackTiles()
}

fun calculateInitialFloor(data: List<String>): Map<Vector, String> {
    val floor = mutableMapOf<Vector, String>()
    data.map { it.toDirections() }
        .map { it.reduce(Vector::plus) }
        .forEach { tile ->
            floor.compute(tile) { _, color ->
                if (color == null || color == "white")
                    "black"
                else
                    "white"
            }
        }
    return floor
}

fun Map<Vector, String>.countBlackTiles() = this.count { (_, color) -> color == "black" }

fun Map<Vector, String>.flipTiles(): Map<Vector, String> {
    val currentTiles = this.keys
    val neighbours = currentTiles
        .flatMap { it.neighbours() }
        .distinct()
        .filter { it !in currentTiles }
        .map { it to "white" }
    return (this + neighbours)
        .map { (tile, color) ->
            val blackNeighbours = tile.neighbours()
                .count { this.getOrDefault(it, "white") == "black" }
            if (color == "black" && (blackNeighbours == 0 || blackNeighbours > 2)) {
                tile to "white"
            } else if (color == "white" && blackNeighbours == 2) {
                tile to "black"
            } else {
                tile to color
            }
        }
        .filter { (_, color) -> color == "black" }
        .toMap()
}

val east = Vector(2, 0)
val southEast = Vector(1, -1)
val southWest = Vector(-1, -1)
val west = Vector(-2, 0)
val northWest = Vector(-1, 1)
val northEast = Vector(1, 1)
val hexagonNeighbours = listOf(east, southEast, southWest, west, northWest, northEast)

fun Vector.neighbours() = hexagonNeighbours.map { this + it }

fun String.toDirections(): List<Vector> {
    val chars = LinkedList(this.toCharArray().toList())
    val result = mutableListOf<Vector>()
    while (chars.isNotEmpty()) {
        result += when (chars.pollFirst()) {
            'e' -> east
            's' -> when (chars.pollFirst()) {
                'e' -> southEast
                'w' -> southWest
                else -> throw IllegalStateException("Unexpected char!")
            }
            'w' -> west
            'n' -> when (chars.pollFirst()) {
                'w' -> {
                    northWest
                }
                'e' -> {
                    northEast
                }
                else -> throw IllegalStateException("Unexpected char!")
            }
            else -> throw IllegalStateException("Unexpected char!")
        }
    }
    return result
}