package day21

import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {

    @Test
    fun test1() {
        val input = """
        5,4
        4,2
        4,5
        3,0
        2,1
        6,3
        2,4
        1,5
        0,6
        3,3
        2,6
        5,1
        1,2
        5,5
        2,5
        6,5
        1,4
        0,4
        6,4
        1,1
        6,1
        1,0
        0,5
        1,6
        2,0
        """.trimIndent().lines().take(12)

        assertEquals(22, part1(input))
    }

}