import day04.Scratchcard
import day04.Scratchcards
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class Day04Part1Test : FunSpec({
    val day = 4

    context("Scratchcard Line") {

        val line = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val sut = Scratchcard(line)

        test("It can find the card number") {
            sut.lineNumber() shouldBe 1
        }

        test("It can find the wining numbers") {
            sut.winningNumbers() shouldHaveSize 5
        }

        test("It can find the card numbers") {
            sut.cardNumbers() shouldHaveSize 8
        }

        test("It can find the card winning numbers") {
            sut.cardWinningNumbers() shouldContainExactlyInAnyOrder setOf(48, 83, 17, 86)
        }

        test("It can find the card points") {
            sut.points() shouldBe 8
        }
    }

    context("Scratchcard Test File") {

        val fileInput = readTestInputForDay(day)
        val sut = Scratchcards(fileInput)

        test("It can count lines") {
            sut.lines() shouldBe 6
        }

        test("It can calculate points") {
            sut.points() shouldContainExactlyInAnyOrder listOf(8, 2, 2, 1, 0, 0)
        }

        test("It can calculate total points") {
            sut.totalPoints() shouldBe 13
        }
    }

    context("Real File Assertions") {
        val fileInput = readInputForDay(day)
        val sut = Scratchcards(fileInput)

        test("It can count lines") {
            sut.lines() shouldBe 211
        }

        test("It can calculate total points") {
            sut.totalPoints() shouldBe 20667
        }
    }

    context("Real Scratchcard line") {

        val line = "Card   1: 82 41 56 54 18 62 29 55 34 20 | 37 14 10 80 58 11 65 96 90  8 59 32 53 21 98 83 17  9 87 25 71 77 70 73 24"
        val sut = Scratchcard(line)

        test("It can find the wining numbers") {
            sut.winningNumbers() shouldHaveSize 10
        }
    }
})

