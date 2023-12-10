package day09

import parseNumbers

class Oasis(private val oasisReport: List<String>) {
    fun rowNumbers(): List<Sequence<Long>> {
        return oasisReport.map { it.parseNumbers() }
    }

    fun generateDifference(sequence: Sequence<Long>): Sequence<Long> {
        return sequence.windowed(size = 2).map { it[1] - it[0] }
    }

    suspend fun generateDifferenceUntilZero(first: Sequence<Long>): Sequence<Sequence<Long>> = sequence {
        var nextValue = first
        yield(first)
        while (nextValue.any { it != 0L }) {
            nextValue = generateDifference(nextValue)
            yield(nextValue)
        }
    }

    enum class Direction(val extractor: (Sequence<Long>) -> Long, val fold: (Long, Long) -> Long) {
        Backwards(extractor = {it.first()}, fold = { accumulator, current -> current - accumulator }),
        Forwards(extractor = {it.last()}, fold = { accumulator, current -> current + accumulator })
    }

    fun generateHistory(differences: Sequence<Sequence<Long>>, direction: Direction): Long {
        return differences.map { direction.extractor(it) }
            .toList()
            .reversed()
            .runningFold(0L) { x, y -> direction.fold(x, y) }
            .last()
    }

    suspend fun totalHistory(direction: Direction = Direction.Forwards): Long {
        return rowNumbers().sumOf { generateHistory(generateDifferenceUntilZero(it), direction) }
    }
}
