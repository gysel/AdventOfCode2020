package ch.mgysel.aoc.day05

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day05Test : StringSpec({
    "verify part one samples" {
        parseSeat("BFFFBBFRRR") shouldBe Seat(70, 7)
        parseSeat("FFFBBBFRRR") shouldBe Seat(14, 7)
        parseSeat("BBFFBBFRLL") shouldBe Seat(102, 4)
    }

    "verify solution of part one" {
        solvePart1() shouldBe 883
    }

    "verify solution of part two" {
        solvePart2() shouldBe 532
    }
})