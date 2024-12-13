package day13

import readResourceAsBufferedReader
import byEmptyLines
import java.math.BigInteger

fun main() {
    println("Part 1: ${part1()}")
    // 83341936921125 too high
    // 83029436920891 right
    println("Part 2: ${part2()}")
}

fun part1(): BigInteger {
    val input = readResourceAsBufferedReader("13_1.txt").lines().toList().byEmptyLines()

    return part1(input)
}

fun part1(input: List<String>): BigInteger {
    return input.map { Machine.parse(it) }
        .filter { it.isPossible }
        .sumOf { it.tokens }
}

fun part2(): BigInteger {
    val input = readResourceAsBufferedReader("13_1.txt").lines().toList().byEmptyLines()
    return part2(input)
}

fun part2(input: List<String>): BigInteger {
    val machines =  input.map { Machine.parse2(it) }
        .filter { it.isPossible }

    return machines
        .sumOf { it.tokens }
}

data class Button(val x: BigInteger, val y: BigInteger) {

    constructor(x: Long, y: Long): this(BigInteger.valueOf(x), BigInteger.valueOf(y))
}

data class Machine(val buttonA: Button, val buttonB: Button, val x: BigInteger, val y: BigInteger) {

    constructor(buttonA: Button, buttonB: Button, x: Long, y: Long): this(buttonA, buttonB, BigInteger.valueOf(x), BigInteger.valueOf(y))

    private val det = buttonA.x * buttonB.y - buttonA.y * buttonB.x

    val isPossible = (buttonA.x * y - buttonA.y * x) % det == BigInteger.ZERO && (buttonB.y * x - buttonB.x * y) % det == BigInteger.ZERO

    val bCount = (buttonA.x * y - buttonA.y * x) / det

    val aCount = (buttonB.y * x - buttonB.x * y) / det

    val tokens = aCount * BigInteger.valueOf(3) + bCount

    companion object {

        fun parse(lines: String): Machine {
            val (a, b, prize) = lines.split("\n")
            val numberPattern = Regex("\\d+")

            val (ax, ay) = numberPattern.findAll(a).toList().map { it.value }.map { BigInteger(it) }
            val (bx, by) = numberPattern.findAll(b).toList().map { it.value }.map { BigInteger(it) }
            val (px, py) = numberPattern.findAll(prize).toList().map { it.value }.map { BigInteger(it) }

            return Machine(
                Button(ax, ay),
                Button(bx, by),
                px, py
            )
        }

        fun parse2(lines: String): Machine {
            val machine = parse(lines)
            return machine.copy(x = machine.x + BigInteger.valueOf(10000000000000), y = machine.y + BigInteger.valueOf(10000000000000))
        }


    }

}