import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

class Day01Test: FunSpec({
    test("first test") {
        true shouldBe true
    }
})
