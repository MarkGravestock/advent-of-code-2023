package day06

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
