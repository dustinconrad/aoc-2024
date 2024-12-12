package day12

import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {

    @Test
    fun testPart1_1() {
        val input = """
        AAAA
        BBCD
        BBCC
        EEEC
        """.trimIndent().lines().toList()

        assertEquals(140, part1(input))
    }

    @Test
    fun testPart1_2() {
        val input = """
        OOOOO
        OXOXO
        OOOOO
        OXOXO
        OOOOO
        """.trimIndent().lines().toList()

        assertEquals(772, part1(input))
    }

    @Test
    fun testPart1_3() {
        val input = """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
        """.trimIndent().lines().toList()

        assertEquals(1930, part1(input))
    }

}