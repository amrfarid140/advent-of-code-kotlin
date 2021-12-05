import kotlin.math.abs

fun main() {

    class Point(val x: Int, val y: Int) {

        override fun toString(): String {
            return "{ x:$x, y:$y }"
        }

        override fun equals(other: Any?): Boolean {
            return other is Point && (other.y == y && other.x == x)
        }

        override fun hashCode(): Int {
            return x.hashCode().plus(y.hashCode())
        }
    }

    class Line(val start: Point, val end: Point) {
        val points: List<Point>

        init {
            points = mutableListOf<Point>().apply {
                add(start)
                if (start.x - end.x == start.y - end.y) {
                    (minOf(start.x, end.x) + 1 until maxOf(start.x, end.x)).forEach { x ->
                        add(Point(x, x))
                    }
                } else {
                    (minOf(start.x, end.x) + 1 until maxOf(start.x, end.x)).forEach { x ->
                        add(Point(x, start.y))
                    }
                    (minOf(start.y, end.y) + 1 until maxOf(start.y, end.y)).forEach { y ->
                        add(
                            Point(
                                start.x,
                                y
                            )
                        )
                    }
                }
                add(end)
            }
        }

        fun draw() {
            val plane = Array(10) { Array(10) { '-' } }
            points.forEach {
                plane[it.x][it.y] = '*'
            }
            plane.forEach {
                println(it.joinToString(""))
            }
        }

        override fun toString(): String {
            return "{ ${points.joinToString(", ") { it.toString() }} }"
        }

        override fun equals(other: Any?): Boolean {
            return other is Line && other.start == start && other.end == end
        }

        override fun hashCode(): Int {
            return start.hashCode().plus(end.hashCode())
        }
    }

    fun part1(input: List<String>): Int {
        val pointsMap = mutableMapOf<Point, Int>()
        val lines = input.map { line ->
            val points = line.replace(" ", "")
                .split("->")
            val start = points[0].split(",").run {
                Point(get(0).toInt(), get(1).toInt())
            }
            val end = points[1].split(",").run {
                Point(get(0).toInt(), get(1).toInt())
            }
            Line(start, end)
        }
        val singleDirectionLines = lines
            .filter {
                it.start.x == it.end.x || it.start.y == it.end.y
            }
        singleDirectionLines.forEach { line ->
            line.points.forEach {
                pointsMap.compute(it) { _, value ->
                    value?.plus(1) ?: 1
                }
            }
        }
        return pointsMap.count { it.value > 1 }
    }

    fun part2(input: List<String>): Int {
        val pointsMap = mutableMapOf<Point, Int>()
        val lines = input.map { line ->
            val points = line.replace(" ", "")
                .split("->")
            val start = points[0].split(",").run {
                Point(get(0).toInt(), get(1).toInt())
            }
            val end = points[1].split(",").run {
                Point(get(0).toInt(), get(1).toInt())
            }
            Line(start, end)
        }
        val singleDirectionLines = lines
            .filter {
                it.start.x == it.end.x || it.start.y == it.end.y || (it.start.x - it.end.x == it.start.y - it.end.y)
            }

//        val plane = Array(10) { Array(10) { '-' } }
//        lines.forEach { line ->
//            line.points.forEach {
//                if (plane[it.x][it.y] == '-') {
//                    plane[it.x][it.y] = '1'
//                } else {
//                    plane[it.x][it.y] = (plane[it.x][it.y].digitToInt() + 1).digitToChar()
//                }
//            }
//        }
//        plane.forEach {
//            println(it.joinToString(""))
//        }
        singleDirectionLines.forEach { line ->
            line.points.forEach {
                pointsMap.compute(it) { _, value ->
                    value?.plus(1) ?: 1
                }
            }
        }
        return pointsMap.count { it.value > 1 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 5)
//    println(part2(testInput))
//    check(part2(testInput) == 12)
    val input = readInput("Day05")
//    println(part1(input))
    println(part2(input))
//    val line = Line(
//        Point(0, 0),
//        Point(8, 8)
//    )
//    println(line)
}

//fun main() {
//
//    data class Point(val x: Int, val y: Int)
//
//    fun parseInput(input: List<String>): List<Pair<Point, Point>> {
//        val linePattern = "(\\d+),(\\d+).*?(\\d+),(\\d+)".toRegex()
//        return input
//            .mapNotNull { linePattern.matchEntire(it) }
//            .map { it.groupValues.drop(1).map(String::toInt) }
//            .map { (a, b, c, d) -> Point(a, b) to Point(c, d) }
//    }
//
//    fun getLine(start: Point, end: Point): List<Point> {
//        val xRange = if (end.x >= start.x) start.x..end.x else start.x downTo end.x
//        val yRange = if (end.y >= start.y) start.y..end.y else start.y downTo end.y
//        return mutableListOf<Point>().apply {
//            val xit = xRange.iterator()
//            val yit = yRange.iterator()
//            var x = -1
//            var y = -1
//            while (xit.hasNext() || yit.hasNext()) {
//                if (xit.hasNext()) x = xit.nextInt()
//                if (yit.hasNext()) y = yit.nextInt()
//                add(Point(x, y))
//            }
//        }
//    }
//
//    fun List<Pair<Point, Point>>.getNumOverlaps(): Int {
//        return map { (start, end) -> getLine(start, end) }
//            .flatten()
//            .groupingBy { it }
//            .eachCount()
//            .filterValues { it > 1 }
//            .size
//    }
//
//    fun part1(input: List<String>): Int = parseInput(input)
//        .filter { (start, end) -> start.x == end.x || start.y == end.y }
//        .getNumOverlaps()
//
//    fun part2(input: List<String>): Int = parseInput(input).getNumOverlaps()
//
//    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 5)
//    check(part2(testInput) == 12)
//
//    val input = readInput("Day05")
//    println(part1(input))
//    println(part2(input))
//}