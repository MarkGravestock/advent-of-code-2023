import day03.EngineSchematic

fun main() {
    fun part1(input: List<String>): Int {
        return EngineSchematic(input).totalOfValidPartNumbers()
    }

    fun part2(input: List<String>): Int {
        return EngineSchematic(input).totalOfValidPartNumbers()
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()

    part2(input).println()
}
