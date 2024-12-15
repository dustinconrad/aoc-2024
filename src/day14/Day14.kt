package day14

import Coord
import addCoord
import readResourceAsBufferedReader
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    println("Part 1: ${part1()}")
    part2(0)
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("14_1.txt").lines().toList()

    return part1(input)
}

fun part1(input: List<String>, seconds: Int = 100, maxY: Int = 103, maxX: Int = 101): Int {
    val robots = input.map { Robot.parse(it) }
        .map { it.simulate(seconds, maxY, maxX) }

    return quadrants(robots, maxY, maxX)
        .values
        .reduce { l, r -> l * r }
}

private fun quadrants(robots: List<Robot>,  maxY: Int = 103, maxX: Int = 101): Map<Int,Int> {
    return robots.groupBy { it.quadrant(maxY, maxX) }
        .filter { (k, v) -> k >= 0 }
        .mapValues { it.value.size }
}

fun part2(initial: Int): Long {
    val input = readResourceAsBufferedReader("14_1.txt").lines().toList()
    return part2(input, initial)
}

fun part2(input: List<String>, initial: Int, maxY: Int = 103, maxX: Int = 101): Long {
    var robots = input.map { Robot.parse(it) }

    var i = initial
    while(true) {
        i++
        robots = robots.map { it.simulate(1, maxY, maxX) }
        if (largeObject(robots)) {
            println(i)
            println(visualizeRobots(robots, maxY, maxX))
        }
    }

    return 2
}

fun visualizeRobots(robots: List<Robot>, maxY: Int = 103, maxX: Int = 101): String {
    val r = robots.map { it.p }.toSet()

    return (0 until maxY).joinToString("\n") { y ->
        (0 until maxX).map { x ->
            if (r.contains(y to x)) {
                "#"
            } else {
                "."
            }
        }.joinToString("")
    }
}

private val dirs = listOf(
    (-1 to 0),
    (0 to 1),
    (1 to 0),
    (0 to -1)
)

fun largeObject(robots: List<Robot>): Boolean {
    val remainingRobots = robots.map { it.p }.toMutableSet()

    val groups = mutableSetOf<Set<Coord>>()

    while(remainingRobots.isNotEmpty()) {
        val robot = remainingRobots.first()
        remainingRobots.remove(robot)
        val group = mutableSetOf<Coord>()
        group.add(robot)

        val q = ArrayDeque<Coord>()
        q.add(robot)
        while (q.isNotEmpty()) {
            val curr = q.removeFirst()

            dirs.map { curr.addCoord(it) }
                .filter { remainingRobots.contains(it) }
                .filter { !group.contains(it) }
                .forEach {
                    group.add(it)
                    q.add(it)
                }
        }

        groups.add(group)
    }

    if (groups.any { it.size >= 50 }) {
        return true
    }

    return false
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