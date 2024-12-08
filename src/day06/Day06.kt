package day06

import Coord
import DirectedLineSegment
import readResourceAsBufferedReader
import java.lang.StringBuilder

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

    val result = mutableSetOf<Pair<Int, Int>>()
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
    while (true) {
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
    while (true) {
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

// add the current position/direction as a segment
fun addSegment(
    verticalSegments: MutableMap<Int, MutableSet<DirectedLineSegment>>,
    horizontalSegments: MutableMap<Int, MutableSet<DirectedLineSegment>>,
    currSegment: DirectedLineSegment
) {
    if (currSegment.dir.first == 0) {
        // horizontal
        horizontalSegments.getOrPut(currSegment.segment.first.first) { mutableSetOf() }.add(currSegment)
    } else {
        // vertical
        verticalSegments.getOrPut(currSegment.segment.first.second) { mutableSetOf() }.add(currSegment)
    }
}

fun guardPath2(input: List<String>): Set<Pair<Int, Int>> {
    val grid = input.map { StringBuilder(it) }
    var curr = input.mapIndexed { row, line -> row to line.indexOf("^") }
        .first { it.second != -1 }

    val verticalSegments = mutableMapOf<Int, MutableSet<DirectedLineSegment>>()
    val horizontalSegments = mutableMapOf<Int, MutableSet<DirectedLineSegment>>()
    var dir = -1 to 0

    val currSegment = segment(grid, curr, dir)
    addSegment(verticalSegments, horizontalSegments, currSegment)

    // for a hypothetical placement, check if it ultimately is a cycle
    fun hypotheticalCycle(initial: Coord, initialDir: Coord, next: Coord): Boolean {
        val localVert = verticalSegments.toMutableMap()
        val localHorz = horizontalSegments.toMutableMap()
        // place block
        if (grid[next.first][next.second] != '.') {
            // can't place a block, cant be a cycle
            return false
        }
        grid[next.first][next.second] = '#'

        var hCurr = initial
        var hCurrDir = initialDir
        var cycle = false

        while(true) {
            // we are blocked, by definition
            // turn
            hCurrDir = dirs[hCurrDir]!!
            // get current segment
            val localSegment = segment(grid, hCurr, hCurrDir)
            val (endY, endX) = localSegment.segment.second
            // check if we've been on this before
            if (hCurrDir.first == 0) {
                // get potential horizontal segment we are on
                if (localHorz[hCurr.first]?.contains(localSegment) ?: false) {
                    cycle = true
                    break
                } else if (endX == 0 || endX == grid[0].lastIndex){
                    break
                }
            } else {
                // get potential vertical segment we are on
                if (localVert[hCurr.second]?.contains(localSegment) ?: false) {
                    cycle = true
                    break
                } else if (endY == 0 || endY == grid.lastIndex) {
                    break
                }
            }
            hCurr = localSegment.segment.second

            addSegment(localVert, localHorz, localSegment)
        }

        grid[next.first][next.second] = '.'
        return cycle
    }

    val cycles = mutableSetOf<Pair<Int, Int>>()

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
            val segment = segment(grid, curr, dir)
            addSegment(verticalSegments, horizontalSegments, segment)
        } else {
            // lets pretend there was an obstacle
            if (hypotheticalCycle(curr, dir, next)) {
                cycles.add(next)
            }

            curr = next
        }
    }

    return cycles
}