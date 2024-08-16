fun main() {

    fun part1(input: List<String>): Int = input.sumOf {
        val (opposite, me) = it.split(" ")
        val oppositeAction = opposite.ParseOpposite()
        when {
            me.toCount() == oppositeAction.toCount() -> Result.DRAW + me.toCount()
            me.toCount() - (oppositeAction.toCount() + 3) % 3 == 1 -> Result.WIN + me.toCount()
            else -> Result.LOSE + me.toCount()
        }
    }

    fun part2(input: List<String>) = input.sumOf {
        val (opposite, me) = it.split(" ")
        val oppositeAction = opposite.ParseOpposite()
        when (me) {
            "X" -> Result.LOSE + oppositeAction.toOpCount()
            "Z" -> Result.WIN + oppositeAction.toWinCount()
            "Y" -> Result.DRAW + oppositeAction.toCount()
            else -> throw IllegalArgumentException("Invalid input")
        }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    val input = readInput("Day02")
    println(part1(input))

    check(part2(testInput) == 12)
    println(part2(input))
}

fun String.toCount(): Int = mapOf(
    "X" to 1,
    "Y" to 2,
    "Z" to 3,
)[this]!!

fun String.toOpCount(): Int = mapOf(
    "X" to "Z",
    "Y" to "X",
    "Z" to "Y",
)[this]!!.toCount()

fun String.toWinCount(): Int = mapOf(
    "X" to "Y",
    "Y" to "Z",
    "Z" to "X",
)[this]!!.toCount()

fun String.ParseOpposite() = mapOf(
    "A" to "X",
    "B" to "Y",
    "C" to "Z"
)[this]!!

object Result {
    val WIN = 6
    val LOSE = 0
    val DRAW = 3
}