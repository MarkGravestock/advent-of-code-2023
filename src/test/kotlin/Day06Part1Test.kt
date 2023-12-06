import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

// https://adventofcode.com/2023/day/6

class BoatRaceOption(val raceTime: Int, val holdButtonTime: Int, val recordDistance: Int) {
    fun distance(): Int {
        val travelTime = raceTime - holdButtonTime
        val velocity = holdButtonTime
        return travelTime * velocity
    }

    fun isRecord(): Boolean {
        return distance() > recordDistance
    }

}

class Day06Part1Test : FunSpec({

    context("Boat Race tactics") {
        test("It can calculate the distance") {
            forAll(
                row(0, 0),
                row(3, 12),
                row(7, 0)
            ) { holdTime, expectedDistance ->
                BoatRaceOption(raceTime = 7, holdButtonTime = holdTime, recordDistance = 9).distance() shouldBe expectedDistance
            }
        }

        test("It can determine if its a record") {
            forAll(
                row(0, false),
                row(3, true),
                row(7, false)
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

class BoatRaces(val fileInput: List<String>) {
    fun numberOfWinningWays(): Int {
        val regex = "(\\d+)".toRegex()
        val times = regex.findAll(fileInput[0]).map{ it.value.toInt() }
        val distances = regex.findAll(fileInput[1]).map{ it.value.toInt() }
        return times.zip(distances).map { BoatRace(it.first, it.second).winningWays() }.reduce { acc, i -> acc * i }
    }

}

class BoatRace(val raceTime: Int, val recordDistance: Int) {
    fun winningWays(): Int {
        return (1..raceTime).asSequence()
            .filter { BoatRaceOption(raceTime, holdButtonTime = it, recordDistance).isRecord() }
            .count()
    }
}

