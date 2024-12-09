package day09

import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
    //println("Part 2: ${part2()}")
}

fun part1(): Long {
    val input = readResourceAsBufferedReader("9_1.txt").readLine()

    return part1(input)
}

fun part1(input: String): Long {
    val disk = toFileParts(input)
    val compacted = compact(disk)

    return checksum(compacted)
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("9_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int{
    return 2
}

sealed class FilePart {

    abstract val id: Int

    object Empty: FilePart() {
        override val id = 0
    }

    data class Data(override val id: Int): FilePart()
}

fun toFileParts(disk: String): List<FilePart>{
    val result = mutableListOf<FilePart>()
    for (i in disk.indices) {
        val digit = disk[i].digitToInt()
        if (i%2 == 0) {
            // data
            repeat(digit) { result.add(FilePart.Data(i / 2)) }
        } else {
            // empty
            repeat(digit) { result.add(FilePart.Empty) }
        }
    }
    return result
}

fun compact(fileparts: List<FilePart>): List<FilePart> {
    val result = fileparts.toMutableList()

    var left = result.indexOfFirst { it is FilePart.Empty }
    var right = result.indexOfLast { it is FilePart.Data }

    while (left < right) {
        val tmp = result[left]
        result[left] = result[right]
        result[right] = tmp

        // increment left
        while(result[left] is FilePart.Data) {
            left++
        }

        // decrement right
        while(result[right] is FilePart.Empty) {
            right--
        }
    }

    val end = result.indexOfFirst { it is FilePart.Empty }

    return result.subList(0, end)
}

fun checksum(fileparts: List<FilePart>): Long {
    return fileparts.mapIndexed { idx, filePart -> idx.toLong() * filePart.id.toLong() }
        .sum()
}