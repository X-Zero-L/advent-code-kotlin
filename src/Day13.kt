import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement

fun readNestedList(input: String): List<Any> {
    val jsonElement: JsonElement = Json.parseToJsonElement(input)
    return parseJsonElement(jsonElement)
}

fun parseJsonElement(element: JsonElement): List<Any> {
    return when (element) {
        is JsonArray -> element.map { parseJsonElement(it) }
        else -> listOf(element.toString().toInt())
    }
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
        val (a, b) = readNestedList(it[0]) to readNestedList(it[1])
        if (compare(a, b) < 0) index + 1 else 0
    }.sum()

    fun part2(input: List<String>): Int = (readNestedList("[[2]]") to readNestedList("[[6]]")).let { (d1, d2) ->
        (input.mapNotNull { if (it.isEmpty()) null else readNestedList(it) } + listOf(
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