fun main() {

    fun build(input: String) =
        buildMap<String, Int> {
            var pwd = ""
            put("", 0)
            input.parseInput.map { it ->
                it.substring(0, 2).let { cmd ->
                    when (cmd) {
                        "cd" -> it.substring(3).let { dir ->
                            when (dir) {
                                ".." -> pwd = pwd.substringBeforeLast("/", "")
                                else -> pwd += if (pwd == "") dir else "/$dir"
                            }
                        }

                        "ls" -> it.substring(3).split("\n").drop(1).filter { "dir" !in it }.map {
                            it.split(" ").let { (size, _) ->
                                var path = pwd
                                put(path, getOrDefault(path, 0) + size.toInt())
                                while (path.isNotEmpty()) {
                                    path = path.substringBeforeLast("/", "")
                                    put(path, getOrDefault(path, 0) + size.toInt())
                                }
                            }
                        }

                        else -> error("Unknown command: $cmd")
                    }
                }
            }
        }


    fun part1(input: String): Int = build(input).values.filter { it < 100000 }.sum()

    fun part2(input: String): Int = build(input).values.let { total ->
        total.max().let { totalSum ->
            total.filter { 70000000 - totalSum + it >= 30000000 }.min()
        }
    }

    val testInput = readRawInput("Day07_test")
    check(part1(testInput) == 95437)

    val input = readRawInput("Day07")
    println(part1(input))

    check(part2(testInput) == 24933642)
    println(part2(input))
}

val String.parseInput: List<String> get() = this.split("$").drop(2).map { it.trim() }