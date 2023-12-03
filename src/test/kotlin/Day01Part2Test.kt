import day01.CalibrationDocumentPartTwo
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day01Part2Test: FunSpec ({

    val firstLine = "two1nine"

    test("it should find the first match") {
        forAll(
            row(firstLine, 2),
            row("abcone2threexyz", 1),
            row("7pqrstsixteen", 7)
        ) { line, expectedFirstMatch ->
            val firstMatch = CalibrationDocumentPartTwo(listOf()).firstMatch(line)

            firstMatch shouldBe expectedFirstMatch
        }
    }

    test("it should find the last match") {
        forAll(
            row(firstLine, 9),
            row("abcone2threexyz", 3),
            row("7pqrstsixteen", 6)
        ) { line, expectedFirstMatch ->
            val firstMatch = CalibrationDocumentPartTwo(listOf()).lastMatch(line)

            firstMatch shouldBe expectedFirstMatch
        }
    }

    test("it should calculate the line value") {
        forAll(
            row(firstLine, 29),
            row("abcone2threexyz", 13),
            row("7pqrstsixteen", 76)
        ) { line, expectedValue ->
            val value = CalibrationDocumentPartTwo(listOf()).lineValueOf(line)
            value shouldBe expectedValue
        }
    }

    test("it should calculate the total calibration values for the test values") {
        val testInput = readInput("Day01_Part2_test")

        val sut = CalibrationDocumentPartTwo(testInput)

        sut.totalCalibrationValues() shouldBe 281
    }

    test("it should calculate the total calibration values") {
        val testInput = readInput("Day01")

        val sut = CalibrationDocumentPartTwo(testInput)

        sut.totalCalibrationValues() shouldBe 54885
    }

})

