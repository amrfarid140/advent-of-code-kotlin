fun main() {

    fun powerConsumption(values: List<String>): Int {
        val (gamma, epsilon) = (0 until values.first().length).map { index ->
            val mutableMap = mutableMapOf(
                    '0' to 0,
                    '1' to 0
            )
            values.forEach { binary ->
                mutableMap.compute(binary[index]) { _, value ->
                    value?.plus(1)
                }
            }
            Pair(
                    mutableMap.entries.maxByOrNull { it.value }!!.key,
                    mutableMap.entries.minByOrNull { it.value }!!.key
            )
        }.fold(Pair("", "")) { acc, chars ->
            acc.copy(
                    first = acc.first.plus(chars.first),
                    second = acc.second.plus(chars.second)
            )
        }
        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2)
    }

    fun bitCriteria(values: List<String>, predicate: (@ParameterName("zeroes") Int, @ParameterName("ones") Int) -> Char): String {
        return (0 until values.first().length).fold(values) { numbers, index ->
            if (numbers.size == 1) {
                return@fold numbers
            }
            val mutableMap = mutableMapOf(
                    '0' to 0,
                    '1' to 0
            )
            numbers.forEach { binary ->
                mutableMap.compute(binary[index]) { _, value ->
                    value?.plus(1)
                }
            }
            numbers.filter { it[index] == predicate(mutableMap['0']!!, mutableMap['1']!!) }
        }.first()
    }

    fun lifeSupportRating(values: List<String>): Int {
        val o2 = bitCriteria(values) { zeroes, ones ->
            if (zeroes > ones) {
                '0'
            } else {
                '1'
            }
        }
        val co2 = bitCriteria(values) { zeroes, ones ->
            if (zeroes > ones) {
                '1'
            } else {
                '0'
            }
        }

        return Integer.parseInt(o2, 2) * Integer.parseInt(co2, 2)
    }

    fun part1(input: List<String>): Int {
        return powerConsumption(input)
    }

    fun part2(input: List<String>): Int {
        return lifeSupportRating(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
