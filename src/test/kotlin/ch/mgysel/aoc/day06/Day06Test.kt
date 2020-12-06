package ch.mgysel.aoc.day06

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


class Day06Test : StringSpec({

    "verify correct solution of part 1" {
        solvePart1() shouldBe 7283
    }
    "verify correct solution of part 2" {
        solvePart2() shouldBe 3520
    }
})