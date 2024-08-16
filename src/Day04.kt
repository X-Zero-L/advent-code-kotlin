fun main() {
    fun IntRange.contains(other: IntRange): Boolean {
        return this.first <= other.first && this.last >= other.last
    }

    fun IntRange.overlaps(other: IntRange): Boolean {
        return this.first <= other.last && other.first <= this.last
    }

    fun Boolean.toInt() = if (this) 1 else 0

    fun processRanges(input: List<String>, check: (IntRange, IntRange) -> Int): Int = input.sumOf {
        it.split(",").map { range ->
            range.split("-").map(String::toInt).let { (start, end) -> start..end }
        }.let { (first, second) -> check(first, second) }
    }

    fun part1(input: List<String>): Int = processRanges(input) { first, second ->
        (first.contains(second) || second.contains(first)).toInt()
    }

    fun part2(input: List<String>): Int = processRanges(input) { first, second ->
        (first.overlaps(second) || second.overlaps(first)).toInt()
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)

    val input = readInput("Day04")
    println(part1(input))

    check(part2(testInput) == 4)
    println(part2(input))
}