import day04.Scratchcard
import day04.Scratchcards
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class Day04Part2Test : FunSpec({
    val day = 4
    val fileNameBase = "Day0${day}"

    context("Scratchcard line") {

        val line = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val sut = Scratchcard(line)

        test("It can find the number of cards won") {
            sut.cardsWon() shouldBe 4
        }
    }

    context("Scratchcards File") {

        val fileInput = readInput("${fileNameBase}_test")
        val sut = Scratchcards(fileInput)

        test("It can calculate scratchcards won") {
            sut.scratchcardsWon().first() shouldContainExactlyInAnyOrder listOf(2, 3, 4, 5)
            sut.scratchcardsWon().last() shouldBe emptyList()
        }

        test("It can calculate total points") {
            sut.totalPoints() shouldBe 13
        }
    }

    context("Real File Assertions") {
        val fileInput = readInput(fileNameBase)
        val sut = Scratchcards(fileInput)

        test("It can calculate total points") {
            sut.totalPoints() shouldBe 20667
        }
    }
})

