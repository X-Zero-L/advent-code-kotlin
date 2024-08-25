fun main() {

    val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun isHighest(i: Int, j: Int, input: List<String>, direction: Pair<Int, Int>): Boolean =
        generateSequence(i to j) { (x, y) -> x + direction.first to y + direction.second }
            .takeWhile { (x, y) -> x in input.indices && y in 0 until input[0].length }.drop(1)
            .all { (x, y) -> input[x][y] < input[i][j] }

    fun getTreeCount(i: Int, j: Int, input: List<String>, direction: Pair<Int, Int>): Int =
        generateSequence(i to j) { (x, y) -> x + direction.first to y + direction.second }
            .drop(1)
            .takeWhile { (x, y) -> x in input.indices && y in 0 until input[0].length && input[x][y] < input[i][j] }
            .count().let { count ->
                if (count == 0) 0 else count + if ((direction.second == 0 && i + direction.first * count in 1 until input.size - 1) ||
                    (direction.second != 0 && j + direction.second * count in 1 until input[0].length - 1)
                ) 1 else 0
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