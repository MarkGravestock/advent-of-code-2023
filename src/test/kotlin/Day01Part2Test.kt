import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day01Part2Test: FunSpec ({

    val firstLine = "two1nine"

    test("it should find the first match") {
        forAll(
            row(firstLine, "two"),
            row("abcone2threexyz", "one"),
            row("7pqrstsixteen", "7")
        ) { line, expectedFirstMatch ->
            val firstMatch = firstMatch(line)

            firstMatch shouldBe expectedFirstMatch
        }
    }

    test("it should find the last match") {
        forAll(
            row(firstLine, "nine"),
            row("abcone2threexyz", "three"),
            row("7pqrstsixteen", "six")
        ) { line, expectedFirstMatch ->
            val firstMatch = lastMatch(line)

            firstMatch shouldBe expectedFirstMatch
        }
    }

    test("it should find the value") {
        forAll(
            row("two", 2),
            row("7", 7),
        ) { line, expectedFirstMatch ->
            val firstMatch = digitAndWordsMap()[line]

            firstMatch shouldBe expectedFirstMatch
        }
    }

    test("it should calculate the line value") {
        forAll(
            row(firstLine, 29),
            row("abcone2threexyz", 13),
            row("7pqrstsixteen", 76)
        ) { line, expectedValue ->
            val value = lineValueOfPart2(line)
            value shouldBe expectedValue
        }
    }

})

private fun firstMatch(line: String): String {
    return line.findAnyOf(digitsAndWords())!!.second
}

private fun lastMatch(line: String): String {
    return line.findLastAnyOf(digitsAndWords())!!.second
}

private fun lineValueOfPart2(line: String): Int {
    val firstValue = 10 * digitAndWordsMap()[firstMatch(line)]!!
    val secondValue = digitAndWordsMap()[lastMatch(line)]!!
    return firstValue + secondValue
}

private fun digitAndWordsMap(): Map<String, Int> {
    val digitWordsPairs = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").mapIndexed { index, value -> Pair(value, index + 1) }
    val digitPairs = (1..9).map { it.toString() }.mapIndexed { index, value -> Pair(value, index + 1) }
    return digitWordsPairs.union(digitPairs).toMap()
}

private fun digitsAndWords(): Set<String> {
    return digitAndWordsMap().keys
}

