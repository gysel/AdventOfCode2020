package ch.mgysel.aoc.day23

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day23Test : StringSpec({

    "verify solution of part 1" {
        solvePart1() shouldBe "59374826"
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 66878091588
    }

    "verify example of part 1" {
        val result = playCrabCups("389125467".toCups(), 10, true)
        result.labelsAfterOne() shouldBe "92658374"
    }

    "labels after 1" {
        "583741926".toCups().labelsAfterOne() shouldBe "92658374"
    }

})

