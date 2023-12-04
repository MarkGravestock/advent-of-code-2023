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

    fun scratchcardsWon(): List<List<Int>> {
        return fileInput.map{ Scratchcard(it).cardsWon() }
            .also { it.println() }
            .mapIndexed { index, value -> IntRange(index + 2, minOf(index +  1 + value, fileInput.size)).toList() }
    }
}

class Scratchcard(private val line: String) {

    private fun extractParts() = ("Card\\s+(\\d+): (.*) \\| (.*)".toRegex()).find(line)!!.groupValues

    fun lineNumber(): Int {
        return extractParts()[1].toInt()
    }

    fun winningNumbers(): Set<Int> {
        line.println()
        return extractParts()[2].split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toHashSet()
    }

    fun cardNumbers(): Set<Int> {
        return extractParts()[3].split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toHashSet()
    }

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
