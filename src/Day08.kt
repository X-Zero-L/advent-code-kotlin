import kotlin.math.abs

fun main() {

    val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun isHighest(i: Int, j: Int, input: List<String>, direction: Pair<Int, Int>): Boolean {
        var (x, y) = i + direction.first to j + direction.second
        while (true) {
            if (x !in input.indices || y !in 0 until input[0].length) return true
            if (input[x][y] >= input[i][j]) return false
            x += direction.first
            y += direction.second
        }
    }

    fun getTreeCount(i: Int, j: Int, input: List<String>, direction: Pair<Int, Int>): Int {
        var (x, y) = i + direction.first to j + direction.second
        while (true) {
            if (x !in input.indices || y !in 0 until input[0].length) return if (direction.second == 0) abs(x - i) - 1 else abs(
                y - j
            ) - 1
            if (input[x][y] >= input[i][j]) return if (direction.second == 0) abs(x - i) else abs(y - j)
            x += direction.first
            y += direction.second
        }
    }

    fun solve(input: List<String>, actionFunction: (Int, Int, MutableList<MutableList<Int>>) -> Unit): List<List<Int>> {
        val (n, m) = (input.size to input[0].length)
        val st = MutableList(n) { MutableList(m) { 0 } }
        for (i in 0 until n) {
            for (j in 0 until m) {
                actionFunction(i, j, st)
            }
        }
        return st
    }

    fun part1(input: List<String>): Int = solve(input) { i, j, st ->
        if (directions.any { (dx, dy) ->
                isHighest(i, j, input, dx to dy)
            }) {
            st[i][j] = 1
        }
    }.sumOf { it.sum() }

    fun part2(input: List<String>): Int = solve(input) { i, j, st ->
        st[i][j] = 1
        directions.forEach { (dx, dy) ->
            st[i][j] *= getTreeCount(i, j, input, dx to dy)
        }
    }.maxOf { it.maxOrNull() ?: 0 }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)

    val input = readInput("Day08")
    println(part1(input))

    check(part2(testInput) == 8)
    println(part2(input))
}