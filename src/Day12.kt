fun main() {
    val directions = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)

    fun isValid(s: Char, e: Char) = s == 'S' || e <= s + 1

    fun isOut(x: Int, y: Int, n: Int, m: Int) = x < 0 || x >= n || y < 0 || y >= m

    fun bfs(start: Pair<Int, Int>, input: List<String>): Int {
        val st = mutableMapOf<Triple<Int, Int, Int>, Boolean>()
        st[Triple(start.first, start.second, 0)] = true
        val q = mutableListOf(Triple(start.first, start.second, 0))
        while (q.isNotEmpty()) {
            val (x, y, times) = q.removeFirst()
            directions.forEachIndexed { index, (dx, dy) ->
                val nx = x + dx
                val ny = y + dy
                if (isOut(nx, ny, input.size, input[0].length)) return@forEachIndexed
                if (input[nx][ny] == 'E') {
                    return times + 1
                }
                if (!isValid(input[x][y], input[nx][ny])) return@forEachIndexed
                if (st[Triple(nx, ny, index)] == true) return@forEachIndexed
                st[Triple(nx, ny, index)] = true
                q.add(Triple(nx, ny, times + 1))
            }
        }
        return Int.MAX_VALUE
    }

    fun part1(input: List<String>): Int = input.asSequence().mapIndexedNotNull { x, row ->
        row.indexOf('S').takeIf { it != -1 }?.let { x to it }
    }.first().let { s -> bfs(s, input) }

    fun part2(input: List<String>): Int = input.flatMapIndexed { x, row ->
        row.mapIndexedNotNull { y, c -> if (c == 'a') x to y else null }
    }.let { startPoints -> startPoints.minOfOrNull { bfs(it, input) } ?: error("No path found") }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 25)

    val input = readInput("Day12")
    println(part1(input))

    check(part2(testInput) == 23)
    println(part2(input))
}