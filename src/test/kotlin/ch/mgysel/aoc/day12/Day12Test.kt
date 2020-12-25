package ch.mgysel.aoc.day12

import ch.mgysel.aoc.common.Vector
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day12Test : StringSpec({

    val data = """
            F10
            N3
            F7
            R90
            F11
        """.trimIndent().lines()
    val actions = data.parseActions()

    "verify example part 1" {
        val start = Vector(0, 0)
        val east = Vector(1, 0)
        val (end, _) = calculatePartOne(actions, start, east)
        end shouldBe Vector(17, -8)
        start.distanceTo(end) shouldBe 25
    }

    "verify example part 2" {
        val shipStart = Vector(0, 0)
        val waypoint = Vector(10, 1)
        val (shipEnd, waypointEnd) = calculatePartTwo(actions, shipStart, waypoint)

        shipEnd shouldBe Vector(214, -72)
        waypointEnd shouldBe Vector(4, -10)
        shipStart.distanceTo(shipEnd) shouldBe 286
    }

    "verify solution of part 1" {
        solvePart1() shouldBe 998
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 71586
    }
})