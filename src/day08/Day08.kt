package day08

import Coord
import addCoord
import getCartesianProduct
import readResourceAsBufferedReader
import subtract

fun main() {
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): Int {
    val input = readResourceAsBufferedReader("8_1.txt").lines().toList()
    return part1(input)
}

fun part1(input: List<String>): Int {
    val antennas = antennas(input)
    val maxY = input.lastIndex
    val maxX = input[0].lastIndex
    val nodes = antennas.flatMap { antinodes(it.value) }
        .toSet()
        .filter { (y, x) -> y in 0 .. maxY && x in 0 .. maxX }

    return nodes.size

}

fun part2(): Int {
    val input = readResourceAsBufferedReader("8_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int{
    val antennas = antennas(input)
    val maxY = input.lastIndex
    val maxX = input[0].lastIndex
    val nodes = antennas.flatMap { antinodes2(it.value, maxY, maxX) }
        .toSet()
        .filter { (y, x) -> y in 0 .. maxY && x in 0 .. maxX }

    return nodes.size
}

fun antennas(input: List<String>): Map<Char,List<Coord>> {
    return input.flatMapIndexed {  y, line  ->
        line.mapIndexed { x , c ->
            if (c.isLetter() || c.isDigit()) {
                c to (y to x)
            } else {
                null
            }
        }.filterNotNull()
    }.groupBy({ p -> p.first }) { it.second }
}

fun antinodes(nodes: Iterable<Coord>): Set<Coord> {
    val coords = listOf(nodes, nodes)
    val pairs = coords.getCartesianProduct()
        .filter { (l, r) -> l != r}

    return pairs.flatMap { (l, r) ->
        val diff = l.subtract(r)
        val anti1 = l.addCoord(diff)
        val anti2 = r.subtract(diff)
        listOf(anti1, anti2)
    }
        .toSet()
}

fun antinodes2(nodes: Iterable<Coord>, maxY: Int, maxX: Int): Set<Coord> {
    val coords = listOf(nodes, nodes)
    val pairs = coords.getCartesianProduct()
        .filter { (l, r) -> l != r}

    return pairs.flatMap { (l, r) ->
        val diff = l.subtract(r)
        val result = mutableSetOf<Coord>()
        var curr = l
        while (curr.first in 0 .. maxY && curr.second in 0 .. maxX) {
            result.add(curr)
            curr = curr.addCoord(diff)
        }
        curr = r
        while (curr.first in 0 .. maxY && curr.second in 0 .. maxX) {
            result.add(curr)
            curr = curr.subtract(diff)
        }

        val anti1 = l.addCoord(diff)
        val anti2 = r.subtract(diff)
        result
    }
        .toSet()
}