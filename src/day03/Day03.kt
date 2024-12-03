package day03

import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("3_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>): Int {
    return input.flatMap { extractMul(it) }
        .sumOf { it.first * it.second }
}

val MUL_REGEX = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")

fun extractMul(line: String): List<Pair<Int,Int>> {
    return MUL_REGEX.findAll(line)
        .map {
            val s = it.value
            val nums = s.subSequence(4, s.length - 1)
            val (l, r) = nums.split(",").map { n -> n.toInt() }
            l to r
        }
        .toList()
}