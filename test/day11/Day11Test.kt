package day11

import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {

    @Test
    fun testPart1() {
        val input = "0 1 10 99 999"

        val expected = "1 2024 1 0 9 9 2021976".split(" ").map { BigInteger(it) }
        assertEquals(expected, part1(input, 1))
    }

    @Test
    fun testPart1_1() {
        val input = "125 17"

        val expected = "253000 1 7".split(" ").map { BigInteger(it) }
        assertEquals(expected, part1(input, 1))
    }

    @Test
    fun testPart1_2() {
        val input = "125 17"

        val expected = "253 0 2024 14168".split(" ").map { BigInteger(it) }
        assertEquals(expected, part1(input, 2))
    }

    @Test
    fun testPart1_3() {
        val input = "125 17"

        val expected = "512072 1 20 24 28676032".split(" ").map { BigInteger(it) }
        assertEquals(expected, part1(input, 3))
    }

    @Test
    fun testPart1_4() {
        val input = "125 17"

        val expected = "512 72 2024 2 0 2 4 2867 6032".split(" ").map { BigInteger(it) }
        assertEquals(expected, part1(input, 4))
    }

    @Test
    fun testPart1_5() {
        val input = "125 17"

        val expected = "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32".split(" ").map { BigInteger(it) }
        assertEquals(expected, part1(input, 5))
    }


    @Test
    fun testPart1_6() {
        val input = "125 17"

        val expected = "2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2".split(" ").map { BigInteger(it) }
        assertEquals(expected, part1(input, 6))
    }

    @Test
    fun testPart1_size() {
        val input = "125 17"

        assertEquals(55312, part1(input).size)
    }

}