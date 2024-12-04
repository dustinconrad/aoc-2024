package day04

import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
    //println("Part 2: ${part2()}")
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
    return 2
}

fun part2(input: List<String>): Int {
    return 2
}

fun findLtoR(grid: List<String>, word: String): Int {
    return grid.sumOf{ Regex(word).findAll(it).toList().size }
}

fun findRtoL(grid: List<String>, word: String): Int {
    return findLtoR(grid, word.reversed())
}

private fun topToBottom(grid: List<String>): List<String> =
    grid[0].indices.map { col -> grid.joinToString("") { line -> line[col].toString() } }

fun findTtoB(grid: List<String>, word: String): Int {
    return findLtoR(topToBottom(grid), word)
}

fun findBtoT(grid: List<String>, word: String): Int {
    return findRtoL(topToBottom(grid), word)
}

private fun upperLeftToBottomRight(grid: List<String>): List<String> {
    val startCol = grid.indices.map { it to 0 }
    val startRow = grid[0].indices.map { 0 to it }

    fun coords(s: Pair<Int,Int>) = generateSequence(s) { (y, x) ->
        if (y < grid.lastIndex && x < grid[0].lastIndex) {
            y + 1 to x + 1
        } else {
            null
        }
    }

    val allStarts = (startCol + startRow).toSet()

    return allStarts.map { coords(it).toList() }
        .map { line -> line.joinToString("") { (y, x) -> grid[y][x].toString() } }
}

fun findUltoBr(grid: List<String>, word: String): Int {
    return findLtoR(upperLeftToBottomRight(grid), word)
}

fun findBrtoUl(grid: List<String>, word: String): Int {
    return findRtoL(upperLeftToBottomRight(grid), word)
}

private fun upperRightToBottomLeft(grid: List<String>): List<String> {
    val startCol = grid.indices.map { it to grid[0].lastIndex }
    val startRow = grid[0].indices.map { 0 to it }

    fun coords(s: Pair<Int,Int>) = generateSequence(s) { (y, x) ->
        if (y < grid.lastIndex && x > 0) {
            y + 1 to x - 1
        } else {
            null
        }
    }

    val allStarts = (startCol + startRow).toSet()

    return allStarts.map { coords(it).toList() }
        .map { line -> line.joinToString("") { (y, x) -> grid[y][x].toString() } }
}

fun findUrtoBl(grid: List<String>, word: String): Int {
    return findRtoL(upperRightToBottomLeft(grid), word)
}

fun findBltoUr(grid: List<String>, word: String): Int {
    return findLtoR(upperRightToBottomLeft(grid), word)
}

fun findWord(grid: List<String>, word: String): Int {
    var sum = 0;
    sum += findLtoR(grid, word)
    sum += findRtoL(grid, word)

    sum += findTtoB(grid, word)
    sum += findBtoT(grid, word)

    sum += findUltoBr(grid, word)
    sum += findBrtoUl(grid, word)

    sum += findUrtoBl(grid, word)
    sum += findBltoUr(grid, word)

    return sum
}