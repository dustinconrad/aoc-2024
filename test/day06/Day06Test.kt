package day06

import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {

    @Test
    fun testPart2() {
        val input = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
        """.trimIndent()
            .lines()

        assertEquals(6, part2(input))
    }

}