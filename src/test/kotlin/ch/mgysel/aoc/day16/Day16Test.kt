package ch.mgysel.aoc.day16

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day16Test : StringSpec({

    "verify example of part 1" {
        val rules = listOf(
            "class" to listOf(1..3, 5..7),
            "row" to listOf(6..11, 33..44),
            "seat" to listOf(13..40, 45..50)
        )
        val tickets = listOf(
            listOf(7, 3, 47),
            listOf(40, 4, 50),
            listOf(55, 2, 20),
            listOf(38, 6, 12),
        )
        countErrors(tickets, rules.flatMap { it.second }) shouldBe 71
    }

    "verify examples of part 2" {
        val rules = listOf(
            "class" to listOf(0..1, 4..19),
            "row" to listOf(0..5, 8..19),
            "seat" to listOf(0..13, 16..19)
        )
        val tickets = listOf(
            listOf(3, 9, 18),
            listOf(15, 1, 5),
            listOf(5, 14, 9),
        )
        val myTicket = listOf(11, 12, 13)

        val positions = findRulePositions(rules, tickets)
        positions.size shouldBe 3
        positions[0] shouldBe "row"
        positions[1] shouldBe "class"
        positions[2] shouldBe "seat"

        val myTicketPositions: Map<String, Int> = mapTicketToPositions(myTicket, positions)
        myTicketPositions["class"] shouldBe 12
        myTicketPositions["row"] shouldBe 11
        myTicketPositions["seat"] shouldBe 13
    }

    "verify solution of part 1" {
        solvePart1() shouldBe 18227
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 2355350878831L
    }
})
