fun main() {
    fun part1(input: List<String>): Int {
        return CalibrationDocument(input).totalCalibrationValues
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()

    part2(input).println()
}
