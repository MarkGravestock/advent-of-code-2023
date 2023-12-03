package day03

import log

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

    fun doesCandidateMatch(candidatePartNumber: CandidatePartNumber): Boolean {
        val bounds = candidatePartNumber.bounds()
        return fileInput.subList(bounds.height.first, bounds.height.last + 1)
            .map { it.subSequence(bounds.width.first, bounds.width.last + 1) }
            .also { it.log() }
            .any {
                it.any { char -> char.isSymbol() }
            }
    }

    fun totalOfValidPartNumbers(): Int {
        return candidateNumbers().filter { doesCandidateMatch(it) }.sumOf { it.partNumber.value }
    }
}

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
