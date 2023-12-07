import kotlin.math.pow

class Hands(private val fileInput: List<String>, val rules: Rules = Rules.Part1 ) {
    fun rank(): List<Hand> {
        return fileInput.map { it.split(" ") }
            .map { Hand(it[0], it[1].toInt(), rules) }
            .sortedWith(compareBy<Hand>{ it.type().rank }.thenBy { it.rank() })
    }

    fun totalWinnings(): Int {
        return rank().mapIndexed {index, hand -> hand.bid * (index + 1) }.sum()
    }
}

data class Hand(private val hand: String, val bid: Int = 0, val rules: Rules = Rules.Part1) {
    fun type(): HandType {
        if (hand == "JJJJJ") return HandType.FiveOfAKind

        val groupedCards = hand.groupBy { it }.toMutableMap()

        val jokers = groupedCards.getOrElse(rules.joker) { listOf() }.size
        groupedCards.remove(rules.joker)

        val largestGroup = groupedCards.entries
            .flatMap { listOf(it.value.size to it.key) }
            .sortedByDescending { it.first }

        return HandType.entries.single { it.matches(groupedCards.keys.count(), largestGroup.first().first + jokers) }
    }

    private val cardRank = rules.cardOrder.reversed()
    fun rank(): Int {
        return hand.reversed().asSequence().foldIndexed(0) { index, acc, char -> acc + cardRank.indexOf(char) * cardRank.length.toDouble().pow(index).toInt() }
    }
}

enum class Rules(val joker: Char, val cardOrder: String) {
    Part1(' ', "AKQJT98765432"),
    Part2('J', "AKQT98765432J")
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
