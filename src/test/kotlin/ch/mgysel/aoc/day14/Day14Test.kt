package ch.mgysel.aoc.day14

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day14Test : StringSpec({

    "verify solution of part 1" {
        solvePart1() shouldBe 13727901897109
    }

    "verify example of part 2" {
        val program = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
        """.trimIndent().lines()
        runPartTwo(program) shouldBe 208
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 5579916171823
    }
})