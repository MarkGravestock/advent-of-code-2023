package day01

import day01.CalibrationDocumentPartOne.Companion.partOne

class CalibrationDocumentPartOne(testInput: List<String>) : CalibrationDocument(testInput, partOne())
{
    companion object {
        fun partOne(): List<Pair<String, Int>> {
            return (1..9).map { it.toString() }.toIndexPair()
        }
    }
}

class CalibrationDocumentPartTwo(testInput: List<String>) : CalibrationDocument(testInput, partTwo())
{
      companion object {

          fun partTwo(): List<Pair<String, Int>> {
              val digitWordsPairs = listOf(
                  "one",
                  "two",
                  "three",
                  "four",
                  "five",
                  "six",
                  "seven",
                  "eight",
                  "nine"
              ).toIndexPair()

              return digitWordsPairs.union(partOne()).toList()
          }
      }
}

abstract class CalibrationDocument(private val testInput: List<String>, private val textToValues: List<Pair<String, Int>>) {

    fun totalCalibrationValues(): Int {
        return testInput.sumOf { lineValueOf(it) }
    }

    fun firstLineValue(): Int {
        return lineValueOfSimple(testInput.first())
    }

    private fun lineValueOfSimple(line: String): Int {
        return line.first(Char::isDigit).digitToInt() * 10 + line.last(Char::isDigit).digitToInt()
    }

    fun firstMatch(line: String): Int {
        return match(line, CharSequence::findAnyOf)
    }

    fun lastMatch(line: String): Int {
        return match(line, CharSequence::findLastAnyOf)
    }

    private fun match(line: String, matcher: (CharSequence, Set<String>) -> Pair<Int, String>?): Int {
        return textToValuesMap().getValue(matcher(line, textToValuesMap().keys)!!.second)
    }

    private fun textToValuesMap() = textToValues.toMap()

    fun lineValueOf(line: String): Int {
        return 10 * firstMatch(line) + lastMatch(line)
    }


    companion object {

        fun List<String>.toIndexPair(): List<Pair<String, Int>> {
            return this.mapIndexed { index, value -> Pair(value, index + 1) }
        }
    }
}


