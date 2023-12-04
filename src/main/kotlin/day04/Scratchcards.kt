package day04

import println
import kotlin.math.pow

class Scratchcards(private val fileInput: List<String>) {
    fun lines(): Int {
        return fileInput.size
    }

    fun totalPoints(): Int {
        return points().sum()
    }

    fun points(): List<Int> {
        return fileInput.map{ Scratchcard(it).points() }
    }

    fun scratchcardsWon(): List<OriginalToCopies> {
        return fileInput.map{ Scratchcard(it).cardsWon() }
            .also { it.println() }
            .mapIndexed { index, value -> OriginalToCopies(index + 1,  IntRange(index + 2, minOf(index +  1 + value, fileInput.size)).toList()) }
    }

    fun totalScratchcardsWon(): Int {
        return originalAndCopyScratchcardsWon().values.sum()
    }

    fun originalAndCopyScratchcardsWon(): Map<Int, Int> {
        val originalToCopies = scratchcardsWon().associate { it.originalCardNumber to it.copiesCardNumbers }

        val originalScratchcards = scratchcardsWon().map { it.originalCardNumber }

        val allScratchcards = mutableListOf<Int>().apply { addAll(originalScratchcards) }

        var copiesWon = scratchcardsWon().flatMap { it.copiesCardNumbers }

        while (copiesWon.isNotEmpty()) {
            allScratchcards.addAll(copiesWon)
            copiesWon = copiesWon.mapNotNull { originalToCopies[it] }.flatten()
        }

        return allScratchcards.groupingBy { it }.eachCount()
    }
}

data class OriginalToCopies(val originalCardNumber: Int, val copiesCardNumbers: List<Int>)

class Scratchcard(private val line: String) {

    private fun extractParts() = ("Card\\s+(\\d+): (.*) \\| (.*)".toRegex()).find(line)!!.groupValues

    fun lineNumber(): Int {
        return extractParts()[1].toInt()
    }

    fun winningNumbers(): Set<Int> {
        return extractNumbersFromGroup(2)
    }

    fun cardNumbers(): Set<Int> {
        return extractNumbersFromGroup(3)
    }

    private fun extractNumbersFromGroup(group: Int) = extractParts()[group].split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toHashSet()

    fun cardWinningNumbers(): Set<Int> {
        return winningNumbers() intersect cardNumbers()
    }

    fun points(): Int {
        return 2.0.pow(cardsWon() - 1).toInt()
    }

    fun cardsWon(): Int {
        return cardWinningNumbers().size
    }
}
