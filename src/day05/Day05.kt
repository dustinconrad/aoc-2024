package day05

import byEmptyLines
import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("5_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>): Int {
    val (orderings, updates) = input.byEmptyLines()
    val pageOrderings = orderings.lines().map { line ->
        val (l, r) = line.split("|").map { it.toInt() }
        l to r
    }.groupBy { it.first }
        .mapValues { (k, v) -> v.map { it.second }.toHashSet() }

    val pageUpdates = updates.lines().map { line -> line.split(",").map { it.toInt() } }

    val ordered = pageUpdates.filter { isOrdered(pageOrderings, it) }

    return ordered
        .sumOf { it[it.size/2] }
}



fun part2(): Int {
    val input = readResourceAsBufferedReader("5_1.txt").lines().toList()
    return 2
}

fun part2(input: List<String>): Int {
    return 2
}

fun isOrdered(pageOrder: Map<Int,Set<Int>>, pages: List<Int>): Boolean {
    val seen = mutableSetOf<Int>()

    for (page in pages) {
        val mustBeBefore = pageOrder.getOrElse(page) { emptySet() }
        if (mustBeBefore.any { seen.contains(it) }) {
            return false
        } else {
            seen.add(page)
        }
    }
    return true
}