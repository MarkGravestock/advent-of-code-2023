import day09.Oasis
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.sequences.shouldHaveSize
import io.kotest.matchers.shouldBe

class Day09Part1Test : FunSpec({

    context("Oasis Report") {
        test("It can produce a row") {
            val sut = Oasis(listOf("0 3 6 9 12 15"))
            sut.rowNumbers() shouldHaveSize 1
            sut.rowNumbers().first() shouldHaveSize 6
        }

        test("It can produce a row") {
            val sut = Oasis(listOf("0 3 6 9 12 15"))
            sut.rowNumbers() shouldHaveSize 1
            sut.rowNumbers().first() shouldHaveSize 6
        }

        test("It can produce a difference row") {
            val sut = Oasis(listOf("0 3 6 9 12 15"))
            sut.generateDifference(sut.rowNumbers().first()) shouldHaveSize 5
            sut.generateDifference(sut.rowNumbers().first()).all { it == 3L } shouldBe true
        }

        test("It can produce a difference rows until they are all zeros") {
            val sut = Oasis(listOf("0 3 6 9 12 15"))

            sut.generateDifferenceUntilZero(sut.rowNumbers().first()).last().all { it == 0L } shouldBe true
            sut.generateDifferenceUntilZero(sut.rowNumbers().first()).last().count() shouldBe 4
        }

        test("It can produce the next values") {
            forAll(
                row("0 3 6 9 12 15", 18L),
                row("1 3 6 10 15 21", 28L),
                row("10  13  16  21  30  45", 68L),
                row("2, -2, -2, 7, 44, 166, 512, 1375, 3320, 7387, 15464, 31029, 60732, 117883, 230123, 455904, 918822, 1876972, 3859134, 7925415, 16154444", 32533964L)
            ) { list, result ->
                val sut = Oasis(listOf(list))

                val differenceRows = sut.generateDifferenceUntilZero(sut.rowNumbers().first())

                sut.generateHistory(differenceRows, Oasis.Direction.Forwards) shouldBe result
            }
        }
    }

    context("Part 1 Test File") {
        val fileInput = readTestInputForDay(9)
        val sut = Oasis(fileInput)

        test("It can calculate total history") {
            sut.totalHistory() shouldBe 114
        }
    }

    context("Part 1 Real File") {
        val fileInput = readInputForDay(9)
        val sut = Oasis(fileInput)

        test("It can calculate total history") {
            sut.totalHistory() shouldBe 1887980197L
        }
    }
})

