package ch.mgysel.aoc.day24

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Day24Test : StringSpec({

    "verify solution of part 1" {
        solvePart1() shouldBe 411
    }

    "verify solution of part 2" {
        solvePart2() shouldBe 4092
    }

    val data = """
        sesenwnenenewseeswwswswwnenewsewsw
        neeenesenwnwwswnenewnwwsewnenwseswesw
        seswneswswsenwwnwse
        nwnwneseeswswnenewneswwnewseswneseene
        swweswneswnenwsewnwneneseenw
        eesenwseswswnenwswnwnwsewwnwsene
        sewnenenenesenwsewnenwwwse
        wenwwweseeeweswwwnwwe
        wsweesenenewnwwnwsenewsenwwsesesenwne
        neeswseenwwswnwswswnw
        nenwswwsewswnenenewsenwsenwnesesenew
        enewnwewneswsewnwswenweswnenwsenwsw
        sweneswneswneneenwnewenewwneswswnese
        swwesenesewenwneswnwwneseswwne
        enesenwswwswneneswsenwnewswseenwsese
        wnwnesenesenenwwnenwsewesewsesesew
        nenewswnwewswnenesenwnesewesw
        eneswnwswnwsenenwnwnwwseeswneewsenese
        neswnwewnwnwseenwseesewsenwsweewe
        wseweeenwnesenwwwswnew
    """.trimIndent().lines()

    "verify example - first 3 days" {
        var floor = calculateInitialFloor(data)
        floor.countBlackTiles() shouldBe 10
        // Day 1
        floor = floor.flipTiles()
        floor.countBlackTiles() shouldBe 15
        // Day 2
        floor = floor.flipTiles()
        floor.countBlackTiles() shouldBe 12
        // Day 3
        floor = floor.flipTiles()
        floor.countBlackTiles() shouldBe 25
    }

    "verify example - 100 days" {
        var floor = calculateInitialFloor(data)
        repeat(100) {
            floor = floor.flipTiles()
        }
        floor.countBlackTiles() shouldBe 2208
    }
})

