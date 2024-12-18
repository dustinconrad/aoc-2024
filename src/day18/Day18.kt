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
    }

    return shortestPath(walls, maxY, maxX)[maxY to maxX]!!
}

fun part2(): String {
    val input = readResourceAsBufferedReader("18_1.txt").lines().toList()
    val result = part2(input)
    return "${result.second},${result.first}"
}

fun part2(input: List<String>, maxY: Int = 70, maxX: Int = 70): Coord {
    val walls = input.map {
        val (x, y) = it.split(",").map { n -> n.toInt() }
        y to x
    }

    return shortestPath2(walls, maxY, maxX)
}

fun shortestPath(wall: Iterable<Coord>, maxY: Int = 70, maxX: Int = 70): Map<Coord,Int> {
    val walls = wall.toSet()
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
    return distances
}

fun shortestPath2(allWalls: List<Coord>, maxY: Int = 70, maxX: Int = 70): Coord {
    val start = 0 to 0
    val end = maxY to maxX
    val walls = ArrayDeque(allWalls)

    var counter = 0
    while (true) {
        val result = shortestPath(walls)
        if (result[end] != null) {
            return allWalls[allWalls.size - counter]
        } else {
            walls.removeLast()
            counter++
        }
    }


}