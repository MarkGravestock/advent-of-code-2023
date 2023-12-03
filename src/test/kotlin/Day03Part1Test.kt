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

        test("It can find part numbers") {

            sut.lines() shouldBe 10
        }
    }

})

class EngineSchematic(private val fileInput: List<String>) {
    fun lines(): Int {
        return fileInput.size
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
