package day01

import readInput
import readResourceAsBufferedReader
import kotlin.math.abs

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("1_1.txt").lines().toList()
    return part1(input)
}

fun part1(lines: List<String>): Int {
    val parsedInput = split(lines)
    return distanceDiff(parsedInput.first, parsedInput.second)
}

fun part2(lines: List<String>): Int {
    val (left, right) = split(lines)
    val lookup = right.groupBy { it }.mapValues { (k, v) -> v.size * k }
    return left.sumOf { lookup.getOrDefault(it, 0) }
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("1_1.txt").lines().toList()
    return part2(input)
}

fun split(numbers: List<String>): Pair<List<Int>, List<Int>> {
    val pairs = numbers
        .map {line -> line.split(Regex("\\s+")).map { it.toInt() } }
        .foldRight(mutableListOf<Int>() to mutableListOf<Int>()) { (l, r), (lAcc, rAcc) ->
            lAcc.add(l)
            rAcc.add(r)
            lAcc to rAcc
        }

    return pairs
}

fun distanceDiff(left: List<Int>, right: List<Int>): Int {
    val leftSorted = left.sorted()
    val rightSorted = right.sorted()

    return leftSorted.zip(rightSorted)
        .sumOf { (l, r) -> abs(l - r) }
}
