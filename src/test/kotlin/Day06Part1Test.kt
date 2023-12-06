import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

// https://adventofcode.com/2023/day/6

class BoatRaceOption(val raceTime: Long, val holdButtonTime: Long, val recordDistance: Long) {
    fun distance(): Long {
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

class BoatRaces(private val fileInput: List<String>) {
    private val regex = "(\\d+)".toRegex()

    private fun extractNumbers(source: String): Sequence<Long> {
        return regex.findAll(source).map { it.value.toLong() }
    }

    fun numberOfWinningWays(): Long {
        val times = extractNumbers(fileInput[0])
        val distances = extractNumbers(fileInput[1])

        return times.zip(distances)
            .map { BoatRace(it.first, it.second).winningWays() }
            .reduce { acc, i -> acc * i }
    }
}

class BoatRace(val raceTime: Long, val recordDistance: Long) {
    fun winningWays(): Long {
        return (1..raceTime).asSequence()
            .filter { BoatRaceOption(raceTime, holdButtonTime = it, recordDistance).isRecord() }
            .countLong()
    }

    private fun <T> Sequence<T>.countLong(): Long {
        var count = 0L
        for (item in this) {
            count++
        }
        return count
    }
}

