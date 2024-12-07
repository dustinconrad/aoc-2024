package day07

import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Long {
    val input = readResourceAsBufferedReader("7_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>): Long {
    return input.map { Equation.parse(it) }
        .filter { it.isPossible() }
        .sumOf { it.result }
}

fun part2(): Long {
    val input = readResourceAsBufferedReader("7_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Long {
    return input.map { Equation.parse(it) }
        .filter { it.isPossible2() }
        .sumOf { it.result }
}

data class Equation(val result: Long, val numbers: List<Long>) {

    fun isPossible(): Boolean {
        val possibilities = numbers.fold(mutableListOf<Long>()) { acc, n ->
            if (acc.isEmpty()) {
                acc.add(n)
                acc
            } else {
                acc.flatMap { listOf(it * n, it + n) }
                    .filter { it <= result }
                    .toMutableList()
            }
        }

        return possibilities.any { it == result }
    }

    fun isPossible2(): Boolean {
        val possibilities = numbers.fold(mutableListOf<Long>()) { acc, n ->
            if (acc.isEmpty()) {
                acc.add(n)
                acc
            } else {
                acc.flatMap { listOf(it * n, it + n, (it.toString() + n.toString()).toLong() ) }
                    .filter { it <= result }
                    .toMutableList()
            }
        }

        return possibilities.any { it == result }
    }

    companion object {

        fun parse(line: String): Equation {
            val (result, numbers) = line.split(Regex(":\\s+"))
            val resultNum = result.toLong()

            val numbersNums = numbers.split(Regex("\\s+")).map { it.trim().toLong() }

            return Equation(resultNum, numbersNums)
        }

    }
}