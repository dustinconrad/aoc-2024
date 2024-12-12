package day12

import Coord
import addCoord
import readResourceAsBufferedReader
import java.math.BigInteger

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("12_1.txt").lines().toList()

    return part1(input)
}

fun part1(input: List<String>): Int {
    val regions = regions(input)

    return regions.sumOf { it.price }
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("12_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    val regions = regions(input)

    return regions.sumOf { it.price2 }
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

    val sideCount by lazy {
        // get all top segments
        val topSegments = horizontalSegments(-1 to 0)
        val bottomSegments = horizontalSegments(1 to 0)
        val leftSegments = verticalSegments(0 to -1)
        val rightSegments = verticalSegments(0 to 1)

        topSegments.size + bottomSegments.size + leftSegments.size + rightSegments.size
    }

    val price2 = area * sideCount

    private fun horizontalSegments(dir: Coord): Set<Set<Coord>> {
        val perims = mutableSetOf<Set<Coord>>()
        val coordsWithPerim = coords.filter {
            !coords.contains(it.addCoord(dir))
        }.toMutableSet()

        while (coordsWithPerim.isNotEmpty()) {
            val currLine = mutableSetOf<Coord>()
            val candidate = coordsWithPerim.first()
            coordsWithPerim.remove(candidate)
            currLine.add(candidate)
            // iterate left
            var left = candidate.second - 1
            while (coordsWithPerim.contains(candidate.first to left)) {
                val p = candidate.first to left
                currLine.add(p)
                coordsWithPerim.remove(p)
                left--
            }
            // iterate right
            var right = candidate.second + 1
            while (coordsWithPerim.contains(candidate.first to right)) {
                val p = candidate.first to right
                currLine.add(p)
                coordsWithPerim.remove(p)
                right++
            }
            perims.add(currLine)
        }
        return perims
    }

    private fun verticalSegments(dir: Coord): Set<Set<Coord>> {
        val perims = mutableSetOf<Set<Coord>>()
        val coordsWithPerim = coords.filter {
            !coords.contains(it.addCoord(dir))
        }.toMutableSet()

        while (coordsWithPerim.isNotEmpty()) {
            val currLine = mutableSetOf<Coord>()
            val candidate = coordsWithPerim.first()
            coordsWithPerim.remove(candidate)
            currLine.add(candidate)
            // iterate up
            var up = candidate.first - 1
            while (coordsWithPerim.contains(up to candidate.second)) {
                val p = up to candidate.second
                currLine.add(p)
                coordsWithPerim.remove(p)
                up--
            }
            // iterate down
            var down = candidate.first + 1
            while (coordsWithPerim.contains(down to candidate.second)) {
                val p = down to candidate.second
                currLine.add(p)
                coordsWithPerim.remove(p)
                down++
            }
            perims.add(currLine)
        }
        return perims
    }

}