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
            val firstMatch = CalibrationDocument.firstMatch(line)

            firstMatch shouldBe expectedFirstMatch
        }
    }

    test("it should find the last match") {
        forAll(
            row(firstLine, "nine"),
            row("abcone2threexyz", "three"),
            row("7pqrstsixteen", "six")
        ) { line, expectedFirstMatch ->
            val firstMatch = CalibrationDocument.lastMatch(line)

            firstMatch shouldBe expectedFirstMatch
        }
    }

    test("it should find the value") {
        forAll(
            row("two", 2),
            row("7", 7),
        ) { line, expectedFirstMatch ->
            val firstMatch = CalibrationDocument.digitAndWordsMap()[line]

            firstMatch shouldBe expectedFirstMatch
        }
    }

    test("it should calculate the line value") {
        forAll(
            row(firstLine, 29),
            row("abcone2threexyz", 13),
            row("7pqrstsixteen", 76)
        ) { line, expectedValue ->
            val value = CalibrationDocument.lineValueOfPart2(line)
            value shouldBe expectedValue
        }
    }

    test("it should calculate the total calibration values for the test values") {
        val testInput = readInput("Day01_Part2_test")

        val sut = CalibrationDocument(testInput)

        sut.totalCalibrationValuesPart2() shouldBe 281
    }

    test("it should calculate the total calibration values") {
        val testInput = readInput("Day01")

        val sut = CalibrationDocument(testInput)

        sut.totalCalibrationValuesPart2() shouldBe 54885
    }

})

