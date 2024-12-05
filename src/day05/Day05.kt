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
    val beforeToAfter = beforeToAfterOrdering(orderings.lines())
    val pageUpdates = updates.lines().map { line -> line.split(",").map { it.toInt() } }

    val ordered = pageUpdates.filter { isOrdered(beforeToAfter, it) }

    return ordered
        .sumOf { it[it.size/2] }
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("5_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    val (orderings, updates) = input.byEmptyLines()
    val beforeToAfter = beforeToAfterOrdering(orderings.lines())
    val afterToBefore = afterToBefore(orderings.lines())

    val pageUpdates = updates.lines().map { line -> line.split(",").map { it.toInt() } }

    val unordered = pageUpdates.filter { !isOrdered(beforeToAfter, it) }

    val nowOrdered = unordered.map { orderPages(afterToBefore, beforeToAfter, it) }

    return nowOrdered.sumOf { it[it.size/2] }
}

fun beforeToAfterOrdering(orderings: List<String>): Map<Int, HashSet<Int>> {
    return orderings.map { line ->
        val (l, r) = line.split("|").map { it.toInt() }
        l to r
    }.groupBy { it.first }
        .mapValues { (k, v) -> v.map { it.second }.toHashSet() }
}

fun afterToBefore(orderings: List<String>): Map<Int, HashSet<Int>> {
    return orderings.map { line ->
        val (l, r) = line.split("|").map { it.toInt() }
        r to l
    }.groupBy { it.first }
        .mapValues { (k, v) -> v.map { it.second }.toHashSet() }
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

fun orderPages(afterToBefore: Map<Int,Set<Int>>, beforeToAfter: Map<Int,Set<Int>>, unorderedPages: List<Int>): List<Int> {
    // take each page and get its in-degree
    val currentPages = unorderedPages.toSet()

    val pagesWithInDegree = unorderedPages.associateWith {
        afterToBefore.getOrElse(it) { emptySet() }.intersect(currentPages).toMutableSet()
    }

    val validBeforeToAfter = unorderedPages.associateWith {
        beforeToAfter.getOrElse(it) { emptySet() }.intersect(currentPages)
    }

    val result = mutableListOf<Int>()
    val orderedPages = unorderedPages.toMutableList()

    while (orderedPages.isNotEmpty()) {
        orderedPages.sortBy { pagesWithInDegree[it]?.size ?: 0 }
        val first = orderedPages.removeFirst()
        val validNexts = beforeToAfter[first]
        for (next in validBeforeToAfter[first] ?: emptySet()) {
            pagesWithInDegree[next]?.remove(first)
        }

        result.add(first)
    }

    return result
}