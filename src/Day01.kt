import java.math.BigInteger

fun main() {
    fun groupAndSumInput(input: List<String>): List<BigInteger> =
        input.fold(mutableListOf(BigInteger.ZERO)) { acc, line ->
            if (line.isBlank()) {
                acc.add(BigInteger.ZERO)
            } else {
                acc[acc.lastIndex] += line.toBigInteger()
            }
            acc
        }

    fun part1(input: List<String>): BigInteger {
        return groupAndSumInput(input).max()
    }

    fun part2(input: List<String>, topn: Int = 3): BigInteger {
        return groupAndSumInput(input).sortedDescending().take(topn).reduce { acc, i -> acc + i }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000.toBigInteger())

    val input = readInput("Day01")
    println(part1(input))

    check(part2(testInput) == 45000.toBigInteger())
    println(part2(input))
}