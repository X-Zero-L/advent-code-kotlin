data class Monkey(
    private val id: Int,
    var items: MutableList<Long> = mutableListOf(),
    val op: List<String>,
    val checkN: Long,
    val left: Int = -1,
    val right: Int = -1,
    var times: Long = 0
)

fun main() {
    fun gcd(a: Long, b: Long): Long = if (b == 0.toLong()) a else gcd(b, a % b)
    fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)
    fun solve(input: String, isWorried: Boolean = true, times: Int = 20): Long {
        val monkeys = mutableListOf<Monkey>()
        fun String.parseLast(): Long = this.trim().split(" ").last().toLong()
        input.split("\n").chunked(7).forEach { chunk ->
            monkeys.add(
                Monkey(
                    monkeys.size,
                    chunk[1].trim().split(" ").drop(2).joinToString(" ").split(", ").map { it.toLong() }
                        .toMutableList(),
                    chunk[2].trim().split(" ").drop(3),
                    chunk[3].parseLast(),
                    chunk[4].parseLast().toInt(),
                    chunk[5].parseLast().toInt()
                )
            )
        }
        repeat(times) {
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeAt(0)
                    fun String.parseToLong(): Long = if (this == "old") item else this.toLong()
                    val a = monkey.op[0].parseToLong()
                    val b = monkey.op[2].parseToLong()
                    val result = when (monkey.op[1]) {
                        "*" -> a * b
                        "+" -> a + b
                        "-" -> a - b
                        else -> throw IllegalArgumentException("Invalid operator")
                    } / (if (isWorried) 3 else 1) % monkeys.map { it.checkN }.reduce { acc, i -> lcm(acc, i) }
                    if (result % monkey.checkN == 0.toLong()) monkeys[monkey.left].items.add(result) else monkeys[monkey.right].items.add(
                        result
                    )
                    monkey.times++
                }
            }
        }
        return monkeys.sortedByDescending { it.times }.take(2).map { it.times }.reduce { acc, i -> acc * i }
    }

    fun part1(input: String): Long = solve(input, true, 20)

    fun part2(input: String): Long = solve(input, false, 10000)

    val testInput = readRawInput("Day11_test")
    check(part1(testInput) == 10605.toLong())

    val input = readRawInput("Day11")
    println(part1(input))

    check(part2(testInput) == 2713310158)
    println(part2(input))
}