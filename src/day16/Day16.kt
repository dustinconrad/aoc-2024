package day16

import Coord
import addCoord
import dirs
import findInGrid
import readResourceAsBufferedReader

fun main() {
    // 146000 wrong
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("16_1.txt").lines().toList()

    return part1(input)
}

fun part1(input: List<String>): Int {
    val scores = traverse(input)
    val end = findInGrid(input, 'E')
    return dirs.map { end to it }
        .minOf { scores.getOrDefault(it, Int.MAX_VALUE) }
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("16_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {

    return 2
}

fun traverse(maze: List<String>): Map<Pair<Coord,Coord>,Int> {
    val start = findInGrid(maze, 'S')

    val scores = mutableMapOf<Pair<Coord,Coord>,Int>()
    scores[start to (0 to 1)] = 0

    val q = ArrayDeque<Pair<Coord,Coord>>()
    q.add(start to (0 to 1))

    while(q.isNotEmpty()) {
        val first = q.removeFirst()
        val (head, dir ) = first
        val currScore = scores[first]!!

        fun maybeAdd(neighborDir: Coord, score: Int = 0) {
            val neighbor = head.addCoord(neighborDir)
            if (maze[neighbor.first][neighbor.second] != '#') {
                val currentNeighborScore = scores.getOrDefault(neighbor to neighborDir, Int.MAX_VALUE)
                if (currScore + score + 1 < currentNeighborScore) {
                    scores[neighbor to neighborDir] = currScore + score + 1
                    q.add(neighbor to neighborDir)
                }
            }
        }

        // find valid neighbors
        // same dir
        maybeAdd(dir)
        // opposite
        val opposite = dir.first * -1 to dir.second * -1
        maybeAdd(opposite, 2 * 1000)
        // swap
        val swapped = dir.second to dir.first
        maybeAdd(swapped, 1000)
        // swap / negate
        val negativeSwapped = dir.second * -1 to dir.first * -1
        maybeAdd(negativeSwapped, 1000)
    }

    return scores
}