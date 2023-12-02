import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class Day01Test: FunSpec ({
    test("it should read the file") {
        val testInput = readInput("Day01_test")

        testInput shouldNotBe null
        testInput shouldHaveSize 4
    }

    test("it should calculate the first calibration value") {
        val testInput = readInput("Day01_test")

        val sut = CalibrationDocument(testInput)

        sut.firstLineValue shouldBe 12
    }

    test("it should calculate the total calibration values") {
        val testInput = readInput("Day01_test")

        val sut = CalibrationDocument(testInput)

        sut.totalCalibrationValues shouldBe 142
    }

})

class CalibrationDocument(testInput: List<String>) {

    val firstLineValue = lineValueOf(testInput.first())

    val totalCalibrationValues = testInput.sumOf { lineValueOf(it) }
    private fun lineValueOf(line: String): Int { return line.first(Char::isDigit).digitToInt() * 10 + line.last(Char::isDigit).digitToInt() }
}
