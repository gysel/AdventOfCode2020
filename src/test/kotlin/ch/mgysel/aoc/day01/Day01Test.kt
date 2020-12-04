package ch.mgysel.aoc.day01

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class Day01Test : AnnotationSpec() {

    @Test
    fun `solve sample part 1`() {
        val numbers = listOf(1721, 979, 366, 299, 675, 1456)
        val pair = findCombinationWithSum(numbers, 2020, 2)
        pair shouldBe listOf(1721, 299)
        pair.product() shouldBe 514579
    }

    @Test
    fun `solve sample part 2`() {
        val numbers = listOf(1721, 979, 366, 299, 675, 1456)
        val pair = findCombinationWithSum(numbers, 2020, 3)
        pair shouldBe listOf(979, 366, 675)
        pair.product() shouldBe 241861950
    }

    @Test
    fun `verify correct solution of part 1`() {
        solvePart1() shouldBe 1018336
    }

    @Test
    fun `verify correct solution of part 2`() {
        solvePart2() shouldBe 288756720
    }

}