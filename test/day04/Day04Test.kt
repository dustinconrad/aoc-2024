package day04

import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {

    @Test
    fun testPart1() {
        val input = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent()
            .lines()

        assertEquals(18, part1(input))
    }
//
//    @Test
//    fun testPart2() {
//        val input = """
//
//        """.trimIndent()
//            .lines()
//
//        assertEquals(4, part2(input))
//    }

}