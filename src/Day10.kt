fun main() {

    fun solve(input: List<String>): Int {
        var cycle = 1
        var x = 1
        var res = 0
        input.forEach { line ->
            val cmd = line.split(" ")
            val steps = if (cmd[0] == "noop") 1 else 2
            repeat(steps) {
                cycle++
                val index = (cycle - 1) % 40
                if (steps == 2 && it == 1) {
                    x += cmd[1].toInt()
                }
                if (cycle == 20 || (cycle - 20) % 40 == 0) {
                    res += cycle * x
                }
                print(if (index in x - 1..x + 1) '#' else '.')
                if (index == 0) {
                    println()
                }
            }
        }
        println()
        return res
    }

    val testInput = readInput("Day10_test")
    check(solve(testInput) == 13140)

    val input = readInput("Day10")
    println(solve(input))
}