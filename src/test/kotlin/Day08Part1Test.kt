import day08.ElfMap
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

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
            val another = ElfMap(readTestInputForDay(8, 1))

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

