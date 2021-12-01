fun main() {

    fun increases(numbers: List<Int>): Int = numbers.filterIndexed { index, i ->
        if (index == 0) {
            false
        } else {
            i > numbers[index - 1]
        }
    }.size

    fun part1(input: List<String>): Int {
        val numbers = input.map { it.toInt() }
        return increases(numbers)
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { it.toInt() }
        val groupSums = mutableListOf<Int>()
        run lit@ {
            numbers.forEachIndexed { index, i ->
                if (index + 2 > numbers.size - 1) {
                    return@lit
                }
                groupSums.add(i + numbers[index + 1] + numbers[index + 2])
            }
        }
        return increases(groupSums)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
