package ch.mgysel.aoc.day17

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day17Test : StringSpec({
    val example = """
            .#.
            ..#
            ###
        """.trimIndent()

    "verify example of part 1" {
        val map = example.parseMap()
        calculateSolution(map) shouldBe 112
    }

    "verify examples of part 2" {
        val map = example.parseMap(4)
        calculateSolution(map) shouldBe 848
    }

    "verify solution of part 1" {
        solvePart1() shouldBe 382
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 2552
    }
})
