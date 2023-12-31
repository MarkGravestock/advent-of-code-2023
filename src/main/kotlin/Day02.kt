import day02.Game
import day02.Games

fun main() {
    fun part1(input: List<String>): Int {
        return Games(input).getTotalPossibleIdsForCubes(Game.Cubes.default())
    }

    fun part2(input: List<String>): Int {
        return Games(input).calculateTotalPowerOfMinimumCubes()
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()

    part2(input).println()
}
