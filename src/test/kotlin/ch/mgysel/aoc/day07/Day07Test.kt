package ch.mgysel.aoc.day07

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class Day07Test : StringSpec({
    val bagsExampleOne = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.""".trimIndent()
        .lines().let(::parseBags)

    "verify samples of part 1" {
        val sources = findSources(bagsExampleOne, "shiny gold")
        sources shouldContainExactlyInAnyOrder listOf("bright white", "muted yellow", "dark orange", "light red")
        sources.count() shouldBe 4
    }

    "verify solution of part 1" {
        solvePart1() shouldBe 326
    }

    "verify first sample of part 2" {
        calculateRequiredBags(bagsExampleOne, "shiny gold") shouldBe 32 + 1
    }

    "verify second sample of part 2" {
        val data = """
            shiny gold bags contain 2 dark red bags.
            dark red bags contain 2 dark orange bags.
            dark orange bags contain 2 dark yellow bags.
            dark yellow bags contain 2 dark green bags.
            dark green bags contain 2 dark blue bags.
            dark blue bags contain 2 dark violet bags.
            dark violet bags contain no other bags.
        """.trimIndent().lines()
        val bags = parseBags(data)
        calculateRequiredBags(bags, "shiny gold") shouldBe 126 + 1
    }
})
