import kotlin.math.pow

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
