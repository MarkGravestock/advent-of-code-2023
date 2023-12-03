import day03.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class Day03Part1Test : FunSpec({
    context("Schematic line") {

        val line = "..35..633."
        val sut = EngineSchematicLine(line, 0)

        test("It can find part numbers") {

            sut.partNumbers() shouldHaveSize 2
        }

        test("It can find their values") {

            sut.partNumbers().first().value shouldBe 35
        }

        test("It can find their location") {

            sut.partNumbers().first().start shouldBe 2
            sut.partNumbers().first().end shouldBe 3
        }
    }

    context("Schematic") {

        val fileInput = readInput("Day03_test")
        val sut = EngineSchematic(fileInput)

        test("It can count lines") {
            sut.lines() shouldBe 10
        }

        test("It can calculate schematic bounds") {
            sut.bounds().height.first shouldBe 0
            sut.bounds().height.last shouldBe 9
            sut.bounds().width.first shouldBe 0
            sut.bounds().width.last shouldBe 9
        }

        test("It calculate the line bounds of the first candidate") {

            val lineBounds = sut.candidateNumbers().first().lineBounds()

            lineBounds.first shouldBe 0
            lineBounds.last shouldBe 3

            val lastHeightBounds = sut.candidateNumbers().last().bounds().width
            lastHeightBounds shouldBe IntRange(4, 8)
        }

        test("It calculate the height bounds of the first candidate") {

            val heightBounds = sut.candidateNumbers().first().bounds().height

            heightBounds.first shouldBe 0
            heightBounds.last shouldBe 1

            val lastHeightBounds = sut.candidateNumbers().last().bounds().height
            lastHeightBounds shouldBe IntRange(8, 9)
        }

        test("It can check adjacent symbol") {
            val actual = sut.doesCandidateMatch(CandidatePartNumber(PartNumber(633, lineNumber = 2, start = 6, end = 8), Bounds(height = IntRange(1, 3), width = IntRange(5, 9))))
            actual shouldBe true
        }

        test("It can calculate total of valid part numbers") {
            sut.totalOfValidPartNumbers() shouldBe 4361
        }

    }

    context("Real File Assertions") {
        val fileInput = readInput("Day03")
        val sut = EngineSchematic(fileInput)

        test("All line are same width") {
            fileInput.groupBy { it.length }.size shouldBe 1
        }

        test("Symbols are identified") {
            fileInput.flatMap { it.asSequence() }.filter { x -> x.isSymbol() }.distinct().joinToString("") shouldBe "/@*\$=&#-+%"
        }

        test("Contains expected Total") {
            sut.totalOfValidPartNumbers() shouldBe 520019
        }
    }
})

