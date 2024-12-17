package day17

import readResourceAsBufferedReader
import java.lang.IllegalArgumentException

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
    val a = digits.find(input[0])!!.value.toInt()
    val b = digits.find(input[1])!!.value.toInt()
    val c = digits.find(input[2])!!.value.toInt()
    val (_, p) = input[4].split(":").map { it.trim() }
    val codes = p.split(",").map { it.toInt() }

    val program = Program(a, b, c, codes)

    val end = execute(program)

    return end.out.joinToString(",")
}

fun part2(): Int {
    val input = readResourceAsBufferedReader("17_1.txt").lines().toList()
    return part2(input)
}

fun part2(input: List<String>): Int {
    return 2
}

sealed class OpCode {

    abstract fun execute(program: Program): Program

    fun comboOperand(program: Program): Int {
        val operand = program.instr[program.ip + 1]

        return when(operand) {
            4 -> program.a
            5 -> program.b
            6 -> program.c
            else -> operand
        }
    }

    fun literalOperand(program: Program): Int {
        return program.instr[program.ip + 1]
    }

    data object Adv : OpCode() {
        override fun execute(program: Program): Program {
            val numerator = program.a
            val denominator = 1.shl(comboOperand(program))
            return program.copy(
                a = numerator / denominator,
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
            return if(program.a == 0) {
                program.copy(ip = program.ip + 2)
            } else {
                program.copy(ip = literalOperand(program))
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
                out = program.out + comboOperand(program) % 8,
                ip = program.ip + 2
            )
        }
    }

    data object Bdv : OpCode() {
        override fun execute(program: Program): Program {
            val numerator = program.a
            val denominator = 2.shl(comboOperand(program))
            return program.copy(
                b = numerator / denominator,
                ip = program.ip + 2
            )
        }
    }

    data object Cdv : OpCode() {
        override fun execute(program: Program): Program {
            val numerator = program.a
            val denominator = 2.shl(comboOperand(program))
            return program.copy(
                c = numerator / denominator,
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



data class Program(val a: Int, val b: Int, val c: Int, val instr: List<Int>, val out: List<Int> = emptyList(), val ip: Int = 0) {

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