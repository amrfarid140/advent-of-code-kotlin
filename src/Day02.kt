fun main() {

    fun position(values: List<String>): Int {
        val depthAndVelocity = values.fold(Pair(0, 0)) { pair, current ->
            val intValue = current.split(" ").last().toInt()
            if (current.startsWith("up")) {
                pair.copy(first = pair.first - intValue)
            } else if (current.startsWith("down")) {
                pair.copy(first = pair.first + intValue)
            } else if (current.startsWith("forward")) {
                pair.copy(second = pair.second + intValue)
            } else {
                pair
            }
        }
        return depthAndVelocity.first * depthAndVelocity.second
    }

    fun aim(values: List<String>): Int {
        val depthAndVelocity = values.fold(Triple(0, 0, 0)) { pair, current ->
            val intValue = current.split(" ").last().toInt()
            if (current.startsWith("up")) {
                pair.copy(first = pair.first - intValue)
            } else if (current.startsWith("down")) {
                pair.copy(first = pair.first + intValue)
            } else if (current.startsWith("forward")) {
                pair.copy(
                    second = pair.second + intValue,
                    third = pair.third + (pair.first * intValue)
                )
            } else {
                pair
            }
        }
        return depthAndVelocity.second * depthAndVelocity.third
    }

    fun part1(input: List<String>): Int {
        return position(input)
    }

    fun part2(input: List<String>): Int {
        return aim(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
