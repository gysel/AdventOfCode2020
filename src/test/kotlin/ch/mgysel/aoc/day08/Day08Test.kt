package ch.mgysel.aoc.day08

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day08Test : StringSpec({

    "verify correct solution of part 1" {
        solvePart1() shouldBe 1594
    }

    "verify correct solution of part 2" {
        solvePart2() shouldBe 758
    }

})