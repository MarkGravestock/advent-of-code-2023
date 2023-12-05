import day04.OriginalToCopies
import day04.Scratchcard
import day04.Scratchcards
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class Day04Part2Test : FunSpec({
    val day = 4

    context("Scratchcard line") {

        val line = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val sut = Scratchcard(line)

        test("It can find the number of cards won") {
            sut.cardsWon() shouldBe 4
        }
    }

    context("Scratchcards File") {

        val fileInput = readTestInputForDay(day)
        val sut = Scratchcards(fileInput)

        test("It can calculate scratchcards won") {
            sut.scratchcardsWon().first().copiesCardNumbers shouldContainExactlyInAnyOrder listOf(2, 3, 4, 5)
            sut.scratchcardsWon().last() shouldBe OriginalToCopies(6, emptyList())
        }

        test("It can calculate the copies of a scratchcard won") {
            sut.originalAndCopyScratchcardsWon().values shouldContainExactly listOf(1, 2, 4, 8, 14, 1)
        }

        test("It can calculate total scratchcards won") {
            sut.totalScratchcardsWon() shouldBe 30
        }
    }

    context("Real File Assertions") {
        val fileInput = readInputForDay(day)
        val sut = Scratchcards(fileInput)

        test("It can calculate total scratchcards won") {
            sut.totalScratchcardsWon() shouldBe 5833065
        }
    }
})

