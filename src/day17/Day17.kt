package day17

import readResourceAsBufferedReader
import java.lang.IllegalArgumentException
import java.math.BigInteger

fun main() {
    // 7,0,0,1,7,2,2,7,7 wrong
    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}

fun part1(): String {
    val input = readResourceAsBufferedReader("17_1.txt").lines().toList()



    return part1(input)
}

fun part1(input: List<String>): String {
    val digits = Regex("\\d+")
    val a = digits.find(input[0])!!.value.toLong()
    val b = digits.find(input[1])!!.value.toLong()
    val c = digits.find(input[2])!!.value.toLong()
    val (_, p) = input[4].split(":").map { it.trim() }
    val codes = p.split(",").map { it.toInt() }

    val program = Program(a, b, c, codes)

    val end = execute(program)

    return end.out.joinToString(",")
}

fun part2(): Long {
    val input = readResourceAsBufferedReader("17_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Long {
    val digits = Regex("\\d+")
    val a = digits.find(input[0])!!.value.toLong()
    val b = digits.find(input[1])!!.value.toLong()
    val c = digits.find(input[2])!!.value.toLong()
    val (_, p) = input[4].split(":").map { it.trim() }
    val codes = p.split(",").map { it.toInt() }

    val program = Program(a, b, c, codes)

    return guessPart2(program)
}

/**
* A mod 8 -> B
* B xor 1 -> B
* A / 2^B -> C
* B xor 5 -> B
* A / 8 -> A
* B xor C -> B
* B % 8 -> OUT
* repeat if A != 0
 */
fun guessPart2(program: Program): Long {
    var currProgram = program

    var registerValue = 40000000000006
    do {
        currProgram = currProgram.copy(a = registerValue)
        var executed = execute(currProgram)
        println("${registerValue} -> ( ${executed.out.size}) ${executed.out}")
        registerValue = registerValue + 8
    } while (executed.out != executed.instr)

    return registerValue - 1
}

sealed class OpCode {

    abstract fun execute(program: Program): Program

    fun comboOperand(program: Program): Long {
        val operand = program.instr[program.ip + 1].toLong()

        return when(operand) {
            4L -> program.a
            5L -> program.b
            6L -> program.c
            else -> operand
        }
    }

    fun literalOperand(program: Program): Long {
        return program.instr[program.ip + 1].toLong()
    }

    data object Adv : OpCode() {
        override fun execute(program: Program): Program {
            val numerator = program.a
            val denominator = BigInteger.valueOf(2L).pow(comboOperand(program).toInt())
            return program.copy(
                a = numerator / denominator.toLong(),
                ip = program.ip + 2
            )
        }
    }

    data object Bxl : OpCode() {
        override fun execute(program: Program): Program {
            return program.copy(
                b = program.b xor literalOperand(program),
                ip = program.ip + 2
            )
        }
    }

    data object Bst : OpCode() {
        override fun execute(program: Program): Program {
            return program.copy(
                b = comboOperand(program) % 8,
                ip = program.ip + 2
            )
        }
    }

    data object Jnz : OpCode() {
        override fun execute(program: Program): Program {
            return if(program.a == 0L) {
                program.copy(ip = program.ip + 2)
            } else {
                program.copy(ip = literalOperand(program).toInt())
            }
        }
    }

    data object Bxc : OpCode() {
        override fun execute(program: Program): Program {
            return program.copy(
                b = program.b xor program.c,
                ip = program.ip + 2
            )
        }
    }

    data object Out : OpCode() {
        override fun execute(program: Program): Program {
            return program.copy(
                out = program.out + (comboOperand(program) % 8).toInt(),
                ip = program.ip + 2
            )
        }
    }

    data object Bdv : OpCode() {
        override fun execute(program: Program): Program {
            val numerator = program.a
            val denominator = BigInteger.valueOf(2L).pow(comboOperand(program).toInt())
            return program.copy(
                b = numerator / denominator.toLong(),
                ip = program.ip + 2
            )
        }
    }

    data object Cdv : OpCode() {
        override fun execute(program: Program): Program {
            val numerator = program.a
            val denominator = BigInteger.valueOf(2L).pow(comboOperand(program).toInt())
            return program.copy(
                c = numerator / denominator.toLong(),
                ip = program.ip + 2
            )
        }
    }

    companion object {

        fun fromCode(code: Int): OpCode {
            return when(code) {
                0 -> Adv
                1 -> Bxl
                2 -> Bst
                3 -> Jnz
                4 -> Bxc
                5 -> Out
                6 -> Bdv
                7 -> Cdv
                else -> throw IllegalArgumentException("Unrecognized code: $code")
            }
        }

    }

}



data class Program(val a: Long, val b: Long, val c: Long, val instr: List<Int>, val out: List<Int> = emptyList(), val ip: Int = 0) {

    val isHalted = ip > instr.lastIndex

    fun step(): Program {
        val code = instr[ip]
        val op = OpCode.fromCode(code)
        return op.execute(this)
    }

}

fun execute(program: Program): Program {
    var currProgram = program
    while(!currProgram.isHalted) {
        currProgram = currProgram.step()
    }
    return currProgram
}