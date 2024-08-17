fun main() {

    fun solve(input: List<String>, actionFunction: (Int, Int, Int, List<ArrayDeque<Char>>) -> Unit): String = input
        .initializeStacks
        .let { stackList ->
            input
                .parseAction
                .map { (times, from, to) ->
                    actionFunction(times, from, to, stackList)
                }.run {
                    stackList
                        .map { it.first() }
                        .joinToString(separator = "")
                }
        }

    fun part1(input: List<String>): String = solve(input) { times, from, to, stackList ->
        repeat(times) {
            stackList[to].addFirst(stackList[from].removeFirst())
        }
    }

    fun part2(input: List<String>): String = solve(input) { times, from, to, stackList ->
        stackList[from].subList(0, times).asReversed().map {
            stackList[to].addFirst(it)
        }.map { stackList[from].removeFirst() }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")

    val input = readInput("Day05")
    println(part1(input))

    check(part2(testInput) == "MCD")
    println(part2(input))
}

val List<String>.initializeStacks: List<ArrayDeque<Char>>
    get() = List(this.getStackSize) { ArrayDeque<Char>() }.also {
        this.fillStackByInput { index, char ->
            it[index].add(char)
        }
    }

val List<String>.getStackSize
    get() = this
        .dropWhile { it.contains("[") }
        .first()
        .split(" ")
        .filter { it.isNotEmpty() }
        .maxOf { it.toInt() }

fun List<String>.fillStackByInput(onCharacterFound: (Int, Char) -> Unit) =
    this
        .filter { it.contains("[") }
        .map { line ->
            line
                .mapIndexed { index, char ->
                    if (char.isLetter()) {
                        onCharacterFound(index / 4, char)
                    }
                }
        }

val List<String>.parseAction: List<Triple<Int, Int, Int>>
    get() = this
        .filter { it.contains("move") }
        .map { line ->
            line
                .split(" ")
                .filter { it.isNotEmpty() }
                .let {
                    Triple(it[1].toInt(), it[3].toInt() - 1, it[5].toInt() - 1)
                }
        }