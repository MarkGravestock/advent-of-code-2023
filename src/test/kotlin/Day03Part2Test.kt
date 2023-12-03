import day03.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class Day03Part2Test : FunSpec({
    context("Schematic") {

        val fileInput = readInput("Day03_test")
        val sut = EngineSchematic(fileInput)

        test("It can find adjacent symbol") {
            val candidate = sut.candidateNumbers().first()
            val actual = sut.doesCandidateMatchSecond(candidate)
            actual shouldBe SymbolMatch(1, 3, candidate)
        }

        test("It can find gears") {
            sut.findGears() shouldHaveSize 2
        }

        test("It can calculate gear ratio") {
            sut.findGears().first().ratio shouldBe 16345
        }

        test("It can find the Total gear ratios") {
            sut.totalGearRatios() shouldBe 467835
        }

    }

    context("Real File Assertions") {
        val fileInput = readInput("Day03")
        val sut = EngineSchematic(fileInput)

        test("Contains expected Total gear ratios") {
            sut.totalGearRatios() shouldBe 75519888
        }
    }
})

