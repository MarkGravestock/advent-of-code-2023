import day05.Almanac
import day05.Mapping
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
                row(97L, 99),
                row(99L, 51),
                row(0L, 0),
            ) { source, destination ->
                first.mapSourceOf(source) shouldBe destination
            }
        }

        test("It can map from original source to final destination") {
            forAll(
                row(79L, 82),
                row(14L, 43),
                row(13L, 35),
                row(55L, 86),
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
                row(97L, false),
                row(98L, true),
                row(99L, true),
                row(100L, false)
            ) { source, expectedCanMap ->
                sut.canMap(source) shouldBe expectedCanMap
            }
        }

        test("It maps value") {
            forAll(
                row(97L, 97),
                row(98L, 50),
                row(99L, 51),
                row(100L, 100),
            ) { source, destination ->
                sut.mappedValue(source) shouldBe destination
            }
        }
    }

        context("Real File Seeds logic for Almanac") {

        val fileInput = readInputForDay(day)
        val sut = Almanac(fileInput)

        test("It can find lowest final destination") {
            sut.findLowestFinalDestination() shouldBe 240320250
        }
    }

})

