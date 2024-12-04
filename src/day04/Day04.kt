package day04

import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("4_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>): Int {
    return findWord(input, "XMAS")
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("4_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    val downRight = findDownRight(input, "MAS", 1).toSet()
    val downLeft = findDownLeft(input, "MAS", 1).toSet()

    val exes = downRight.intersect(downLeft)

    return exes.size
}

fun findHorizontal(grid: List<String>, word: String, returnIdx: Int = 0): List<Pair<Int,Int>> {
    val starting = grid.indices.map { it to 0 }
    val lines = iterateGrid(starting, grid.lastIndex, grid[0].lastIndex) { (y, x) -> y to x + 1}

    val foundWords = lines.flatMap { findInLine(grid, it, word, returnIdx) }
    val foundReversed = lines.flatMap { findInLine(grid, it, word.reversed(), returnIdx) }

    return foundWords + foundReversed
}

fun findVertical(grid: List<String>, word: String, returnIdx: Int = 0): List<Pair<Int,Int>> {
    val starting = grid[0].indices.map { 0 to it }
    val lines = iterateGrid(starting, grid.lastIndex, grid[0].lastIndex) { (y, x) -> y + 1 to x}

    val foundWords = lines.flatMap { findInLine(grid, it, word, returnIdx) }
    val foundReversed = lines.flatMap { findInLine(grid, it, word.reversed(), returnIdx) }

    return foundWords + foundReversed
}

fun findDownRight(grid: List<String>, word: String, returnIdx: Int = 0): List<Pair<Int,Int>> {
    val startCol = grid.indices.map { it to 0 }
    val startRow = grid[0].indices.map { 0 to it }

    val starting = (startCol + startRow).toSet()

    val lines = iterateGrid(starting, grid.lastIndex, grid[0].lastIndex) { (y, x) -> y + 1 to x + 1}

    val foundWords = lines.flatMap { findInLine(grid, it, word, returnIdx) }
    val foundReversed = lines.flatMap { findInLine(grid, it, word.reversed(), returnIdx) }

    return foundWords + foundReversed
}

fun findDownLeft(grid: List<String>, word: String, returnIdx: Int = 0): List<Pair<Int,Int>> {
    val startCol = grid.indices.map { it to grid[0].lastIndex }
    val startRow = grid[0].indices.map { 0 to it }

    val starting = (startCol + startRow).toSet()

    val lines = iterateGrid(starting, grid.lastIndex, grid[0].lastIndex) { (y, x) -> y + 1 to x - 1}

    val foundWords = lines.flatMap { findInLine(grid, it, word, returnIdx) }
    val foundReversed = lines.flatMap { findInLine(grid, it, word.reversed(), returnIdx) }

    return foundWords + foundReversed
}

private fun findInLine(grid: List<String>, coords: List<Pair<Int,Int>>, word: String, returnIdx: Int = 0): List<Pair<Int,Int>> {
    return coords.windowed(word.length)
        .filter {
            val letters = it.joinToString("") { (y,x) -> grid[y][x].toString() }
            letters == word
        }.map { it[returnIdx] }
}

private fun iterateGrid(starting: Iterable<Pair<Int,Int>>, maxRow: Int, maxCol: Int, generate: (Pair<Int, Int>) -> Pair<Int, Int>): List<List<Pair<Int,Int>>> {
    fun coords(s: Pair<Int,Int>) = generateSequence(s) { p ->
        val (nextY, nextX) = generate(p)
        if (nextY in 0..maxCol && nextX in 0 .. maxRow) {
            nextY to nextX
        } else {
            null
        }
    }

    return starting.map { coords(it).toList() }
}

fun findWord(grid: List<String>, word: String): Int {
    val horizontal = findHorizontal(grid, word)
    val vertical = findVertical(grid, word)
    val downRight = findDownRight(grid, word)
    val downLeft = findDownLeft(grid, word)

    return horizontal.size + vertical.size + downRight.size + downLeft.size
}