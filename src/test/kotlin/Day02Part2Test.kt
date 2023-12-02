import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day02Part2Test : FunSpec({
    test("can find minimum cubes per game") {
        val testInput = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"

        val sut = Game(testInput)

        val minimumCubes = sut.findMinimumCubes()

        minimumCubes.red shouldBe 20
        minimumCubes.blue shouldBe 6
        minimumCubes.green shouldBe 13
    }

    test("can calculate power of minimum cubes") {
        val testInput = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"

        val sut = Game(testInput)

        val powerOfMinimumCubes = sut.findMinimumCubes().power()

        powerOfMinimumCubes shouldBe 1560
    }

    test("can calculate power of minimum cubes in the test file") {
        val testInput = readInput("Day02_test")

        val sut = Games(testInput)

        sut.calculateTotalPowerOfMinimumCubes() shouldBe 2286
    }

    test("can calculate power of minimum cubes in the actual file") {
        val testInput = readInput("Day02")

        val sut = Games(testInput)

        sut.calculateTotalPowerOfMinimumCubes() shouldBe 67335
    }
})
