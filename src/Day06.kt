fun main() {

    fun simulate(values: List<Int>, turns: Int) : Long {
        // We will have a map of fishes with similar age { [3 days]: 5 fishes }
        val fish = values.groupBy { it }
            .map { (k, v) -> k to v.size.toLong() }
            .toMap()
            .toMutableMap()

        repeat(turns) {
            val updates = fish.map { (age, amount) ->
                if(age == 0) (6 to amount) else (age - 1 to amount)
            } + (8 to (fish[0] ?: 0))
            fish.clear()
            updates.forEach { (age, amount) -> fish[age] = (fish[age] ?: 0) + amount  }
        }

        return fish.values.sum()
    }

    fun part1(input: List<String>): Long {
        return simulate(input.single().split(",").map { it.toInt() }, 80)
    }

    fun part2(input: List<String>): Long {
        return simulate(input.single().split(",").map { it.toInt() }, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
