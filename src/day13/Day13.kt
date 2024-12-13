package day13

import readResourceAsBufferedReader
import byEmptyLines

fun main() {
    println("Part 1: ${part1()}")
    // 83341936921125 too high
    println("Part 2: ${part2()}")
}

fun part1(): Long {
    val input = readResourceAsBufferedReader("13_1.txt").lines().toList().byEmptyLines()

    return part1(input)
}

fun part1(input: List<String>): Long {
    return input.map { Machine.parse(it) }
        .filter { it.isPossible }
        .sumOf { it.tokens }
}

fun part2(): Long {
    val input = readResourceAsBufferedReader("13_1.txt").lines().toList().byEmptyLines()
    return part2(input)
}

fun part2(input: List<String>): Long {
    return input.map { Machine.parse2(it) }
        .filter { it.isPossible }
        .sumOf { it.tokens }
}

data class Button(val x: Long, val y: Long)

data class Machine(val buttonA: Button, val buttonB: Button, val x: Long, val y: Long) {

    private val bNum = y * buttonA.x - buttonA.y * x
    private val bDenom = buttonA.x * buttonB.y - buttonA.y * buttonB.x

    val isPossible = bNum % bDenom == 0L

    val bCount = bNum / bDenom

    val aCount = (x - buttonB.x * bCount)/buttonA.x

    val tokens = aCount * 3 + bCount

    companion object {

        fun parse(lines: String): Machine {
            val (a, b, prize) = lines.split("\n")
            val numberPattern = Regex("\\d+")

            val (ax, ay) = numberPattern.findAll(a).toList().map { it.value }.map { it.toLong() }
            val (bx, by) = numberPattern.findAll(b).toList().map { it.value }.map { it.toLong() }
            val (px, py) = numberPattern.findAll(prize).toList().map { it.value }.map { it.toLong() }

            return Machine(
                Button(ax, ay),
                Button(bx, by),
                px, py
            )
        }

        fun parse2(lines: String): Machine {
            val machine = parse(lines)
            return machine.copy(x = machine.x + 10000000000000, y = machine.y + 10000000000000)
        }


    }

}