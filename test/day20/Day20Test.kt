package day20

import kotlin.test.Test
import kotlin.test.assertEquals

class Day20Test {

    @Test
    fun test1_1() {
        val input = """
        ###############
        #...#...#.....#
        #.#.#.#.#.###.#
        #S#...#.#.#...#
        #######.#.#.###
        #######.#.#...#
        #######.#.###.#
        ###..E#...#...#
        ###.#######.###
        #...###...#...#
        #.#####.#.###.#
        #.#...#.#.#...#
        #.#.#.#.#.#.###
        #...#...#...###
        ###############
        """.trimIndent().lines()

        val shortestPaths = shortestPaths(input)
        val coord = 1 to 7
        val shortcuts = shortcut(input, shortestPaths, coord)

        assertEquals(listOf(12), shortcuts)
    }

    @Test
    fun test1_full() {
        val input = """
        ###############
        #...#...#.....#
        #.#.#.#.#.###.#
        #S#...#.#.#...#
        #######.#.#.###
        #######.#.#...#
        #######.#.###.#
        ###..E#...#...#
        ###.#######.###
        #...###...#...#
        #.#####.#.###.#
        #.#...#.#.#...#
        #.#.#.#.#.#.###
        #...#...#...###
        ###############
        """.trimIndent().lines()

        val shortestPaths = shortestPaths(input)
        val allShortcuts = allShortcuts(input, shortestPaths)
            .groupBy { it }
            .mapValues { (k, v) -> v.size }

        assertEquals(mapOf(
            2 to 14,
            4 to 14,
            6 to 2,
            8 to 4,
            10 to 2,
            12 to 3,
            20 to 1,
            36 to 1,
            38 to 1,
            40 to 1,
            64 to 1
        ), allShortcuts)
    }

}