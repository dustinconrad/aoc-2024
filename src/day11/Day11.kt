package day11

import readResourceAsBufferedReader
import java.math.BigInteger

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("11_1.txt").readLine()

    return part1(input).size
}

fun part1(input: String, iterations: Int = 25): List<BigInteger> {
    var result = input.split(" ").map { BigInteger(it) }

    repeat(iterations) {
        result = result.flatMap { blink(it) }
    }

    return result
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("11_1.txt").readLine()
    return part1(input, 75).size
}

fun part2(input: List<String>): Int {
    return 2
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