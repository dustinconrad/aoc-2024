package day02

import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {

    @Test
    fun testPart2() {
        val input = """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9
        """.trimIndent()
            .lines()

        assertEquals(4, part2(input))
    }

}