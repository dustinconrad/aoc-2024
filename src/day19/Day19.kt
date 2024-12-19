package day19

import byEmptyLines
import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("19_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>): Int {
    val (available, targets) = input.byEmptyLines()
    val availableTowels = available.split(",").map { it.trim() }
    val desiredDesigns = targets.split("\n")

    val options = availableTowels.groupBy { it[0] }

    val validDesigns = desiredDesigns.filter { canMake(options, it) }
    return validDesigns.count()
}

fun part2(): Long {
    val input = readResourceAsBufferedReader("19_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Long {
    val (available, targets) = input.byEmptyLines()
    val availableTowels = available.split(",").map { it.trim() }
    val desiredDesigns = targets.split("\n")

    val options = availableTowels.groupBy { it[0] }
    val memo = mutableMapOf<String,Long>()

    val valid = desiredDesigns.filter { canMake(options, it) }

    val ways = valid.map { canMake2(options, memo, it) }

    return ways.sum()
}

fun canMake(options: Map<Char,List<String>>, design: String, startIdx: Int = 0): Boolean {
    if (startIdx == design.length) {
        return true
    }
    // match first letter
    val potential = options[design[startIdx]] ?: emptyList()
    if (potential.isNotEmpty()) {
        for (o in potential) {
            if (design.regionMatches(startIdx, o,0, o.length)) {
                if (canMake(options, design, startIdx + o.length)) {
                    return true
                }
            }
        }

    }

    return false
}

fun canMake2(options: Map<Char,List<String>>, memo: MutableMap<String,Long>, design: String, startIdx: Int = 0): Long {
    if (startIdx == design.length) {
        return 1L
    }
    val targetStr = design.substring(startIdx)
    if (memo.containsKey(targetStr)) {
        return memo[targetStr]!!
    }
    // match first letter
    val potential = options[design[startIdx]] ?: emptyList()
    var ways = 0L
    if (potential.isNotEmpty()) {
        for (o in potential) {
            if (design.regionMatches(startIdx, o,0, o.length)) {
                val rest = canMake2(options, memo, design, startIdx + o.length)
                if (rest != 0L) {
                    ways += rest
                }
            }
        }
    }

    memo[targetStr] = ways

    return ways
}