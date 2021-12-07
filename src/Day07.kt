import kotlin.math.abs

fun main() {

    fun positionsInput(input: List<String>) =
        input.first().split(",").map { it.toInt() }

    fun part1(input: List<String>): Int {
        val positions = positionsInput(input)
        return positions.minOf {
            positions.fold(0) { acc, i -> abs(i - it) + acc }
        }
    }

    fun cost(distance: Int): Int = (1..distance).sum()

    fun part2(input: List<String>): Int {
        val positions = positionsInput(input)
        return positions.minOf {
            positions.sumOf { i -> cost(abs(i - it)) }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
