package day13

import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {

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
    fun testPart2_1() {
        val input = """
        AAAA
        BBCD
        BBCC
        EEEC
        """.trimIndent().lines().toList()

        assertEquals(80, part2(input))
    }

}