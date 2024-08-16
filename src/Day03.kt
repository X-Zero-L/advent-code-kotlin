fun main() {

    fun Char.toValue() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> throw IllegalArgumentException("Invalid input")
    }

    fun findIntersectionValue(sets: List<String>): Int {
        return sets.map { it.toSet() }.reduce { acc, set -> acc.intersect(set) }.first().toValue()
    }

    fun part1(input: List<String>): Int = input.sumOf {
        findIntersectionValue(it.chunked(it.length / 2))
    }

    fun part2(input: List<String>): Int = input.chunked(3).sumOf { chunk ->
        findIntersectionValue(chunk)
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    val input = readInput("Day03")
    println(part1(input))

    check(part2(testInput) == 70)
    println(part2(input))
}