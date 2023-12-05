import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe

// https://adventofcode.com/2023/day/5

class Day05Part1Test : FunSpec({
    val day = 5

    context("Seeds logic for Almanac") {

        val fileInput = readTestInputForDay(day)
        val sut = Almanac(fileInput)

        test("It can find the seeds") {
            sut.seeds() shouldHaveSize 4
        }

        test("It can find the mappings") {
            sut.maps() shouldHaveSize 7
            sut.maps()["light-to-temperature map:"]?.size() shouldBe 3
        }

        test("It can map a source to destination") {
            val first = sut.maps().values.first()

            forAll(
                row(97, 99),
                row(99, 51),
                row(0, 0),
            ) { source, destination ->
                first.mapSourceOf(source) shouldBe destination
            }
        }

        test("It can map from original source to final destination") {
            forAll(
                row(79, 82),
                row(14, 43),
                row(13, 35),
                row(55, 86),
            ) { source, destination ->
                sut.mapOriginalSourceOf(source) shouldBe destination
            }
        }

        test("It can find lowest final destination") {
            sut.findLowestFinalDestination() shouldBe 35
        }
    }

    context("Mapping logic") {
        val sut = Mapping(destinationRangeStart = 50, sourceRangeStart = 98, rangeLength = 2)

        test("It can map") {
            forAll(
                row(97, false),
                row(98, true),
                row(99, true),
                row(100, false)
            ) { source, expectedCanMap ->
                sut.canMap(source) shouldBe expectedCanMap
            }
        }

        test("It maps value") {
            forAll(
                row(97, 97),
                row(98, 50),
                row(99, 51),
                row(100, 100),
            ) { source, destination ->
                sut.mappedValue(source) shouldBe destination
            }
        }
    }

})

class Almanac(private val fileInput: List<String>) {
    private fun extractSeedParts(line: String) = ("seeds: ((?:\\d+\\s*)+)".toRegex()).find(line)
    fun seeds(): List<Int> {
        val seeds = extractSeedParts(fileInput.first())
        return seeds!!.groups[1]?.value?.trim()?.split("\\s+".toRegex())!!.map { it.toInt() }
    }

    fun maps(): Map<String, CategoryMap> {
        val categoryMaps = mutableMapOf<String, CategoryMap>()
        var currentMap = CategoryMap.empty()

        for (line in 2..<fileInput.size) {
            val lineContent = fileInput[line]

            if (lineContent.isBlank()) continue

            if (lineContent.contains(" map:")) {
                currentMap = CategoryMap(lineContent)
                categoryMaps[lineContent] = currentMap
            } else {
                val (destinationRangeStart, sourceRangeStart, rangeLength) = lineContent.split(" ").map { it.toInt() }
                currentMap.addMapping(Mapping(destinationRangeStart, sourceRangeStart, rangeLength))
            }
        }

        return categoryMaps
    }

    fun mapOriginalSourceOf(source: Int): Int {
        var nextValue = source

        for (map in maps()) {
            nextValue = map.value.mapSourceOf(nextValue)
        }

        return nextValue
    }

    fun findLowestFinalDestination(): Int {
        return seeds().minOf { mapOriginalSourceOf(it) }
    }

}

class Mapping(val destinationRangeStart: Int, val sourceRangeStart: Int, val rangeLength: Int) {

    private val range = IntRange(start = sourceRangeStart, endInclusive = sourceRangeStart + rangeLength - 1)
    fun canMap(value: Int): Boolean {
        return range.contains(value)
    }

    fun mappedValue(value: Int): Int {
        return if (canMap(value)) (value - sourceRangeStart) + destinationRangeStart else value
    }

}

class CategoryMap(val line: String) {

    private val mappings: MutableList<Mapping> = mutableListOf()
    fun addMapping(mapping: Mapping) {
        mappings.add(mapping)
    }

    fun size(): Int = mappings.size
    fun mapSourceOf(value: Int): Int {
        val mapping = mappings.singleOrNull { it.canMap(value) }
        return mapping?.mappedValue(value) ?: value
    }

    companion object {
        fun empty(): CategoryMap {
            return CategoryMap("error")
        }
    }

}

