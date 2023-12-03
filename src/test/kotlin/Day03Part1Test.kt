import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class Day03Part1Test : FunSpec ({
    context("Schematic line") {

        val line = "..35..633."
        val sut = EngineSchematicLine(line)

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

        test("It can calculate bounds") {
            sut.bounds().height.first shouldBe 0
            sut.bounds().height.last shouldBe 9
            sut.bounds().width.first shouldBe 0
            sut.bounds().width.last shouldBe 9
        }

        test("It calculate the bounds of the first one") {
            sut.candidateNumbers().first().lineBounds().first shouldBe 0
        }
    }

})

class EngineSchematic(private val fileInput: List<String>) {
    fun lines(): Int {
        return fileInput.size
    }

    fun candidateNumbers(): Iterable<CandidatePartNumber> {
        return fileInput.flatMap { EngineSchematicLine(it).partNumbers() }.map{ CandidatePartNumber(it) }
    }

    fun bounds(): Bounds {
        return Bounds(IntRange(0, fileInput.size - 1), IntRange(0, fileInput.first().length - 1))
    }
}

class Bounds(val height: IntRange, val width: IntRange)

class CandidatePartNumber(val partNumber: PartNumber) {
    fun lineBounds() : IntRange {
        return IntRange(10, 0)
    }

}

class EngineSchematicLine(private val line: String) {
    fun partNumbers(): Iterable<PartNumber> {
        val regex = "(\\d+)".toRegex()
        val matches = regex.findAll(line)
        return matches.map { PartNumber(it.value.toInt(), it.range.first, it.range.last) }.asIterable()
    }
}

class PartNumber(val value: Int, val start: Int, val end: Int)
