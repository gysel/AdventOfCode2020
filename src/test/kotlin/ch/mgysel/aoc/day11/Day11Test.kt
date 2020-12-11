package ch.mgysel.aoc.day11

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

typealias SeatMap = List<List<Char>>

class Day11Test : StringSpec({

    val map: SeatMap = """
        L.LL.LL.LL
        LLLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent().lines().parseMap()

    "verify example of part 1" {
        val mapStates: Sequence<SeatMap> = createMapStates(map, 4, ::countAdjacentSeats)
        reachEquilibrium(mapStates) shouldBe 37
    }

    "verify example of part 2" {
        val mapStates: Sequence<SeatMap> = createMapStates(map, 5, ::countVisibleSeats)
        reachEquilibrium(mapStates) shouldBe 26
    }

    "verify correct solution of part 1" {
        solvePart1() shouldBe 2296
    }

    "verify correct solution of part 2" {
        solvePart2() shouldBe 2089
    }

})