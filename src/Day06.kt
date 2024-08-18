fun main() {

    fun String.solve(distinct: Int = 4): Int =
        windowedSequence(distinct) { it.toSet().size == distinct }.indexOf(true) + distinct

    fun part1(input: String): Int = input.solve(4)

    fun part2(input: String): Int = input.solve(14)

    val testInput = readRawInput("Day06_test")
    check(part1(testInput) == 6)

    val input = readRawInput("Day06")
    println(part1(input))

    check(part2(testInput) == 23)
    println(part2(input))
}