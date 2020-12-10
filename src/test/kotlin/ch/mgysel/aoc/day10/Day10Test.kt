package ch.mgysel.aoc.day10

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day10Test : StringSpec({

    "verify first sample of part one" {
        val adapters = listOf(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4)
        calculatePartOne(adapters) shouldBe (7 * 5)
    }

    "verify correct solution of part 1" {
        solvePart1() shouldBe 1984
    }

    "verify first sample of part two" {
        val adapters = listOf(16, 10, 15, 5, 1, 11, 7, 19, 6, 12, 4)
        calculatePartTwo(adapters) shouldBe 8
    }
    "verify second sample of part two" {
        val adapters = listOf(
            28, 33, 18, 42, 31, 14, 46, 20, 48,
            47, 24, 23, 49, 45, 19, 38, 39, 11, 1,
            32, 25, 35, 8, 17, 7, 9, 4, 2, 34, 10, 3
        )
        calculatePartTwo(adapters) shouldBe 19208
    }

    "verify correct solution of part 2" {
        solvePart2() shouldBe 3543369523456
    }

})