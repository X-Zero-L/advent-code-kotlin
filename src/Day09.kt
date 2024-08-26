import kotlin.math.abs

enum class Direction(val dx: Int, val dy: Int) {
    U(0, -1),
    D(0, 1),
    L(-1, 0),
    R(1, 0),
    U_L(-1, -1),
    U_R(1, -1),
    D_L(-1, 1),
    D_T(1, 1),
    STOP(0, 0);

    operator fun component1(): Int {
        return dx
    }

    operator fun component2(): Int {
        return dy
    }
}

fun main() {


    val directions = Direction.entries

    fun isAdjacent(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean = directions.any {
        abs(p1.first - p2.first) == abs(it.dx) && abs(p1.second - p2.second) == abs(it.dy)
    }

    fun solve(input: List<String>, ropeLength: Int): Int = mutableSetOf(0 to 0).let { locationSet ->
        MutableList(ropeLength) { 0 to 0 }.let { rope ->
            input.forEach { line ->
                val (direction, distance) = line.split(" ").let { (di, dis) -> Direction.valueOf(di) to dis.toInt() }
                repeat(distance) {
                    rope[0] = rope[0].let { (x, y) -> (x + direction.dx to y + direction.dy) }
                    rope.forEachIndexed { index, _ ->
                        if (index == 0) return@forEachIndexed
                        if (isAdjacent(rope[index - 1], rope[index])) {
                            return@repeat
                        }
                        val (dx, dy) = rope[index].let { (x, y) -> rope[index - 1].first - x to rope[index - 1].second - y }
                            .let { (x, y) -> (if (x == 0) 0 else x / abs(x)) to (if (y == 0) 0 else y / abs(y)) }
                        val newDirection = directions.first { it.dx == dx && it.dy == dy }
                        rope[index] = rope[index].let { (x, y) -> (x + newDirection.dx to y + newDirection.dy) }
                    }
                    locationSet.add(rope.last())
                }
            }
        }
        locationSet.size
    }

    fun part1(input: List<String>): Int = solve(input, 2)

    fun part2(input: List<String>): Int = solve(input, 10)

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)

    val input = readInput("Day09")
    println(part1(input))

    check(part2(readInput("Day09_test2")) == 36)
    println(part2(input))
}