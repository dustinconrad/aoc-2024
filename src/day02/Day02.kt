package day02

import readResourceAsBufferedReader
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    println("Part 1: ${part1()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("2_1.txt").lines().toList()
    return part1(input)
}

fun part1(lines: List<String>): Int {
    val reports = lines.map { line -> line.split(Regex("\\s+")).map { it.toInt() } }
    return safeReports(reports)
}

fun safeReports(reports: List<List<Int>>): Int {
    return reports.count { isSafe(it) }
}

fun isSafe(reports: List<Int>): Boolean {

    val initialDiff = reports[0] - reports[1]
    if (abs(initialDiff) !in 1..3) {
        return false
    }
    val initialSign = initialDiff.sign
    for (i in 2 .. reports.lastIndex) {
        val first = reports[i - 1]
        val second = reports[i]
        val diff = first - second
        if (diff.sign != initialSign || abs(diff) !in 1..3) {
            return false
        }
    }
    return true
}