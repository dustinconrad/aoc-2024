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

fun part2(): Int {
    val input = readResourceAsBufferedReader("19_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    val (available, targets) = input.byEmptyLines()
    val availableTowels = available.split(",").map { it.trim() }
    val desiredDesigns = targets.split("\n")

    val options = availableTowels.groupBy { it[0] }

    val ways = desiredDesigns.map { canMake2(options, it) }

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

fun canMake2(options: Map<Char,List<String>>, design: String, startIdx: Int = 0): Int {
    if (startIdx == design.length) {
        return 1
    }
    // match first letter
    val potential = options[design[startIdx]] ?: emptyList()
    var ways = 0
    if (potential.isNotEmpty()) {
        for (o in potential) {
            if (design.regionMatches(startIdx, o,0, o.length)) {
                val rest = canMake2(options, design, startIdx + o.length)
                if (rest != 0) {
                    ways += rest
                }
            }
        }
    }

    return ways
}