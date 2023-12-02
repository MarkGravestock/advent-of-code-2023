import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class Day01Part1Test: FunSpec ({
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

test("it should calculate the total calibration values for the real file") {
        val testInput = readInput("Day01")

        val sut = CalibrationDocument(testInput)

        sut.totalCalibrationValues shouldBe 54697
    }
})

