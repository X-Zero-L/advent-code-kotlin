fun parse(input: String): Any {
    var i = 0
    fun next(): Any {
        if (input[i] == '[') { // build new list
            i++
            val res = mutableListOf<Any>()
            while (true) {
                if (input[i] == ']') {
                    i++
                    return res
                }
                res.add(next())
                if (input[i] == ']') {
                    i++
                    return res
                }
                check(input[i] == ',')
                i++
            }
        }
        check(input[i] in '0'..'9')
        var num = 0
        while (input[i] in '0'..'9') {
            num = num * 10 + (input[i] - '0')
            i++
        }
        return num
    }

    val res = next()
    check(i == input.length)
    return res
}

fun compare(a: Any, b: Any): Int {
    if (a is Int && b is Int) return a.compareTo(b)
    if (a is List<*> && b is List<*>) {
        var i = 0
        while (i < a.size && i < b.size) {
            val cmp = compare(a[i]!!, b[i]!!)
            if (cmp != 0) return cmp
            i++
        }
        return a.size.compareTo(b.size)
    }
    if (a is Int) return compare(listOf(a), b)
    if (b is Int) return compare(a, listOf(b))
    error("Unsupported type: type of a is ${a::class.simpleName}, type of b is ${b::class.simpleName}")
}

fun main() {
    fun part1(input: List<String>): Int = input.chunked(3).mapIndexed { index, it ->
        val (a, b) = parse(it[0]) to parse(it[1])
        if (compare(a, b) < 0) index + 1 else 0
    }.sum()

    fun part2(input: List<String>): Int = (parse("[[2]]") to parse("[[6]]")).let { (d1, d2) ->
        (input.mapNotNull { if (it.isEmpty()) null else parse(it) } + listOf(
            d1,
            d2
        )).sortedWith { a, b ->
            compare(a, b)
        }.let {
            (it.indexOf(d1) + 1) * (it.indexOf(d2) + 1)
        }
    }


    val testInput = readInput("Day13_test")
    check(part1(testInput) == 13)

    val input = readInput("Day13")
    println(part1(input))

    check(part2(testInput) == 140)
    println(part2(input))
}