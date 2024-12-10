package day10

import Coord
import addCoord
import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("10_1.txt").lines().toList()

    return part1(input)
}

fun part1(input: List<String>): Int {
    return scoreTrailheads(input)
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("10_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    return rateTrailheads(input)
}

fun scoreTrailheads(input: List<String>): Int {
    val dp = input.map { Array<MutableSet<Coord>>(input[0].length) { mutableSetOf() } }

    val q = ArrayDeque<Coord>()
    val nines = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if(c == '9') {
                y to x
            } else {
                null
            }
        }.filterNotNull()
    }
    nines.forEach { curr ->
        val (currY, currX) = curr
        dp[currY][currX].add(curr)
        // add to queue
        listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
            .map { curr.addCoord(it) }
            .filter { (y, x) -> y in 0 .. input.lastIndex && x in 0 .. input[0].lastIndex }
            .filter { (y, x) -> input[y][x].isDigit() && input[y][x].digitToInt() == (input[currY][currX]).digitToInt() - 1}
            .forEach { q.add(it) }
    }

    while (q.isNotEmpty()) {
        val curr = q.removeFirst()
        val (currY, currX) = curr
        val currHeight = input[currY][currX].digitToInt()

        val validNeighbors = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
            .map { curr.addCoord(it) }
            .filter { (y, x) -> y in 0 .. input.lastIndex && x in 0 .. input[0].lastIndex }
            .groupBy { (y, x) -> if (input[y][x].isDigit()) {
                    input[y][x].digitToInt()
                } else {
                    -2
                }
            }

        // compute current score
        if (validNeighbors.containsKey(currHeight + 1)) {
            validNeighbors[currHeight + 1]!!.forEach { (y, x) ->
                dp[currY][currX].addAll(dp[y][x])
            }
        }
        // q neighbors
        validNeighbors[currHeight - 1]?.forEach {
            q.add(it)
        }
    }

    val zeroes = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if(c == '0') {
                y to x
            } else {
                null
            }
        }.filterNotNull()
    }

    return zeroes.sumOf { (y, x) -> dp[y][x].size }
}

fun rateTrailheads(input: List<String>): Int {
    val dp = input.map { IntArray(input[0].length) }

    val q = ArrayDeque<Coord>()
    val nines = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if(c == '9') {
                y to x
            } else {
                null
            }
        }.filterNotNull()
    }
    nines.forEach { curr ->
        val (currY, currX) = curr
        dp[currY][currX] = 1
        // add to queue
        listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
            .map { curr.addCoord(it) }
            .filter { (y, x) -> y in 0 .. input.lastIndex && x in 0 .. input[0].lastIndex }
            .filter { (y, x) -> input[y][x].isDigit() && input[y][x].digitToInt() == (input[currY][currX]).digitToInt() - 1}
            .forEach { q.add(it) }
    }

    while (q.isNotEmpty()) {
        val curr = q.removeFirst()
        val (currY, currX) = curr
        val currHeight = input[currY][currX].digitToInt()

        val validNeighbors = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
            .map { curr.addCoord(it) }
            .filter { (y, x) -> y in 0 .. input.lastIndex && x in 0 .. input[0].lastIndex }
            .groupBy { (y, x) -> if (input[y][x].isDigit()) {
                    input[y][x].digitToInt()
                } else {
                    -2
                }
            }

        // compute current score
        if (validNeighbors.containsKey(currHeight + 1)) {
            dp[currY][currX] = validNeighbors[currHeight + 1]!!.sumOf { (y, x) -> dp[y][x] }
        }
        // q neighbors
        validNeighbors[currHeight - 1]?.forEach {
            q.add(it)
        }
    }

    val zeroes = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, c ->
            if(c == '0') {
                y to x
            } else {
                null
            }
        }.filterNotNull()
    }

    return zeroes.sumOf { (y, x) -> dp[y][x] }
}