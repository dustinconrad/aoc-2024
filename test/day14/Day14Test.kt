package day14

import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {

    @Test
    fun testSimulate1() {
        val input = "p=2,4 v=2,-3"
        val robot = Robot.parse(input)

        val result = robot.simulate(1, 7, 11)

        assertEquals(1 to 4, result.p)
    }

    @Test
    fun testSimulate2() {
        val input = "p=2,4 v=2,-3"
        val robot = Robot.parse(input)

        val result = robot.simulate(2, 7, 11)

        assertEquals(5 to 6, result.p)
    }

    @Test
    fun testSimulate3() {
        val input = "p=2,4 v=2,-3"
        val robot = Robot.parse(input)

        val result = robot.simulate(3, 7, 11)

        assertEquals(2 to 8, result.p)
    }

    @Test
    fun testSimulate4() {
        val input = "p=2,4 v=2,-3"
        val robot = Robot.parse(input)

        val result = robot.simulate(4, 7, 11)

        assertEquals(6 to 10, result.p)
    }


    @Test
    fun testSimulate5() {
        val input = "p=2,4 v=2,-3"
        val robot = Robot.parse(input)

        val result = robot.simulate(5, 7, 11)

        assertEquals(3 to 1, result.p)
    }


    @Test
    fun testPart1() {
        val input = """
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3
        """.trimIndent().lines().toList()

        assertEquals(12, part1(input, 100, 7, 11))
    }
}