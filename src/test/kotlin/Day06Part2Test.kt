import day06.BoatRaces
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

// https://adventofcode.com/2023/day/6

class Day06Part2Test : FunSpec({

    context("Boat Races Part 2") {

        test("It can find number of ways to beat the record for the 2nd part") {
            val fileInput = readTestInputForDay(6)
            val sut = BoatRaces(fileInput.map { it.replace("\\s".toRegex(), "") })

            sut.numberOfWinningWays() shouldBe 71503
        }

        test("It can find number of ways to beat the record for the real 2nd part") {
            val fileInput = readInputForDay(6)
            val sut = BoatRaces(fileInput.map { it.replace("\\s".toRegex(), "") })

            sut.numberOfWinningWays() shouldBe 39132886
        }
    }
})

