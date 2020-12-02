package ch.mgysel.aoc.day02

import io.kotlintest.shouldBe
import org.junit.Test


class Day2Test {

    @Test
    fun `test second policy with given sample`() {
        matchesSecondPolicy("1-3 a: abcde") shouldBe true
        matchesSecondPolicy("1-3 b: cdefg") shouldBe false
        matchesSecondPolicy("2-9 c: ccccccccc") shouldBe false
    }

    @Test
    fun `verify solution part one`() {
        solvePart1() shouldBe 519
    }

    @Test
    fun `verify solution part two`() {
        solvePart2() shouldBe 708
    }
}