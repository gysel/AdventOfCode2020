package ch.mgysel.aoc.day25

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day25Test : StringSpec({

    "verify solution of part 1" {
        solvePart1() shouldBe 3803729
    }

    "verify example" {
        findSecretLoopSize(5764801) shouldBe 8
        findSecretLoopSize(17807724) shouldBe 11
    }

})

