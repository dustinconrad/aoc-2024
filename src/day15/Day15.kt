package day15

import Coord
import addCoord
import byEmptyLines
import readResourceAsBufferedReader

fun main() {
    // 1490461 too low
    println("Part 1: ${part1()}")
    //println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("15_1.txt").lines().toList()

    return part1(input)
}

fun part1(input: List<String>): Int {
    val (coords, moves) = input.byEmptyLines()
    val grid = coords.lines().map { StringBuilder(it) }

    processMoves(grid, moves.filter { it != '\n'})

    //println(grid.joinToString("\n"))

    return gps(grid)
}



fun processMoves(grid: List<StringBuilder>, moves: String) {
    val robotCoord = grid.mapIndexed { y, line ->
        val at = line.indexOf("@")
        if (at != -1) {
            y to at
        } else {
            null
        }
    }.filterNotNull()
    .first()

    moves.fold(robotCoord) { coord, move -> processMove(grid, coord, move) }
}

fun gps(grid: List<StringBuilder>): Int {
    return grid.flatMapIndexed { y, line -> line.mapIndexed { x, c ->
        if (c == 'O') {
            y * 100 + x
        } else {
            null
        }
    }
    }.filterNotNull()
        .sum()
}

val dirs = mapOf(
    '>' to (0 to 1),
    'v' to (1 to 0),
    '<' to (0 to -1),
    '^' to (-1 to 0)
)

fun processMove(grid: List<StringBuilder>, robot: Coord, move: Char): Coord {
    val vec = dirs[move]!!

    fun move(c: Coord): Boolean {
        val adjacent = c.addCoord(vec)
        val o = grid[c.first][c.second]
        return when(o) {
            '.' -> true
            '#' -> false
            else -> {
                val result = move(adjacent)
                if (result) {
                    grid[adjacent.first][adjacent.second] = grid[c.first][c.second]
                    grid[c.first][c.second] = '.'
                }
                result
            }
        }
    }

    return if (move(robot)) {
        robot.addCoord(vec)
    } else {
        robot
    }
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("15_1.txt").lines().toList()

    return part2(input)
}

fun part2(input: List<String>): Int {
    return 2
}

