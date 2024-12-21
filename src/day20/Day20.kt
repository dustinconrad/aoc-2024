package day20

import Coord
import addCoord
import dirs
import findInGrid
import manhattanDistance
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

fun part2(): Int {
    val input = readResourceAsBufferedReader("20_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    val shortestPaths = shortestPaths(input)
    val allShortcuts = allShortcuts(input, shortestPaths, 20)

    return allShortcuts.count { it >= 100 }
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

fun shortcut(grid: List<String>, shortestPaths: Map<Coord, Int>, c: Coord, manhattanDistance: Int = 2): List<Int> {
    val currPath = shortestPaths[c]!!

    val candidates = (c.first - manhattanDistance .. c.first + manhattanDistance).flatMap { y ->
        (c.second - manhattanDistance .. c.second + manhattanDistance).map { x -> y to x  } }
        .filter { (y, x) -> y in 0 .. grid.lastIndex && x in 0 .. grid[0].lastIndex }
        .filter { manhattanDistance(c, it) <= manhattanDistance }
        .filter {
            shortestPaths.getOrDefault(it, Int.MAX_VALUE - manhattanDistance) + manhattanDistance(it, c) < currPath
        }

    return candidates
        .map { currPath - (shortestPaths[it]!! + manhattanDistance(it, c)) }
}

fun allShortcuts(grid: List<String>, shortestPaths: Map<Coord, Int>, manhattanDistance: Int = 2): List<Int> {
    return shortestPaths.keys.flatMap { shortcut(grid, shortestPaths, it, manhattanDistance) }
}