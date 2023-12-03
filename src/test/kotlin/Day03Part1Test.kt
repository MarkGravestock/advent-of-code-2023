import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class Day03Part1Test : FunSpec ({
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
    }
})

private fun Char.isSymbol(): Boolean {
    return !(this.isDigit() || this == '.')
}

class EngineSchematic(private val fileInput: List<String>) {
    fun lines(): Int {
        return fileInput.size
    }

    fun candidateNumbers(): Iterable<CandidatePartNumber> {
        return fileInput.flatMapIndexed { index, it ->  EngineSchematicLine(it, index).partNumbers() }
            .map{ CandidatePartNumber(it, bounds()) }
    }

    fun bounds(): Bounds {
        return Bounds(IntRange(0, fileInput.size - 1), IntRange(0, fileInput.first().length - 1))
    }

    fun doesCandidateMatch(candidatePartNumber: CandidatePartNumber) : Boolean {
        val bounds = candidatePartNumber.bounds()
        return fileInput.subList(bounds.height.first, bounds.height.last + 1)
            .map { it.subSequence(bounds.width.first, bounds.width.last + 1) }
            .also { it.log() }
            .any { it.any { it.isSymbol() } }
    }
}

class Bounds(val height: IntRange, val width: IntRange)

class CandidatePartNumber(private val partNumber: PartNumber, private val bounds: Bounds) {
    fun bounds() : Bounds {
        val height = IntRange(maxOf(partNumber.lineNumber - 1, bounds.height.first), minOf(partNumber.lineNumber + 1, bounds.height.last))
        return Bounds(width = lineBounds(), height = height)
    }

    fun lineBounds() : IntRange {
        return IntRange(maxOf(partNumber.start - 1, bounds.width.first), minOf(partNumber.end + 1, bounds.width.last))
    }
}

class EngineSchematicLine(private val line: String, private val lineNumber: Int) {
    fun partNumbers(): Iterable<PartNumber> {
        val regex = "(\\d+)".toRegex()
        val matches = regex.findAll(line)
        return matches.map { PartNumber(it.value.toInt(), it.range.first, it.range.last, lineNumber) }.asIterable()
    }
}

class PartNumber(val value: Int, val start: Int, val end: Int, val lineNumber: Int)

