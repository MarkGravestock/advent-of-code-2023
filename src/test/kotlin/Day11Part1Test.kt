import day10.Coordinate
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength
import kotlin.math.abs

class Day11Part1Test : FunSpec({
    context("Test Data") {
        val fileInput = readTestInputForDay(11)
        val sut = Universe(fileInput)

        test("It can expand") {
            sut.expand()
            sut.universe shouldHaveSize 12
            sut.universe.first() shouldHaveLength 13
        }

        test("It has galaxies") {
            sut.galaxies() shouldHaveSize 42
        }

        test("It generates pairs of galaxies") {
            sut.galaxyPairs() shouldHaveSize 36
        }

        test("It can calculate the distance between galaxies") {
            sut.manhattanDistance(Galaxy(1, 6), Galaxy(5, 11)) shouldBe 9
            sut.manhattanDistance(Galaxy(0, 11), Galaxy(5, 11)) shouldBe 5
            sut.manhattanDistance(Galaxy(0, 2), Galaxy(12, 7)) shouldBe 17
            sut.manhattanDistance(Galaxy(4, 0), Galaxy(9, 10)) shouldBe 15
        }

        test("It can calculate the shortest path") {
            sut.shortestPath() shouldBe 374
        }
    }

    context("Part 1 Test") {
        val fileInput = readInputForDay(11)
        val sut = Universe(fileInput)

        test("It can calculate the shortest path") {
            sut.shortestPath() shouldBe 9724940
        }
    }
})

class Universe(val fileInput: List<String>) {

    var universe = fileInput.toMutableList()
    fun expand() {
        var result = universe.expandRows()
        result = result.transpose()
        result = result.expandRows()
        universe = result.transpose().toMutableList()
    }

    private fun MutableList<String>.expandRows(): MutableList<String> {
        val result = mutableListOf<String>()
        forEach {
            result.add(it)
            if (it.all { it == '.' }) result.add(it)
        }
        return result
    }

    fun MutableList<String>.transpose(): MutableList<String> {
        if (isEmpty()) return mutableListOf()

        val columnCount = first().length
        return (0 until columnCount).map { columnIndex ->
            map { it[columnIndex] }.joinToString("")
        }.toMutableList()
    }

    fun galaxies(): List<Galaxy> {
        expand()

        return findGalaxies()
    }

    fun galaxyPairs(): List<Pair<Galaxy, Galaxy>> {
        return generatePermutations(galaxies())
    }


    fun manhattanDistance(galaxy1: Galaxy, galaxy2: Galaxy): Int {
        return abs(galaxy1.x - galaxy2.x) + abs(galaxy1.y - galaxy2.y)
    }

    fun shortestPath(): Int {
        return galaxyPairs().sumOf { manhattanDistance(it.first, it.second) }
    }

    private fun findGalaxies() =
        universe.mapIndexedNotNull() { index: Int, it: String -> if (it.contains("#")) Pair(index, it) else null }
            .flatMap {
                val index = it.first
                it.second.mapIndexedNotNull { charIndex: Int, char: Char -> if (char == '#') Galaxy(charIndex, index) else null }
            }

    fun <T> generatePermutations(list: List<T>): List<Pair<T, T>> {
        val pairs = mutableListOf<Pair<T, T>>()

        for (i in list.indices) {
            for (j in i + 1 until list.size) {
                pairs.add(Pair(list[i], list[j]))
            }
        }

        return pairs
    }


}

typealias Galaxy = Coordinate
