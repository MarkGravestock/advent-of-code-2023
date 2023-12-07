import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import kotlin.math.pow

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

class Hands(private val fileInput: List<String>) {
    fun rank(): List<Hand> {
        return fileInput.map { it.split(" ") }.map { Hand(it[0], it[1].toInt()) }.sortedWith(compareBy<Hand>{ it.type().rank }.thenBy { it.rank() })
    }

    fun totalWinnings(): Int {
        return rank().mapIndexed {index, hand -> hand.bid * (index + 1) }.sum()
    }
}

data class Hand(private val hand: String, val bid: Int = 0) {
    fun type(): HandType {
        val groupedCards = hand.groupBy { it }
        val largestGroup = groupedCards.entries
            .flatMap { listOf(it.value.size to it.key) }
            .sortedByDescending { it.first }

        return HandType.entries.single { it.matches(groupedCards.keys.count(), largestGroup.first().first) }
    }

    private val cardRank = "AKQJT98765432".reversed()
    fun rank(): Int {
        return hand.reversed().asSequence().foldIndexed(0) { index, acc, char -> acc + cardRank.indexOf(char) * cardRank.length.toDouble().pow(index).toInt() }
    }
   infix fun isStrongerThan(otherHand: Hand): Boolean {
        val firstDifference = hand.zip(otherHand.hand).first { it.first != it.second }
        return firstDifference.first > firstDifference.second
    }
}

enum class HandType(private val groupCount: Int, private val largestGroup: Int, val rank: Int) {
    FiveOfAKind(1, 5, 7),
    FourOfAKind(2, 4, 6),
    FullHouse(2, 3, 5),
    ThreeOfAKind(3, 3, 4),
    TwoPair(3, 2, 3),
    OnePair(4, 2, 2),
    HighCard(5, 1, 1);

    fun matches(groupCount: Int, largestGroup: Int): Boolean {
        return this.groupCount == groupCount && this.largestGroup == largestGroup
    }
}

