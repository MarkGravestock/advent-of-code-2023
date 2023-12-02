import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day01Part2Test: FunSpec ({

    val firstLine = "two1nine"
    val digitsAndWords = digitsAndWords()

    test("it should find the first match") {
        val firstMatch = firstLine.findAnyOf(digitsAndWords)!!.second

        firstMatch shouldBe "two"
    }

    test("it should find the last match") {
        val lastMatch = firstLine.findLastAnyOf(digitsAndWords)!!.second

        lastMatch shouldBe "nine"
    }

    test("it should find the first value") {
        val firstMatch = firstLine.findAnyOf(digitsAndWords)!!.second
        val firstValue = digitAndWordsMap()[firstMatch]

        firstValue shouldBe 2
    }

})

private fun digitAndWordsMap(): Map<String, Int> {
    val digitWordsPairs = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").mapIndexed{ index, value -> Pair(value, index + 1) }
    val digitPairs = (1..9).mapIndexed() { index, value -> Pair(value.toString(), index + 1) }
    return digitWordsPairs.union(digitPairs).toMap()
}

private fun digitsAndWords(): Set<String> {
    val digitWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val digits = (1..9).map { it.toString() }
    return digitWords.union(digits)
}

