package day16

import Coord
import addCoord
import dirs
import findInGrid
import readResourceAsBufferedReader

fun main() {
    // 146000 wrong
    // 147628 right
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
//    val scores = traverse(input)
//    val end = findInGrid(input, 'E')
//
//    val min = dirs.map { end to it }
//        .minOf { scores.getOrDefault(it, Int.MAX_VALUE) }
//
//    val q = ArrayDeque<Pair<Coord,Coord>>()
//    val visited = mutableSetOf<Pair<Coord,Coord>>()
//
//    dirs.map { end to it }
//        .filter { scores[it] == min }
//        .forEach {
//            visited.add(it)
//            q.add(it)
//        }
//
//    while(q.isNotEmpty()) {
//        val head = q.removeFirst()
//        val (hCoord, hDir) = head
//
//
//    }


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

        fun maybeAdd(neighborDir: Coord) {
            val neighbor = head.addCoord(neighborDir)
            if (maze[neighbor.first][neighbor.second] != '#') {
                val currentNeighborScore = scores.getOrDefault(neighbor to neighborDir, Int.MAX_VALUE)
                if (currScore + 1 <= currentNeighborScore) {
                    scores[neighbor to neighborDir] = currScore + 1
                    q.add(neighbor to neighborDir)
                }
            }
        }

        fun maybeTurn(neighborDir: Coord) {
            val turned = head to neighborDir
            val currTurnedScore = scores.getOrDefault(turned, Int.MAX_VALUE)
            if (currScore + 1000 <= currTurnedScore) {
                scores[turned] = currScore + 1000
                q.add(turned)
            }
        }

        // find valid neighbors
        // same dir
        maybeAdd(dir)
        // swap
        val swapped = dir.second to dir.first
        maybeTurn(swapped)
        // swap / negate
        val negativeSwapped = dir.second * -1 to dir.first * -1
        maybeTurn(negativeSwapped)
    }

    return scores
}