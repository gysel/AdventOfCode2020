package ch.mgysel.aoc.day17

import ch.mgysel.aoc.common.cartesianProduct
import ch.mgysel.aoc.common.printAndMeasureDuration

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = """
    ##...#.#
    ####.#.#
    #...####
    ..#.#.#.
    ####.#..
    #.#.#..#
    .####.##
    ..#...##
""".trimIndent()

fun solvePart1(): Any {
    val map = data.parseMap(3)
    return calculateSolution(map)
}

fun solvePart2(): Any {
    val map = data.parseMap(4)
    return calculateSolution(map)
}

fun calculateSolution(startState: Map<Position, Boolean>): Int {
    val states = generateSequence(startState, ::simulateCycle)
    val sixthState = states
        .drop(6) // the initial map is emitted by the Sequence as well
        .first()
    return sixthState.values.count { it }
}

fun simulateCycle(state: Map<Position, Boolean>): Map<Position, Boolean> {
    val newState = state.map { (position, cube) ->
        position to calculateNewCubeState(cube, position, state)
    }.toMap()
    // add new neighbours of existing cubes
    val newCubes = newState.keys
        .flatMap(Position::getNeighbourCoordinates)
        .asSequence()
        .distinct()
        .filter { !newState.contains(it) }
        .map { it to calculateNewCubeState(false, it, state) }
        .toMap()
    return newState + newCubes
}

fun String.parseMap(dimensions: Int = 3): Map<Position, Boolean> {
    return lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            val position = if (dimensions == 3) Position(x, y, 0) else Position(x, y, 0, 0)
            position to (c == '#')
        }
    }.toMap()
}

private fun calculateNewCubeState(
    cube: Boolean,
    position: Position,
    state: Map<Position, Boolean>
): Boolean {
    val activeNeighbours = position.getNeighbourCoordinates()
        .mapNotNull { state[it] }
        .count { it }
    return when {
        cube && (activeNeighbours in 2..3) -> cube
        !cube && activeNeighbours == 3 -> true
        else -> false
    }
}

data class Position(val components: List<Int>) {

    constructor(vararg components: Int) : this(components.toList())

    fun getNeighbourCoordinates(): Sequence<Position> {
        val dimensions = components.size
        return cartesianProduct((1..dimensions).map { listOf(-1, 0, 1) })
            .asSequence()
            .filter { !it.all { c -> c == 0 } }
            .map { offsets ->
                Position(offsets.zip(components).map { it.first + it.second })
            }
    }
}
