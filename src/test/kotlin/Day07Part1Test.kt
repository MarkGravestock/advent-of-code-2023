import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

// https://adventofcode.com/2023/day/7

class Day07Part1Test : FunSpec({

    context("Camel Card Hand") {
        test("It can calculate the hand type") {
            forAll(
                row("AAAAA", HandType.FiveOfAKind),
                row("AA8AA", HandType.FourOfAKind),
                row("23332", HandType.FullHouse),
                row("TTT98", HandType.ThreeOfAKind),
                row("23432", HandType.TwoPair),
                row("A23A4", HandType.OnePair),
                row("23456", HandType.HighCard),
            ) { hand, type ->
                Hand(hand).type() shouldBe type
            }
        }
        test("It can calculate the stronger hand") {
            forAll(
                row("33332", "2AAAA", true),
                row("77888", "77788", true),
            ) { first, second, stronger ->
                Hand(first).isStrongerThan(Hand(second)) shouldBe stronger

                Hand(first).rank() shouldBeGreaterThan Hand(second).rank()
            }
        }
    }

    context("Camel Card Hands") {
        val fileInput = readTestInputForDay(7)
        val sut = Hands(fileInput)

        test("It can sort hands") {

            sut.rank().shouldContainExactly(listOf(Hand("32T3K", 765), Hand("KTJJT", 220), Hand("KK677", 28), Hand("T55J5", 684), Hand("QQQJA", 483)))
        }

        test("It can sort hands") {

            sut.totalWinnings() shouldBe 6440
        }
    }

    context("Camel Card Real Hand") {
        val fileInput = readInputForDay(7)
        val sut = Hands(fileInput)

        test("It can sort hands") {

            sut.totalWinnings() shouldBe 250058342
        }
    }
})

