package ch.mgysel.aoc.day03

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day03 : StringSpec({

    "verify example of part 1" {
        val lines = listOf(
            "..##.......",
            "#...#...#..",
            ".#....#..#.",
            "..#.#...#.#",
            ".#...##..#.",
            "..#.##.....",
            ".#.#.#....#",
            ".#........#",
            "#.##...#...",
            "#...##....#",
            ".#..#...#.#"
        )
        val landscape = Landscape.parse(lines)
        moveAndCountTrees(landscape, Step(3, 1)) shouldBe 7

    }

    "verify correct solution of part 1" {
        solvePart1() shouldBe 184
    }
    "verify correct solution of part 2" {
        solvePart2() shouldBe 2431272960L
    }

})