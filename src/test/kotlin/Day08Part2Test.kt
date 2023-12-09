import day08.ElfMap
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day08Part2Test : FunSpec({


    context("Part 2 Test Map") {
        val fileInput = readTestInputForDay(8, 2)
        val sut = ElfMap(fileInput)

        test("It can find when all ghosts have reached the end") {
            sut.calculateGhostStepsToEnd() shouldBe 6
        }
    }

    context("Part 2 Real Map") {
        val fileInput = readInputForDay(8)
        val sut = ElfMap(fileInput)

        test("It can find when all ghosts have reached the end") {
            //sut.calculateGhostStepsToEnd() shouldBe 6
        }

        test("It can find when first ghost has reached the end") {
          //  sut.calculateGhostStepsToEndForFirst() shouldBe 6
        }

    }
})


