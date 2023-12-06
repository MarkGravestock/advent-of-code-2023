import day06.BoatRace
import day06.BoatRaceOption
import day06.BoatRaces
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

// https://adventofcode.com/2023/day/6

class Day06Part1Test : FunSpec({

    context("Boat Race tactics") {
        test("It can calculate the distance") {
            forAll(
                row(0L, 0L),
                row(3L, 12L),
                row(7L, 0L)
            ) { holdTime, expectedDistance ->
                BoatRaceOption(raceTime = 7, holdButtonTime = holdTime, recordDistance = 9).distance() shouldBe expectedDistance
            }
        }

        test("It can determine if its a record") {
            forAll(
                row(0L, false),
                row(3L, true),
                row(7L, false)
            ) { holdTime, isRecord ->
                BoatRaceOption(raceTime = 7, holdButtonTime = holdTime, recordDistance = 9).isRecord() shouldBe isRecord
            }
        }

        test("It can determine the number of winning way") {
            BoatRace(raceTime = 7, recordDistance = 9).winningWays() shouldBe 4
        }
    }

    context("Boat Races") {

        test("It can number of ways to beat the record") {
            val fileInput = readTestInputForDay(6)
            val sut = BoatRaces(fileInput)

            sut.numberOfWinningWays() shouldBe 288
        }

        test("It can number of ways to beat the record for the real file") {
            val fileInput = readInputForDay(6)
            val sut = BoatRaces(fileInput)

            sut.numberOfWinningWays() shouldBe 2374848
        }
    }
})

