package ch.mgysel.aoc.day02

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day02 : StringSpec({
    "verify second policy with given samples" {
        matchesSecondPolicy("1-3 a: abcde") shouldBe true
        matchesSecondPolicy("1-3 b: cdefg") shouldBe false
        matchesSecondPolicy("2-9 c: ccccccccc") shouldBe false
    }

    "correct solution of part 1" {
        solvePart1() shouldBe 519
    }
    "correct solution of part 2" {
        solvePart2() shouldBe 708
    }
})
