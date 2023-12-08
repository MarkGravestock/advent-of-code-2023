import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

// https://adventofcode.com/2023/day/8

class Day08Part1Test : FunSpec({

    context("Map") {
        val fileInput = readTestInputForDay(8)
        val sut = ElfMap(fileInput)

        test("It can calculate the hand type") {
            forAll(
                row("AAA", "L", "BBB"),
                row("DDD", "R", "DDD")
            ) { currentLocation, instruction, nextLocation ->
                sut.navigateStep(currentLocation, instruction) shouldBe nextLocation
            }
        }

        test("It can navigate") {
            sut.calculateStepsToEndUsing("RL") shouldBe 2
            sut.calculateStepsToEnd() shouldBe 2
        }

        test("It can navigate another map") {
            val another = ElfMap(
                listOf(
                    "LLR",
                    "",
                    "AAA = (BBB, BBB)",
                    "BBB = (AAA, ZZZ)",
                    "ZZZ = (ZZZ, ZZZ"
                )
            )

            another.calculateStepsToEndUsing("LLR") shouldBe 6
            another.calculateStepsToEnd()
        }

    }

    context("Part 1 Real Map") {
        val fileInput = readInputForDay(8)
        val sut = ElfMap(fileInput)
        test("It can navigate") {
            sut.calculateStepsToEnd() shouldBe 21409
        }
    }
})

class ElfMap(val fileInput: List<String>) {

    private val linePattern = """(\w+)\s*=\s*\((\w+),\s*(\w+)\)""".toRegex()

    private val instructions: String = fileInput.first()
    private val mapLines = fileInput.drop(2)
        .mapNotNull { linePattern.find(it)?.groupValues }
        .also { println(it) }
        .associate { it[1] to MapLine(it[2], it[3]) }

    fun calculateStepsToEnd(): Int {
        return calculateStepsToEndUsing(instructions)
    }
    fun calculateStepsToEndUsing(instructions: String): Int {
        var steps = 0
        var index = 0
        var location = "AAA"

        while (location != "ZZZ") {
            location = navigateStep(location, instructions[index].toString())
            steps++
            index = (++index % instructions.length)
        }

        return steps
    }

    fun navigateStep(location: String, instruction: String): String {
        return mapLines[location]!!.navigate(instruction)
    }

}

class MapLine(private val leftLocation: String, private val rightLocation: String) {
    fun navigate(instruction: String): String {
        return when (instruction) {
            "L" -> leftLocation
            "R" -> rightLocation
            else -> throw IllegalStateException()
        }
    }
}

