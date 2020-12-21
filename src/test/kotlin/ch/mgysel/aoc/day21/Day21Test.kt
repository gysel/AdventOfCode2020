package ch.mgysel.aoc.day21

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day21Test : StringSpec({
    "verify solution of part 1" {
        solvePart1() shouldBe 2020
    }

    "verify solution of part 2" {
        solvePart2() shouldBe "bcdgf,xhrdsl,vndrb,dhbxtb,lbnmsr,scxxn,bvcrrfbr,xcgtv"
    }

})