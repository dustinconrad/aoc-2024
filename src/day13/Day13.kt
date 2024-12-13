package day13

import readResourceAsBufferedReader
import byEmptyLines

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("13_1.txt").lines().toList().byEmptyLines()

    return part1(input)
}

fun part1(input: List<String>): Int {
    return input.map { Machine.parse(it) }
        .filter { it.isPossible }
        .sumOf { it.tokens }
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("13_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {

    return 2
}

data class Button(val x: Int, val y: Int)

data class Machine(val buttonA: Button, val buttonB: Button, val x: Int, val y: Int) {

    private val bNum = y * buttonA.x - buttonA.y * x
    private val bDenom = buttonA.x * buttonB.y - buttonA.y * buttonB.x

    val isPossible = bNum % bDenom == 0

    val bCount = bNum / bDenom

    val aCount = (x - buttonB.x * bCount)/buttonA.x

    val tokens = aCount * 3 + bCount

    companion object {

        fun parse(lines: String): Machine {
            val (a, b, prize) = lines.split("\n")
            val numberPattern = Regex("\\d+")

            val (ax, ay) = numberPattern.findAll(a).toList().map { it.value }.map { it.toInt() }
            val (bx, by) = numberPattern.findAll(b).toList().map { it.value }.map { it.toInt() }
            val (px, py) = numberPattern.findAll(prize).toList().map { it.value }.map { it.toInt() }

            return Machine(
                Button(ax, ay),
                Button(bx, by),
                px, py
            )
        }

    }

}