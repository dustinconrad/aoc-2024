package day06

import Coord
import DirectedLineSegment
import readResourceAsBufferedReader
import java.lang.StringBuilder

fun main() {
    println("Part 1: ${part1()}")
    // 422 too low
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

fun segment(input: List<StringBuilder>, initial: Coord, initialDir: Coord): DirectedLineSegment {
        var currSegment = initial
        var segmentDir = initialDir
        // iterate in dir until object or out of bounds
        while(true) {
            val next = (currSegment.first + segmentDir.first) to (currSegment.second + segmentDir.second)
            if ((next.first !in (0..input.lastIndex)) || (next.second !in (0..input[0].lastIndex))) {
                break
            }
            if (input[next.first][next.second] == '#') {
                break
            }
            currSegment = next
        }
        val end = currSegment
        // reverse direction and iterate
        segmentDir = initialDir.first * -1 to initialDir.second * -1
        currSegment = initial
        while(true) {
            val next = (currSegment.first + segmentDir.first) to (currSegment.second + segmentDir.second)
            if ((next.first !in (0..input.lastIndex)) || (next.second !in (0..input[0].lastIndex))) {
                break
            }
            if (input[next.first][next.second] == '#') {
                break
            }
            currSegment = next
        }
        val start = currSegment

        return DirectedLineSegment(
            initialDir, start to end
        )
    }

fun guardPath2(input: List<String>): Set<Pair<Int,Int>> {
    val grid = input.map { StringBuilder(it) }
    var curr = input.mapIndexed { row, line -> row to line.indexOf("^") }
        .first { it.second != -1 }

    val verticalSegments = mutableMapOf<Int,MutableSet<DirectedLineSegment>>()
    val horizontalSegments = mutableMapOf<Int,MutableSet<DirectedLineSegment>>()
    var dir = -1 to 0

    fun addCurrentSegment() {
        // compute the segment we are on
        val currSegment = segment(grid, curr, dir)
        if (dir.first == 0) {
            // horizontal
            horizontalSegments.getOrPut(curr.first) { mutableSetOf() }.add(currSegment)
        } else {
            // vertical
            verticalSegments.getOrPut(curr.second) { mutableSetOf() }.add(currSegment)
        }
    }
    addCurrentSegment()

    val cycles = mutableSetOf<Pair<Int,Int>>()

    while (true) {
        val next = (curr.first + dir.first) to (curr.second + dir.second)

        if ((next.first !in (0..input.lastIndex)) || (next.second !in (0..input[0].lastIndex))) {
            break
        }
        // if theres an obstacle, this is the dir
        val maybeDir = dirs[dir]!!
        if (input[next.first][next.second] == '#') {
            // guess there was an obstacle
            dir = maybeDir
            // compute segment we are on
            addCurrentSegment()
        } else {
            // lets pretend there was an obstacle
            if (maybeDir.first == 0) {
                // get potential horizontal segment we are on
                val alreadyTrodden = horizontalSegments[curr.first] ?: emptySet()
                if (alreadyTrodden.any { ls -> maybeDir == ls.dir && ls.containsCoord(curr) } ) {
                    cycles.add(next)
                }
            } else {
                // get potential vertical segment we are on
                val alreadyTrodden = verticalSegments[curr.second] ?: emptySet()
                if (alreadyTrodden.any { ls -> maybeDir == ls.dir && ls.containsCoord(curr) } ) {
                    cycles.add(next)
                }
            }

            curr = next
        }
    }

    return cycles
}