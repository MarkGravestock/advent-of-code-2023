import day05.Almanac
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

// https://adventofcode.com/2023/day/5

class Day05Part2Test : FunSpec({
    val day = 5

    context("Seeds logic for Almanac Part 2") {

        val fileInput = readTestInputForDay(day)
        val sut = Almanac(fileInput)

        test("It can find the seed ranges") {
            sut.seedRanges() shouldHaveSize 2
        }

        test("It can find the seeds from the ranges") {
            sut.seedsFromRanges() shouldHaveSize 27
        }

        test("It can find lowest final destination") {
            sut.findLowestFinalDestinationFromRanges() shouldBe 46
        }
    }

    context("Real File Seeds logic for Almanac") {

        val fileInput = readInputForDay(day)
        val sut = Almanac(fileInput)

        test("It can find lowest final destination") {
          //  sut.findLowestFinalDestinationFromRanges() shouldBe 240320250
        }
    }

})

