package day10

import println
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

enum class Directions(val offset: Coordinate) {
    North(Coordinate(0, -1)),
    East(Coordinate(1, 0)),
    South(Coordinate(0, 1)),
    West(Coordinate(-1, 0))
}

val start = mapOf(Directions.North to "|F7", Directions.South to "|LJ", Directions.West to "-LF", Directions.East to "-7J")
val hyphen = mapOf(Directions.North to "", Directions.South to "", Directions.West to "-LF", Directions.East to "-7J")
val bar = mapOf(Directions.North to "|F7", Directions.South to "|LJ", Directions.West to "", Directions.East to "")
val L = mapOf(Directions.North to "|F7", Directions.South to "", Directions.West to "", Directions.East to "-7J")
val J = mapOf(Directions.North to "|F7", Directions.South to "", Directions.West to "-LF", Directions.East to "")
val F = mapOf(Directions.North to "", Directions.South to "|LJ", Directions.West to "", Directions.East to "-7J")
var seven = mapOf(Directions.North to "", Directions.South to "|LJ", Directions.West to "-LF", Directions.East to "")
val directionForCurrentPipe = mapOf('-' to hyphen, '|' to bar, 'L' to L, 'J' to J, 'F' to F, '7' to seven, 'S' to start)

class PipeMap(val fileInput: List<String>) {

    private val start = Coordinate(x = fileInput.single { it.contains("S") }.indexOfFirst { it == 'S' }, y = fileInput.indexOfFirst { it.contains("S") })
    fun start(): Coordinate {
        return start
    }

    fun at(coordinate: Coordinate) : Char {
       return fileInput[coordinate.y][coordinate.x]
    }

    fun movesAt(coordinate: Coordinate) : List<Coordinate> {
        val moves = directionForCurrentPipe[at(coordinate)]!!.entries

        return moves.map { Pair(coordinate.move(it.key.offset), it.value) }
            .filter { inBounds(it.first) }
            .filter { it.second.contains(at(it.first)) || at(it.first) == 'S' }
            .map { it.first }
    }

    private fun inBounds(coordinate: Coordinate): Boolean {
        return coordinate.y >= 0 && coordinate.y < fileInput.size && coordinate.x < fileInput.first().length && coordinate.x >= 0
    }

    fun nextBestMoveAt(coordinate: Coordinate): Coordinate {
        return movesAt(coordinate).maxBy { it.distanceFrom(start) }
    }

    fun findMaxLocation() : Pair<Coordinate, Int> {
        val visitedLocations = mutableListOf<Coordinate>()
        val nextLocations = mutableListOf<Coordinate>()
        var lastLocation = start()

        visitedLocations.add(lastLocation)
        nextLocations.add(movesAt(lastLocation).first())

        while (nextLocations.isNotEmpty()) {

            val nextLocation = nextLocations.first().also { it.println() }

            if (abs(nextLocation.x - lastLocation.x) > 1 || abs(nextLocation.y - lastLocation.y) > 1) error("next ${nextLocation} last ${lastLocation} ${visitedLocations.size}")

            nextLocations.remove(nextLocation)
            visitedLocations.add(nextLocation)

            val nextMoves = movesAt(nextLocation).filter { !visitedLocations.contains(it) }.filter { !nextLocations.contains(it) }

            nextLocations.addAll(0, nextMoves)
            lastLocation = nextLocation
        }

        val maxDistance = ceil(visitedLocations.size.toDouble() / 2.toDouble()).toInt()

        return Pair(visitedLocations[maxDistance], maxDistance)
    }

}

data class Coordinate(val x: Int, val y: Int) {
    fun move(offset: Coordinate) : Coordinate {
        return Coordinate(x + offset.x, y + offset.y)
    }

    fun distanceFrom(start: Coordinate): Double {
       return sqrt((x - start.x).toDouble().pow(2) + (y - start.y).toDouble().pow(2))
    }
}
