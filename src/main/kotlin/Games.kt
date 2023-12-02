class Games(private val testInput: List<String>) {
    fun getTotalPossibleIdsForCubes(cubes: Game.Cubes): Int {
        return testInput.map { Game(it) }.filter { it.isPossibleFor(cubes) }.map { it.number() }.sum()
    }

}

class Game(private val testInput: String) {
    fun number(): Int {
        return ("Game (\\d+): (.*)".toRegex()).find(testInput)!!.groupValues[1].toInt()
    }

    fun numberOfTurns(): Int {
        return turns().count()
    }

    fun turns(): Iterable<Turn> {
        return ("Game (\\d+): (.*)".toRegex()).find(testInput)!!.groupValues[2].split(";".toRegex()).map { Turn(it) }
    }

    fun isPossibleFor(cubes: Cubes) : Boolean {
        return turns().all { cubes.isPossibleTurn(it) }
    }

    class Cubes(val red: Int, val green: Int, val blue: Int) {
        fun isPossibleTurn(turn: Turn) : Boolean {
            return turn.red() <= red && turn.green() <= green && turn.blue() <= blue
        }

        companion object {
            fun default() : Cubes {
                return Cubes(red = 12, green = 13, blue = 14)
            }
        }
    }

    class Turn(private val turnDetails: String) {

        private fun cubesOfColour(colour: String): Int {
            return ("(\\d+) $colour".toRegex()).find(turnDetails)?.groupValues?.get(1)?.toInt() ?: 0
        }

        fun red(): Int {
            return cubesOfColour("red")
        }

        fun green(): Int {
            return cubesOfColour("green")
        }

        fun blue(): Int {
            return cubesOfColour("blue")
        }

    }

}
