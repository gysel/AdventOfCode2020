package ch.mgysel.aoc.day09

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day09Test : StringSpec({

    "verify correct solution of part 1" {
        solvePart1() shouldBe 217430975
    }

    "verify correct solution of part 2" {
        solvePart2() shouldBe 28509180
    }

})