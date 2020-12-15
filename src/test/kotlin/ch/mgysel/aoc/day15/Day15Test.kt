package ch.mgysel.aoc.day15

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day15Test : StringSpec({

    "verify first example of part 1" {
        val input = listOf(0, 3, 6)
        runGame(input) shouldBe 436
    }

    "verify more examples of part 2" {
        runGame(listOf(1, 3, 2)) shouldBe 1
        runGame(listOf(2, 1, 3)) shouldBe 10
        runGame(listOf(1, 2, 3)) shouldBe 27
        runGame(listOf(2, 3, 1)) shouldBe 78
        runGame(listOf(3, 2, 1)) shouldBe 438
        runGame(listOf(3, 1, 2)) shouldBe 1836
    }

    "verify solution of part 1" {
        solvePart1() shouldBe 410
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 238
    }
})

