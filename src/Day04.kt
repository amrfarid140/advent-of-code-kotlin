fun main() {

    class BoardItem(
        val value: Int,
    ) {
        var isStruck: Boolean = false
            private set

        fun strike() {
            isStruck = true
        }

        override fun toString(): String {
            return "${value}${if (isStruck) "*" else ""} "
        }
    }

    class Board(private val numbers: List<List<BoardItem>>) {
        fun List<List<BoardItem>>.score(multiplier: Int): Int {
            return if (any { row -> row.all { it.isStruck } }) {
                numbers.sumOf { row ->
                    row.sumOf { if (it.isStruck) 0 else it.value }
                }.times(multiplier)
            } else {
                0
            }
        }

        fun List<List<BoardItem>>.transpose(): List<List<BoardItem>> {
            val transposed = Array(size) { Array<BoardItem?>(first().size) { null } }
            for (i in indices) {
                for (j in first().indices) transposed[i][j] = this[j][i]
            }
            return transposed.map { it.filterNotNull().toList() }.toList()
        }

        fun mark(numberToMark: Int): Int {
            numbers.forEach { row ->
                row.forEach { item ->
                    if (item.value == numberToMark) {
                        item.strike()
                    }
                }
            }
            return maxOf(numbers.score(numberToMark), numbers.transpose().score(numberToMark))
        }

        override fun toString(): String {
            val builder = StringBuilder()
            numbers.forEach { row ->
                row.forEach {
                    builder.append(it.toString())
                }
                builder.append("\n")
            }
            return builder.toString()
        }
    }

    class Game(
        val draw: List<Int>,
        val boards: List<Board>
    ) {
        fun play(): Int {
            for (i in draw.indices) {
                println("play ${i + 1}: ${draw[i]}")
                println("----------------")
                val score = boards.sumOf { it.mark(draw[i]) }
                println("Score: $score")
                println("----------------")
                boards.forEach {
                    println(it.toString())
                }
                if (score > 0) {
                    return score
                }

            }
            return 0
        }

        fun playLastToWin(): Int {
            val boardsCopy = boards.toMutableList()
            for (i in draw.indices) {
                if (boardsCopy.size == 1) {
                    val lastScore = boardsCopy.first().mark(draw[i])
                    if (lastScore > 0) {
                        return lastScore
                    }
                } else {
                    boardsCopy.removeIf { it.mark(draw[i]) > 0}
                }
            }
            return 0
        }
    }

    fun readInput(input: List<String>): Game {
        val numberDraw = input.first().split(",").map { it.toInt() }
        val boards = input.subList(1, input.size)
            .filter { it.isNotBlank() }
            .windowed(5, 5) { board ->
                Board(
                    board.map { row ->
                        val x = row.trim().split(" ")
                            .filter { it.isNotBlank() }
                        x.map { col -> BoardItem(col.toInt()) }
                    }
                )
            }
        return Game(numberDraw, boards)
    }

    fun part1(input: List<String>): Int {
        val game = readInput(input)
        return game.play()
    }

    fun part2(input: List<String>): Int {
        val game = readInput(input)
        return game.playLastToWin()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
