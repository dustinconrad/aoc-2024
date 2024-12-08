package day06

import Coord
import readResourceAsBufferedReader
import kotlin.text.StringBuilder

fun main() {
    println("Part 1: ${part1()}")
    // 422 too low
    // 3989 wrong
    // 2037 wrong
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
    val obstacles = guardPath2(input)
    return obstacles.size
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
    val cycles = mutableSetOf<Coord>()

    while (true) {
        visited.add(curr to dir)
        val next = (curr.first + dir.first) to (curr.second + dir.second)
        if ((next.first !in (0..input.lastIndex)) || (next.second !in (0..input[0].lastIndex))) {
            break
        }
        if (grid[next.first][next.second] == '#') {
            dir = dirs[dir]!!
        } else {
            if(maybeCycle(grid, visited, curr, dir, next)) {
                cycles.add(next)
            }
            curr = next
        }
    }

    return cycles
}

fun maybeCycle(grid: List<StringBuilder>, initialVisited: Set<Pair<Coord,Coord>>, initialCurr: Coord, initialDir: Coord, initialNext: Coord): Boolean {
    val nextChar = grid[initialNext.first][initialNext.second]
    if (nextChar != '.') {
        return false
    }
    if (dirs.keys.map { initialNext to it }
        .any { initialVisited.contains(it) }) {
        return false
    }
    grid[initialNext.first][initialNext.second] = '#'
    val visited = initialVisited.toMutableSet()
    var curr = initialCurr
    var dir = dirs[initialDir]!!

    var cycle = false

    while (true) {
        if (visited.contains(curr to dir)) {
            cycle = true
            break
        }
        visited.add(curr to dir)
        val next = (curr.first + dir.first) to (curr.second + dir.second)
        if ((next.first !in (0..grid.lastIndex)) || (next.second !in (0..grid[0].lastIndex))) {
            break
        }
        if (grid[next.first][next.second] == '#') {
            dir = dirs[dir]!!
        } else {
            curr = next
        }
    }

    grid[initialNext.first][initialNext.second] = nextChar
    return cycle
}
