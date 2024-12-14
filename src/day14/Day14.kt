package day14

import Coord
import readResourceAsBufferedReader
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    println("Part 1: ${part1()}")
    //println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("14_1.txt").lines().toList()

    return part1(input)
}

fun part1(input: List<String>, seconds: Int = 100, maxY: Int = 103, maxX: Int = 101): Int {
    val robots = input.map { Robot.parse(it) }
        .map { it.simulate(seconds, maxY, maxX) }

    return robots.groupBy { it.quadrant(maxY, maxX) }
        .filter { (k, v) -> k >= 0 }
        .map { it.value.size }
        .reduce { l, r -> l * r }
}

fun part2(): Long {
    val input = readResourceAsBufferedReader("14_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Long {
    return 2
}

data class Robot(val p: Coord, val v: Coord) {

    fun simulate(seconds: Int, maxY: Int, maxX: Int): Robot {
        var yDiff = v.first * seconds
        var xDiff = v.second * seconds

        val ySign = yDiff.sign
        val xSign = xDiff.sign

        yDiff = abs(yDiff)
        xDiff = abs(xDiff)

        yDiff = yDiff % maxY
        xDiff = xDiff % maxX

        var newY = p.first + ySign * yDiff
        var newX = p.second + xSign * xDiff

        if (newY >= maxY) {
            newY = newY % maxY
        }

        if (newY < 0) {
            newY = maxY + newY
        }

        if (newX >= maxX) {
            newX = newX % maxX
        }

        if (newX < 0) {
            newX = maxX + newX
        }


        return this.copy(
            p = newY to newX
        )
    }

    fun quadrant(maxY: Int, maxX: Int): Int {
        val halfMaxY = maxY / 2
        val halfMaxX = maxX / 2
        return when {
            p.first < halfMaxY && p.second < halfMaxX -> 0
            p.first > halfMaxY && p.second < halfMaxX -> 1
            p.first < halfMaxY && p.second > halfMaxX -> 2
            p.first > halfMaxY && p.second > halfMaxX -> 3
            else -> -1
        }

    }

    companion object {

        fun parse(line: String): Robot {
            val pairs = Regex("-?\\d+,-?\\d+")
            val (pS, vS) = pairs.findAll(line).map { it.value }.toList()
            val (px, py) = pS.split(",").map { it.toInt() }
            val (vx, vy) = vS.split(",").map { it.toInt() }

            return Robot(py to px, vy to vx)
        }

    }

}