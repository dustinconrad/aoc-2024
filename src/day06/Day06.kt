package day06

import Coord
import readResourceAsBufferedReader
import kotlin.text.StringBuilder

fun main() {
    println("Part 1: ${part1()}")
    // 422 too low
    // 3989 wrong
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("6_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>): Int {
    val coords = guardPath(input)
    return coords.size
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("6_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    //val obstacles = guardPath2(input)
    return 2
}

val dirs = mapOf(
    (-1 to 0) to (0 to 1),
    (0 to 1) to (1 to 0),
    (1 to 0) to (0 to -1),
    (0 to -1) to (-1 to 0)
)

fun guardPath(input: List<String>): Set<Pair<Int, Int>> {
    var curr = input.mapIndexed { row, line -> row to line.indexOf("^") }
        .first { it.second != -1 }

    val visited = mutableSetOf<Pair<Coord,Coord>>()
    var dir = -1 to 0

    while (true) {
        visited.add(curr to dir)
        val next = (curr.first + dir.first) to (curr.second + dir.second)
        if ((next.first !in (0..input.lastIndex)) || (next.second !in (0..input[0].lastIndex))) {
            break
        }
        if (input[next.first][next.second] == '#') {
            dir = dirs[dir]!!
        } else {
            curr = next
        }
    }

    return visited.map { it.first }.toSet()
}

fun guardPath2(input: List<String>): Set<Pair<Int, Int>> {
    val grid = input.map { StringBuilder(it) }
    var curr = input.mapIndexed { row, line -> row to line.indexOf("^") }
        .first { it.second != -1 }

    val visited = mutableSetOf<Pair<Coord,Coord>>()
    var dir = -1 to 0

    while (true) {
        visited.add(curr to dir)
        val next = (curr.first + dir.first) to (curr.second + dir.second)
        if ((next.first !in (0..input.lastIndex)) || (next.second !in (0..input[0].lastIndex))) {
            break
        }
        if (grid[next.first][next.second] == '#') {
            dir = dirs[dir]!!
        } else {
            curr = next
        }
    }

    return visited.map { it.first }.toSet()
}

//fun maybeCycle(grid: List<StringBuilder>, visited: Set<Pair<Coord,Coord>>, curr: Coord, currDir: Coord,  next: Coord): Boolean {
//    val nextChar = grid[next.first][next.second]
//    if (nextChar != '.' ) {
//        return false
//    }
//    grid[next.first][next.second] = '#'
//
//
//    grid[next.first][next.second] = nextChar
//}
