package ch.mgysel.aoc.day13

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day13Test : StringSpec({


    "verify solution of part 1" {
        solvePart1() shouldBe 205
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 803025030761664L
    }

    "verify first example of part 2" {
        val buses = "7,13,x,x,59,x,31,19".parseBusLines()
        calculatePart2(buses) shouldBe 1068781
    }
    "verify other examples of part 2" {
        "17,x,13,19".parseBusLines()
            .let(::calculatePart2) shouldBe 3417

        "67,7,59,61".parseBusLines()
            .let(::calculatePart2) shouldBe 754018

        "67,x,7,59,61".parseBusLines()
            .let(::calculatePart2) shouldBe 779210

        "67,7,x,59,61".parseBusLines()
            .let(::calculatePart2) shouldBe 1261476

        "1789,37,47,1889".parseBusLines()
            .let(::calculatePart2) shouldBe 1202161486
    }
})