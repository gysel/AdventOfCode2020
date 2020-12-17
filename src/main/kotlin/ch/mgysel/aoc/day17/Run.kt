package ch.mgysel.aoc.day17

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
    val startState: Map<Position, Boolean> = data.parseMap()
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

fun String.parseMap(): Map<Position, Boolean> {
    return lines().flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            val position = Position(x, y, 0)
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

data class Position(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    fun getNeighbourCoordinates() = (-1..1).flatMap { x ->
        (-1..1).flatMap { y ->
            (-1..1).mapNotNull { z ->
                Position(this.x + x, this.y + y, this.z + z)
                    .takeIf { it != this }
            }
        }
    }
}

fun solvePart2(): Any = "TODO"