package day20

import Coord
import addCoord
import dirs
import findInGrid
import readResourceAsBufferedReader

fun main() {
    // 1381 too high, but for someone else
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("20_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>): Int {
    val shortestPaths = shortestPaths(input)
    val allShortcuts = allShortcuts(input, shortestPaths)

    return allShortcuts.count { it >= 100 }
}

fun part2(): Long {
    val input = readResourceAsBufferedReader("20_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Long {
    return 2
}

fun shortestPaths(grid: List<String>): Map<Coord, Int> {
    val end = findInGrid(grid, 'E')
    val results = mutableMapOf<Coord,Int>()

    var curr: Coord? = end
    var dist = 0

    while(curr != null) {
        results[curr] = dist++

        curr = dirs.map { curr!!.addCoord(it) }
            .filterNot { results.contains(it) }
            .filter { (y, x) -> y in 0 .. grid.lastIndex && x in 0 .. grid[0].lastIndex }
            .filter { (y, x) -> grid[y][x] != '#' }
            .firstOrNull()
    }

    return results
}

fun shortcut(grid: List<String>, shortestPaths: Map<Coord, Int>, c: Coord): List<Int> {
    val currPath = shortestPaths[c]!!

    val candidates = dirs.map { c.addCoord(it).addCoord(it) }
        .filter { (y, x) -> y in 0 .. grid.lastIndex && x in 0 .. grid[0].lastIndex }
        .filter { shortestPaths.getOrDefault(it, Int.MAX_VALUE - 2) + 2 < currPath }

    return candidates
        .map { currPath - (shortestPaths[it]!! + 2) }
}

fun allShortcuts(grid: List<String>, shortestPaths: Map<Coord, Int>): List<Int> {
    return shortestPaths.keys.flatMap { shortcut(grid, shortestPaths, it) }
}