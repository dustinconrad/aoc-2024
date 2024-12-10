package day10

import kotlin.test.Test
import kotlin.test.assertEquals

class Da10Test {

    @Test
    fun testPart1_1() {
        val input = """
        ...0...
        ...1...
        ...2...
        6543456
        7.....7
        8.....8
        9.....9
        """.trimIndent().lines()

        assertEquals(2, part1(input))
    }

    @Test
    fun testPart1_2() {
        val input = """
        ..90..9
        ...1.98
        ...2..7
        6543456
        765.987
        876....
        987....
        """.trimIndent().lines()

        assertEquals(4, part1(input))
    }



}