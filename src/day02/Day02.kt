package day02

import readResourceAsBufferedReader
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("2_1.txt").lines().toList()
    return part1(input)
}

fun part1(lines: List<String>): Int {
    val reports = lines.map { line -> line.split(Regex("\\s+")).map { it.toInt() } }
    return safeReports(reports)
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("2_1.txt").lines().toList()
    return part2(input)
}

fun part2(lines: List<String>): Int {
    val reports = lines.map { line -> line.split(Regex("\\s+")).map { it.toInt() } }
    return safeReports(reports, 1)
}

fun safeReports(reports: List<List<Int>>, dropsLeft: Int = 0): Int {
    return reports.count { isSafe(it, dropsLeft) }
}

fun isSafe(reports: List<Int>, dropsLeft: Int = 0): Boolean {
    // first determine "sign" of reports
    val reportsSign = reports.windowed(2)
        .map { (l, r) -> l - r }
        .groupBy { it.sign }

    val majoritySign = reportsSign.maxBy { (k, v) -> v.size }.key

    if (majoritySign == 0) {
        return false
    }

    for (i in 1 .. reports.lastIndex) {
        val first = reports[i - 1]
        val second = reports[i]
        val diff = first - second

        if (diff.sign != majoritySign || abs(diff) !in 1..3) {
            if (dropsLeft == 0) {
                return false
            } else {
                // try dropping i - 1, or i
                val noIminus1 = reports.toMutableList()
                noIminus1.removeAt(i - 1)
                if (isSafe(noIminus1)) {
                    return true
                }
                val noI = reports.toMutableList()
                noI.removeAt(i)
                return isSafe(noI)
            }
        }
    }

    return true
}