package ch.mgysel.aoc.day19

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.maps.haveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class Day19Test : StringSpec({

    val sample = """
        0: 1 2
        1: "a"
        2: 1 3 | 3 1
        3: "b"
    """.trimIndent().lines()

    "verify example of part 1" {
        val (rules, messages) = sample.parse()
        rules should haveSize(4)
        rules.calculatePossibleMessages("1") shouldBe setOf("a")
        rules.calculatePossibleMessages("3") shouldBe setOf("b")
        rules.calculatePossibleMessages("2") shouldBe setOf("ab", "ba")
        rules.calculatePossibleMessages("0") shouldBe setOf("aab", "aba")
    }

    "verify solution of part 1" {
        solvePart1() shouldBe 120
    }

    "verify examples of part 2" {
    }


    "verify solution of part 2" {
        solvePart2() shouldBe "TODO"
    }


})
