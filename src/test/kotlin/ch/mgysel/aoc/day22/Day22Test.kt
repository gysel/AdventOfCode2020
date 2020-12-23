package ch.mgysel.aoc.day22

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day22Test : StringSpec({

    "verify solution of part 1" {
        solvePart1() shouldBe 32824
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 36515
    }

    "verify example of part 2" {
        val player1 = listOf(9, 2, 6, 3, 1)
        val player2 = listOf(5, 8, 4, 7, 10)
        val result = playRecursiveCombat(player1, player2)
        result.winner shouldBe "2"
        result.stack shouldBe listOf(7, 5, 6, 2, 4, 1, 10, 8, 9, 3)
        result.calculateScore() shouldBe 291
    }

})