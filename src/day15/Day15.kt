package day15

import Coord
import addCoord
import byEmptyLines
import readResourceAsBufferedReader

fun main() {
    // 1490461 too low
    println("Part 1: ${part1()}")
    // 1490546 too low
    println("Part 2: ${part2()}")
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

fun part2(): Int {
    val input = readResourceAsBufferedReader("15_1.txt").lines().toList()

    return part2(input)
}

fun part2(input: List<String>): Int {
    val (coords, moves) = input.byEmptyLines()
    val grid = coords.lines().map { line -> line.map {
        when(it) {
            '#' -> "##"
            'O' -> "[]"
            '.' -> ".."
            '@' -> "@."
            else -> throw IllegalArgumentException()
        } }.joinToString("")
    }.map { StringBuilder(it) }

    processMoves2(grid, moves.filter { it != '\n'})

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

fun processMoves2(grid: List<StringBuilder>, moves: String) {
    val robotCoord = grid.mapIndexed { y, line ->
        val at = line.indexOf("@")
        if (at != -1) {
            y to at
        } else {
            null
        }
    }.filterNotNull()
    .first()

    moves.fold(robotCoord) { coord, move -> processMove2(grid, coord, move) }
}

fun gps(grid: List<StringBuilder>): Int {
    return grid.flatMapIndexed { y, line -> line.mapIndexed { x, c ->
        if (c == 'O' || c == '[') {
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

fun processMove2(grid: List<StringBuilder>, robot: Coord, move: Char): Coord {
    val vec = dirs[move]!!

    fun canMove(c: Coord): Boolean {
        val adjacent = c.addCoord(vec)
        val o = grid[c.first][c.second]
        val isVertical = vec.second == 0
        return when {
            o == '.' -> true
            o == '#' -> false
            isVertical && (o == '[' || o == ']') -> {
                val leftBox: Coord
                val rightBox: Coord
                if (o == '[') {
                    leftBox = c
                    rightBox = c.addCoord(0 to 1)
                } else {
                    leftBox = c.addCoord(0 to -1)
                    rightBox = c
                }
                canMove(leftBox.addCoord(vec)) && canMove(rightBox.addCoord(vec))
            }
            else -> canMove(adjacent)
        }
    }

    fun move(c: Coord) {
        val adjacent = c.addCoord(vec)
        val o = grid[c.first][c.second]
        val isVertical = vec.second == 0
        if (o == '.' || o == '#') {
            return
        }
        if (isVertical && (o == '[' || o == ']')) {
            val leftBox: Coord
            val rightBox: Coord
            if (o == '[') {
                leftBox = c
                rightBox = c.addCoord(0 to 1)
            } else {
                leftBox = c.addCoord(0 to -1)
                rightBox = c
            }
            val leftAdjacent = leftBox.addCoord(vec)
            val rightAdjacent = rightBox.addCoord(vec)
            move(leftAdjacent)
            grid[leftAdjacent.first][leftAdjacent.second] = grid[leftBox.first][leftBox.second]
            grid[leftBox.first][leftBox.second] = '.'

            move(rightAdjacent)
            grid[rightAdjacent.first][rightAdjacent.second] = grid[rightBox.first][rightBox.second]
            grid[rightBox.first][rightBox.second] = '.'
        } else {
            move(adjacent)
            grid[adjacent.first][adjacent.second] = grid[c.first][c.second]
            grid[c.first][c.second] = '.'
        }
    }

    val canMove = canMove(robot)

    return if (canMove) {
        move(robot)
//        println(move)
//        println(grid.joinToString("\n"))
//        println()
        robot.addCoord(vec)
    } else {
//        println(move)
//        println(grid.joinToString("\n"))
//        println()
        robot
    }
}