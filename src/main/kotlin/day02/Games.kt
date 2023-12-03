package day02

class Games(private val testInput: List<String>) {
    fun getTotalPossibleIdsForCubes(cubes: Game.Cubes): Int {
        return testInput.map { Game(it) }.filter { it.isPossibleFor(cubes) }.map { it.number() }.sum()
    }

    fun calculateTotalPowerOfMinimumCubes(): Int {
        return testInput.map { Game(it) }.sumOf { it.findMinimumCubes().power() }
    }

}

class Game(private val testInput: String) {
    companion object {
        private const val GAME_NUMBER = 1
        private const val TURN_STRING = 2
        private const val COLOUR_DELIMETER = ";"
    }

    fun number(): Int {
        return extractParts()[GAME_NUMBER].toInt()
    }

    fun numberOfTurns(): Int {
        return turns().count()
    }

    fun turns(): Iterable<Turn> {
        return extractParts()[TURN_STRING].split(COLOUR_DELIMETER.toRegex()).map { Turn(it) }
    }

    private fun extractParts() = ("Game (\\d+): (.*)".toRegex()).find(testInput)!!.groupValues

    fun isPossibleFor(cubes: Cubes) : Boolean {
        return turns().all { cubes.isPossibleTurn(it) }
    }

    fun findMinimumCubes(): Cubes {
        return Cubes(turns().maxOf { it.red() }, turns().maxOf { it.green() }, turns().maxOf { it.blue() })
    }

    class Cubes(val red: Int, val green: Int, val blue: Int) {
        fun isPossibleTurn(turn: Turn) : Boolean {
            return turn.red() <= red && turn.green() <= green && turn.blue() <= blue
        }

        fun power(): Int {
            return red * green * blue
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
