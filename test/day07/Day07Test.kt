package day07

import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test {

    @Test
    fun testPart1() {
        val input = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
        """.trimIndent()
            .lines()

        assertEquals(3749, part1(input))
    }

    @Test
    fun testPart2() {
        val input = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
        """.trimIndent()
            .lines()

        assertEquals(11387, part1(input))
    }

}