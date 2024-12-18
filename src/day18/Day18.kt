package day18

import Coord
import addCoord
import dirs
import readResourceAsBufferedReader
import java.lang.IllegalArgumentException
import java.math.BigInteger

fun main() {
    // 140 wrong
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("18_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>, maxY: Int = 70, maxX: Int = 70): Int {
    val walls = input.take(1024).map {
        val (x, y) = it.split(",").map { n -> n.toInt() }
        y to x
    }.toSet()

    return shortestPath(walls, maxY, maxX)
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("18_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    return 2
}

fun shortestPath(walls: Set<Coord>, maxY: Int = 70, maxX: Int = 70): Int {

    val start = 0 to 0
    val end = maxY to maxX

    val distances = mutableMapOf<Coord, Int>()
    val q = ArrayDeque<Coord>()
    q.add(start)
    distances[start] = 0

    while (!q.isEmpty()) {
        val curr = q.removeFirst()
        if (curr == end) {
            break
        }
        val currDist = distances[curr]!!
        // fill neighbors
        val validNeighbors = dirs.map { curr.addCoord(it) }
            .filter { it.first in 0 .. maxY && it.second in 0 .. maxX }
            .filter {!distances.containsKey(it) && !walls.contains(it)};

        validNeighbors.forEach {
            q.add(it)
            distances[it] = currDist + 1
        }
    }
    return distances[end]!!
}