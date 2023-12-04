import day04.Scratchcards

fun main() {
    fun part1(input: List<String>): Int {
        return Scratchcards(input).totalPoints()
    }

    fun part2(input: List<String>): Int {
        return Scratchcards(input).totalScratchcardsWon()
    }

    val testInput = readInputForDay(4)
    check(part1(testInput) == 20667)

    val input = readInputForDay(4)
    check(part2(input) == 5833065)
}
