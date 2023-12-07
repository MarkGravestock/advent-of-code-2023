import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

// https://adventofcode.com/2023/day/7

class Day07Part2Test : FunSpec({

    context("Camel Card Part 2 Hand") {
        test("It can calculate the hand type") {
            forAll(
                row("32T3K", HandType.OnePair),
                row("KK677", HandType.TwoPair),
                row("T55J5", HandType.FourOfAKind),
                row("QQQJA", HandType.FourOfAKind),
                row("KTJJT", HandType.FourOfAKind),
                row("7JJ7J", HandType.FiveOfAKind),
                row("JJJJJ", HandType.FiveOfAKind),
            ) { hand, type ->
                Hand(hand, rules = Rules.Part2).type() shouldBe type
            }
        }
        test("It can calculate the stronger hand") {
            forAll(
                row("QQQJA", "T55J5"),
                row("KTJJT", "QQQJA"),
            ) { first, second ->
                Hand(first, rules = Rules.Part2).rank() shouldBeGreaterThan Hand(second, rules = Rules.Part2).rank()
            }
        }
    }

    context("Camel Card Part 2 Hands") {
        val fileInput = readTestInputForDay(7)
        val sut = Hands(fileInput, rules = Rules.Part2)

        test("It can sort hands") {

            sut.totalWinnings() shouldBe 5905
        }
    }

    context("Camel Card Real Part 2 Hand") {
        val fileInput = readInputForDay(7)
        val sut = Hands(fileInput, rules = Rules.Part2)

        test("It can sort hands") {

            sut.totalWinnings() shouldBe 250506580
        }
    }
})

