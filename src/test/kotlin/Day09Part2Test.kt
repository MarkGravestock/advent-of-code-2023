import day09.Oasis
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day09Part2Test : FunSpec({

    context("Part 2") {
        test("It can produce the previous values") {
            forAll(
                row("0 3 6 9 12 15", -3L),
                row("1 3 6 10 15 21", 0),
                row("10  13  16  21  30  45", 5L),
            ) { list, result ->
                val sut = Oasis(listOf(list))

                val differenceRows = sut.generateDifferenceUntilZero(sut.rowNumbers().first())

                sut.generateHistory(differenceRows, direction = Oasis.Direction.Backwards) shouldBe result
            }
        }

        val fileInput = readTestInputForDay(9)
        val sut = Oasis(fileInput)

        test("It can calculate total history") {
            sut.totalHistory(Oasis.Direction.Backwards) shouldBe 2L
        }

    }

    context("Part 2 Real File") {
        val fileInput = readInputForDay(9)
        val sut = Oasis(fileInput)

        test("It can calculate total history") {
            sut.totalHistory(Oasis.Direction.Backwards) shouldBe 990L
        }
    }
})

