package ch.mgysel.aoc.day17

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day17Test : StringSpec({

    "verify example of part 1" {
        val map = """
            .#.
            ..#
            ###
        """.trimIndent().parseMap()

        val states = generateSequence(map, ::simulateCycle)
            .drop(1) // drop the initial map
            .take(6).toList()
        states.last().count { it.value } shouldBe 112
    }

    "verify examples of part 2" {
    }

    "verify solution of part 1" {
        solvePart1() shouldBe 382
    }

    "verify solution of part 2" {
        solvePart2() shouldBe "TODO"
    }
})
