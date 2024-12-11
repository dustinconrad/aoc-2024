package day11

import readResourceAsBufferedReader
import java.math.BigInteger

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Long {
    val input = readResourceAsBufferedReader("11_1.txt").readLine()

    return part1(input)
}

fun part1(input: String, iterations: Int = 25): Long {
    var initial = input.split(" ").map { BigInteger(it) }
        .map { it to 1L }
        .toMap()

    repeat(iterations) {
        initial = blink2(initial)
    }

    return initial.values.sum()
}

fun part2(): Long {
    val input = readResourceAsBufferedReader("11_1.txt").readLine()
    return part2(input)
}

fun part2(input: String, iterations: Int = 75): Long {
    return part1(input, iterations)
}

fun blink(stone: BigInteger): List<BigInteger> {
    return when {
        stone == BigInteger.ZERO -> listOf(BigInteger.ONE)
        stone.toString().length % 2 == 0 -> {
            val asString = stone.toString()
            val left = asString.substring(0, asString.length/2)
            val right = asString.substring(asString.length/2)
            listOf(BigInteger(left), BigInteger(right))
        }
        else -> listOf(stone.multiply(BigInteger.valueOf(2024)))
    }
}

fun blink2(stones: Map<BigInteger, Long>): Map<BigInteger, Long> {
    return stones.flatMap { (k, v) -> blink(k).map { it to v } }
        .groupBy { it.first }
        .mapValues { (k, v) -> v.sumOf { it.second } }
}