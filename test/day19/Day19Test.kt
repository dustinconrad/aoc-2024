package day19

import kotlin.test.Test
import kotlin.test.assertEquals

class Day19Test {

    @Test
    fun test1() {
        val input = """
r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb
        """.trimIndent().lines()

        assertEquals(6, part1(input))
    }

    @Test
    fun test2_gbbr() {
        val input = """
r, wr, b, g, bwu, rb, gb, br

gbbr
        """.trimIndent().lines()

        assertEquals(4, part2(input))
    }

    @Test
    fun test2() {
        val input = """
r, wr, b, g, bwu, rb, gb, br

brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb
        """.trimIndent().lines()

        assertEquals(16, part2(input))
    }

}