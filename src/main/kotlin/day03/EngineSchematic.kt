package day03

import println

class EngineSchematic(private val fileInput: List<String>) {
    fun lines(): Int {
        return fileInput.size
    }

    fun candidateNumbers(): Iterable<CandidatePartNumber> {
        return fileInput.flatMapIndexed { index, it -> EngineSchematicLine(it, index).partNumbers() }
            .map { CandidatePartNumber(it, bounds()) }
    }

    fun bounds(): Bounds {
        return Bounds(IntRange(0, fileInput.size - 1), IntRange(0, fileInput.first().length - 1))
    }

    fun doesCandidateMatch(candidatePartNumber: CandidatePartNumber): SymbolMatch? {
        return doesCandidateMatch(candidatePartNumber) { x -> x.isSymbol() };
    }

    fun doesCandidateMatchSecond(candidatePartNumber: CandidatePartNumber): SymbolMatch? {
        return doesCandidateMatch(candidatePartNumber) { x -> x == '*' }
    }

    private fun doesCandidateMatch(candidatePartNumber: CandidatePartNumber, characterMatcher: (Char) -> Boolean): SymbolMatch? {
        val bounds = candidatePartNumber.bounds()
        return fileInput.subList(bounds.height.first, bounds.height.last + 1)
            .mapIndexed { index, it -> it.subSequence(bounds.width.first, bounds.width.last + 1) to index }
            .flatMap { it.first.flatMapIndexed { columnIndex, char -> listOf(Triple(char, it.second, columnIndex)) } }
            .also { it.println() }
            .mapNotNull{ if (characterMatcher.invoke(it.first)) SymbolMatch(row = it.second + bounds.height.first, column =  it.third + bounds.width.first, candidatePartNumber) else null }
            .singleOrNull()
    }

    fun totalOfValidPartNumbers(): Int {
        return candidateNumbers().mapNotNull { doesCandidateMatch(it) }.sumOf { it.candidatePartNumber.partNumber.value }
    }

    fun findCandidateGears(): Map<Pair<Int, Int>, List<SymbolMatch>> {
        return candidateNumbers().mapNotNull { doesCandidateMatchSecond(it) }.groupBy { Pair(it.row, it.column) }
    }

    fun findGears(): Iterable<Gears> {
        return  findCandidateGears().filterValues { it.size == 2 }.map { Gears(it.value.first().candidatePartNumber, it.value[1].candidatePartNumber, it.key) }
    }

    fun totalGearRatios(): Int {
        return findGears().sumOf { it.ratio }
    }
}

data class Gears(val firstPartNumber: CandidatePartNumber, val secondPartNumber: CandidatePartNumber, val value: Pair<Int,Int>) {
    val ratio: Int = firstPartNumber.partNumber.value * secondPartNumber.partNumber.value
}

data class SymbolMatch(val row: Int, val column: Int, val candidatePartNumber: CandidatePartNumber)

fun Char.isSymbol(): Boolean {
    return !(this.isDigit() || this == '.')
}

class Bounds(val height: IntRange, val width: IntRange)

class CandidatePartNumber(val partNumber: PartNumber, private val bounds: Bounds) {
    fun bounds(): Bounds {
        val height = IntRange(maxOf(partNumber.lineNumber - 1, bounds.height.first), minOf(partNumber.lineNumber + 1, bounds.height.last))
        return Bounds(width = lineBounds(), height = height)
    }

    fun lineBounds(): IntRange {
        return IntRange(maxOf(partNumber.start - 1, bounds.width.first), minOf(partNumber.end + 1, bounds.width.last))
    }
}

class EngineSchematicLine(private val line: String, private val lineNumber: Int) {
    fun partNumbers(): Iterable<PartNumber> {
        val regex = "(\\d+)".toRegex()
        val matches = regex.findAll(line)
        return matches.map { PartNumber(it.value.toInt(), it.range.first, it.range.last, lineNumber) }.asIterable()
    }
}

class PartNumber(val value: Int, val start: Int, val end: Int, val lineNumber: Int)
