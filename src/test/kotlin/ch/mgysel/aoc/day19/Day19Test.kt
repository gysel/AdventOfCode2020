package ch.mgysel.aoc.day19

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.maps.haveSize
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class Day19Test : StringSpec({

    val firstExample = """
        0: 1 2
        1: "a"
        2: 1 3 | 3 1
        3: "b"
    """.trimIndent().lines()

    "verify example of part 1" {
        val (rules, _) = firstExample.parse()
        rules should haveSize(4)
        rules.ruleMatch("0", "aab") shouldBe true
        rules.ruleMatch("0", "aba") shouldBe true
        rules.ruleMatch("0", "aaa") shouldBe false
        rules.ruleMatch("0", "abab") shouldBe false
    }

    val secondExample = """
        0: 4 1 5
        1: 2 3 | 3 2
        2: 4 4 | 5 5
        3: 4 5 | 5 4
        4: "a"
        5: "b"

        ababbb
        bababa
        abbbab
        aaabbb
        aaaabbb
    """.trimIndent().lines()

    "verify second example of part 1" {
        val (rules, messages) = secondExample.parse()
        val matching = messages.filter { rules.ruleMatch("0", it) }
        matching shouldContainExactly listOf("ababbb", "abbbab")
    }

    "verify solution of part 1" {
        solvePart1() shouldBe 120
    }

    val examplePartTwo = """
        42: 9 14 | 10 1
        9: 14 27 | 1 26
        10: 23 14 | 28 1
        1: "a"
        11: 42 31
        5: 1 14 | 15 1
        19: 14 1 | 14 14
        12: 24 14 | 19 1
        16: 15 1 | 14 14
        31: 14 17 | 1 13
        6: 14 14 | 1 14
        2: 1 24 | 14 4
        0: 8 11
        13: 14 3 | 1 12
        15: 1 | 14
        17: 14 2 | 1 7
        23: 25 1 | 22 14
        28: 16 1
        4: 1 1
        20: 14 14 | 1 15
        3: 5 14 | 16 1
        27: 1 6 | 14 18
        14: "b"
        21: 14 1 | 1 14
        25: 1 1 | 1 14
        22: 14 14
        8: 42
        26: 14 22 | 1 20
        18: 15 15
        7: 14 5 | 1 21
        24: 14 1

        abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
        bbabbbbaabaabba
        babbbbaabbbbbabbbbbbaabaaabaaa
        aaabbbbbbaaaabaababaabababbabaaabbababababaaa
        bbbbbbbaaaabbbbaaabbabaaa
        bbbababbbbaaaaaaaabbababaaababaabab
        ababaaaaaabaaab
        ababaaaaabbbaba
        baabbaaaabbaaaababbaababb
        abbbbabbbbaaaababbbbbbaaaababb
        aaaaabbaabaaaaababaa
        aaaabbaaaabbaaa
        aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
        babaaabbbaaabaababbaabababaaab
        aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba
    """.trimIndent().lines()

    "verify examples of part 2" {
        val (rules, messages) = examplePartTwo.parse()
        messages.count { rules.ruleMatch("0", it) } shouldBe 3
        val newRules = rules + modifications
        messages.count { newRules.ruleMatch("0", it) } shouldBe 12
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 350
    }


})
