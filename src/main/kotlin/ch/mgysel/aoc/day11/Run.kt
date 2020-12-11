package ch.mgysel.aoc.day11

import ch.mgysel.aoc.common.InputData
import ch.mgysel.aoc.common.printAndMeasureDuration

typealias SeatMap = List<List<Char>>

fun main() {
    printAndMeasureDuration("Part One", ::solvePart1)
    printAndMeasureDuration("Part Two", ::solvePart2)
}

val data = InputData.readLines("day11.txt")
fun List<String>.parseMap(): SeatMap = this.map { it.toCharArray().toList() }

fun solvePart1(): Any {
    val map = data.parseMap()
    val mapStates: Sequence<SeatMap> = createMapStates(map, 4, ::countAdjacentSeats)
    return reachEquilibrium(mapStates)
}

fun solvePart2(): Any {
    val map = data.parseMap()
    val mapStates: Sequence<SeatMap> = createMapStates(map, 5, ::countVisibleSeats)
    return reachEquilibrium(mapStates)
}

fun reachEquilibrium(mapStates: Sequence<SeatMap>): Int = mapStates
    .zipWithNext()
    //.onEach { it.first.print() }
    .first { (it, next) -> it == next }
    .let { (it, _) -> countOccupiedSeats(it) }

fun SeatMap.print() {
    this.forEach { println(it.joinToString("")) }
    println("")
}

private fun countOccupiedSeats(map: SeatMap) =
    map.sumOf { row -> row.count { seat -> seat == '#' } }

fun createMapStates(
    map: SeatMap,
    leaveSeatThreshold: Int,
    countSeatsFunction: (rowIndex: Int, columnIndex: Int, map: SeatMap, state: Char) -> Int
): Sequence<SeatMap> = sequence {
    var currentMap = map
    while (true) {
        yield(currentMap)
        currentMap = currentMap.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, seat ->
                val occupiedSeats = countSeatsFunction(rowIndex, columnIndex, currentMap, '#')
                if (seat == 'L' && occupiedSeats == 0) {
                    '#'
                } else {

                    if (seat == '#' && occupiedSeats >= leaveSeatThreshold) { //
                        'L'
                    } else {
                        seat
                    }
                }
            }
        }
    }
}

val directions = (-1..1).flatMap { x ->
    (-1..1).map { y -> x to y }
}.filter { !(it.first == 0 && it.second == 0) }

fun countAdjacentSeats(rowIndex: Int, columnIndex: Int, map: SeatMap, state: Char): Int {
    return directions.sumBy { (x, y) ->
        if (map.getSeatState(columnIndex + x, rowIndex + y) == state) 1 else 0
    }
}

fun countVisibleSeats(rowIndex: Int, columnIndex: Int, map: SeatMap, state: Char): Int {
    return directions
        .map { direction ->
            generateSequence(columnIndex to rowIndex) { (columnIndex, rowIndex) ->
                columnIndex + direction.first to rowIndex + direction.second
            }.drop(1) // drop the seed from the sequence
        }
        .map { coordinatesInOneDirection ->
            val visibleSeat = coordinatesInOneDirection
                .map { (column, row) ->
                    map.getSeatState(column, row)
                }
                .firstOrNull { it != '.' }
            if (visibleSeat == '#') 1 else 0
        }.sum()
}

fun SeatMap.getSeatState(column: Int, row: Int) = this.getOrNull(row)?.getOrNull(column)
