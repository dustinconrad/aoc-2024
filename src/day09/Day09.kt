package day09

import readResourceAsBufferedReader

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
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

fun part2(): Long {
    val input = readResourceAsBufferedReader("9_1.txt").readLine()
    return part2(input)
}

fun part2(input: String): Long {
    val disk = toFileParts(input)
    val compacted = compact2(disk)

    return checksum(compacted)
}

sealed class FilePart {

    abstract val id: Int
    abstract val length: Int

    data class Empty(override val length: Int): FilePart() {
        override val id = 0
    }

    data class Data(override val id: Int, override val length: Int): FilePart()
}

fun toFileParts(disk: String): List<FilePart>{
    val result = mutableListOf<FilePart>()
    for (i in disk.indices) {
        val digit = disk[i].digitToInt()
        if (i%2 == 0) {
            // data
            repeat(digit) { result.add(FilePart.Data(i / 2, digit)) }
        } else {
            // empty
            repeat(digit) { result.add(FilePart.Empty(digit)) }
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

fun compact2(fileparts: List<FilePart>): List<FilePart> {
    val result = fileparts
        .filter { it.length != 0 }
        .toMutableList()

    var right = result.indexOfLast { it is FilePart.Data }

    fun debug() {
        println(result.joinToString("") {
            if (it is FilePart.Empty) {
                "."
            } else {
                it.id.toString()
            } })
    }

    while (right > 0) {
        val data = result[right]
        var left = 0
        while (left < right) {
            val candidate = result[left]
            if (candidate is FilePart.Empty && candidate.length >= data.length) {
                break
            } else {
                left += candidate.length
            }
        }
        if (left < right) {
            val empty = result[left]
            val diff = empty.length - data.length
            // move
            for (i in 0 until data.length) {
                result[left + i] = result[right - i]
                result[right - i] = FilePart.Empty(data.length)
            }
            // update any leftover empties
            if (diff > 0) {
                for (i in 0 until diff) {
                    result[left + data.length + i] = FilePart.Empty(diff)
                }
            }
        }

        // decrement right
        do {
            val candidate = result[right]
            right -= candidate.length
        } while (right > 0 && result[right] is FilePart.Empty)
    }

    return result
}

fun checksum(fileparts: List<FilePart>): Long {
    return fileparts.mapIndexed { idx, filePart -> idx.toLong() * filePart.id.toLong() }
        .sum()
}