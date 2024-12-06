package day06

import byEmptyLines
import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
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
    return 2
}

fun part2(input: List<String>): Int {
    return 2
}

val dirs = mapOf(
    (-1 to 0) to (0 to 1),
    (0 to 1) to (1 to 0),
    (1 to 0) to (0 to -1),
    (0 to -1) to (-1 to 0)
)

fun guardPath(input: List<String>): Set<Pair<Int,Int>> {
    var curr = input.mapIndexed { row, line -> row to line.indexOf("^") }
        .first { it.second != -1 }

    val result = mutableSetOf<Pair<Int,Int>>()
    var dir = -1 to 0

    while (true) {
        result.add(curr)
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

    return result
}