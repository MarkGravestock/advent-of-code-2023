import day01.CalibrationDocumentPartOne
import day01.CalibrationDocumentPartTwo

fun main() {
    fun part1(input: List<String>): Int {
        return CalibrationDocumentPartOne(input).totalCalibrationValues()
    }

    fun part2(input: List<String>): Int {
        return CalibrationDocumentPartTwo(input).totalCalibrationValues()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()

    part2(input).println()
}
