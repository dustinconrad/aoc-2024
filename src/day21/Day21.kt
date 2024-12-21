package day21

import readResourceAsBufferedReader

fun main() {
    // 1381 too high, but for someone else
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("21_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>): Int {
    return 1
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("21_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    return 2
}
