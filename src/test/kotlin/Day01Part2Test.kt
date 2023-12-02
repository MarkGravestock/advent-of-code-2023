import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day01Part2Test: FunSpec ({

    val firstLine = "two1nine"
    val digitsAndWords = digitsAndWords()

    test("it should find the first value") {
        val firstMatch = firstLine.findAnyOf(digitsAndWords)!!.second

        firstMatch shouldBe "two"
    }

    test("it should find the last value") {
        val lastMatch = firstLine.findLastAnyOf(digitsAndWords)!!.second

        lastMatch shouldBe "nine"
    }
})

private fun digitsAndWords(): Set<String> {
    val digitWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val digits = (1..9).map { it.toString() }
    return digitWords.union(digits)
}

