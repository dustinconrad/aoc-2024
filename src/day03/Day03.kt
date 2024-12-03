package day03

import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("3_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>): Int {
    return input.flatMap { extractMul(it) }
        .sumOf { it.first * it.second }
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("3_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    return extractMul2(input.joinToString("\n"))
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

val MUL_REGEX2 = Regex("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)")

fun extractMul2(line: String): List<Pair<Int,Int>> {
    var enabled = true
    val pairs = mutableListOf<Pair<Int,Int>>()
    for (m in MUL_REGEX2.findAll(line)) {
        val s = m.value
        when {
            s.length == 4 -> {
                enabled = true
            }
            s.length == 7 && s.startsWith("d") -> {
                enabled = false
            }
            else -> {
                if (enabled) {
                    val nums = s.subSequence(4, s.length - 1)
                    val (l, r) = nums.split(",").map { n -> n.toInt() }
                    pairs.add(l to r)
                }
            }
        }
    }
    return pairs
}