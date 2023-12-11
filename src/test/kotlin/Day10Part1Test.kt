import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
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

    private val start = Coordinate(x = fileInput.filter { it.contains("S") }.single().indexOfFirst { it == 'S' }, y = fileInput.indexOfFirst { it.contains("S") })
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

class Day10Part1Test : FunSpec({
    context("Pipe Map") {

        val fileInput = readTestInputForDay(10)
        val sut = PipeMap(fileInput)

        test("It can count lines") {
            sut.start() shouldBe Coordinate(1, 1)
        }

        test("It can find moves") {
            sut.movesAt(Coordinate(1, 1)) shouldContainExactlyInAnyOrder listOf(Coordinate(1,2), Coordinate(2 ,1))
            sut.movesAt(Coordinate(2, 1)) shouldContainExactlyInAnyOrder listOf(Coordinate(3 ,1), Coordinate(1, 1))
            sut.movesAt(Coordinate(1, 2)) shouldContainExactlyInAnyOrder listOf(Coordinate(1 ,3), Coordinate(1, 1))
            sut.movesAt(Coordinate(1, 3)) shouldContainExactlyInAnyOrder listOf(Coordinate(1 ,2), Coordinate(2,3))
        }

        test("It can find the best next move") {
            sut.nextBestMoveAt(Coordinate(1, 2)) shouldBe Coordinate(1, 3)
        }

        test("It can find the max location") {
            sut.findMaxLocation().first shouldBe Coordinate(3, 3)
            sut.findMaxLocation().second shouldBe 4
        }
    }

    context("TestPipe Map 1") {

        val fileInput = readTestInputForDay(10, 1)
        val sut = PipeMap(fileInput)

        test("It can find the max location") {
            sut.findMaxLocation().first shouldBe Coordinate(2, 2)
            sut.findMaxLocation().second shouldBe 3
        }
    }

    context("TestPipe Map 2") {

        val fileInput = readTestInputForDay(10, 2)
        val sut = PipeMap(fileInput)

        test("It can find the max location") {
            sut.findMaxLocation().first shouldBe Coordinate(4, 2)
            sut.findMaxLocation().second shouldBe 8
        }

        test("It can find the start") {
            sut.start() shouldBe Coordinate(0, 2)
        }
    }

    context("Part 1 Map") {

        val fileInput = readInputForDay(10)
        val sut = PipeMap(fileInput)

        test("It can find the max steps") {
            sut.findMaxLocation().second shouldBe 7012
        }

        test("It can find moves") {
            sut.movesAt(Coordinate(92, 86))  shouldContainExactlyInAnyOrder listOf(Coordinate(93, 86), Coordinate(91, 86))
        }
    }
})

