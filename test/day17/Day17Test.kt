package day17

import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {

    @Test
    fun test1_1() {
        val program = Program(0,0,9, listOf(2,6))
        val result  = program.step()

        assertEquals(result.b, 1)
    }

    @Test
    fun test1_2() {
        val input = """
        Register A: 10
        Register B: 0
        Register C: 0

        Program: 5,0,5,1,5,4
        """.trimIndent().lines()

        assertEquals("0,1,2", part1(input))
    }

    @Test
    fun test1_3() {
        val input = """
        Register A: 2024
        Register B: 0
        Register C: 0

        Program: 0,1,5,4,3,0
        """.trimIndent().lines()

        assertEquals("4,2,5,6,7,7,7,7,3,1,0", part1(input))
    }

    @Test
    fun test1_4() {
        val program = Program(0,29,0, listOf(1,7))
        val result  = program.step()

        assertEquals(result.b, 26)
    }

    @Test
    fun test1_5() {
        val program = Program(0,2024,43690, listOf(4,0))
        val result  = program.step()

        assertEquals(result.b, 44354)
    }

    @Test
    fun test1_example() {
        val input = """
        Register A: 729
        Register B: 0
        Register C: 0

        Program: 0,1,5,4,3,0
        """.trimIndent().lines()

        assertEquals("4,6,3,5,6,3,5,2,1,0", part1(input))
    }



}