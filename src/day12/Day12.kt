package day12

import Coord
import addCoord
import readResourceAsBufferedReader
import java.math.BigInteger

fun main() {
    println("Part 1: ${part1()}")
    //println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("12_1.txt").lines().toList()

    return part1(input)
}

fun part1(input: List<String>): Int {
    val regions = regions(input)

    return regions.sumOf { it.price }
}

fun part2(): Long {
    val input = readResourceAsBufferedReader("12_1.txt").readLine()
    return part2(input)
}

fun part2(input: String, iterations: Int = 75): Long {
    return 2
}

fun regions(input: List<String>): Set<Region> {
    val allCoords = input.flatMapIndexed { y, line -> line.indices.map { y to it } }
        .toMutableSet()

    val result = mutableSetOf<Region>()
    val seen = mutableSetOf<Coord>()

    //println("total coords: ${allCoords.size}")
    while (allCoords.isNotEmpty()) {
        val start = allCoords.first()
        val nextRegion = nextRegion(input, seen, start)
        result.add(nextRegion)
        //println("Regions: ${result.size}")
        seen.addAll(nextRegion.coords)
        allCoords.removeAll(nextRegion.coords)
        //println("coords left: ${allCoords.size}")
    }
    return result
}

private val dirs = listOf(
    (-1 to 0),
    (0 to 1),
    (1 to 0),
    (0 to -1)
)

private fun nextRegion(input: List<String>, seen: Set<Coord>, start: Coord): Region {
    val visited = mutableSetOf<Coord>()

    val currChar = input[start.first][start.second]

    val q = ArrayDeque<Coord>()
    q.add(start)
    visited.add(start)

    while (q.isNotEmpty()) {
        val head = q.removeFirst()
        val (hy, hx) = head

        dirs.map { head.addCoord(it) }
            .filter { (y, x) -> y in 0 .. input.lastIndex && x in 0 .. input[0].lastIndex }
            .filter { (y, x) -> input[y][x] == currChar }
            .filter { !visited.contains(it) }
            .forEach {
                q.add(it)
                visited.add(it)
            }
    }

    return Region(visited)
}

data class Region(val coords: Set<Coord>) {
    val area = coords.size

    val perimeter by lazy {
        coords.sumOf { p ->
            dirs.map { d -> p.addCoord(d) }
                .count { !coords.contains(it) }
        }
    }

    val price = area * perimeter
}