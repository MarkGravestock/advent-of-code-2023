class CalibrationDocument(private val testInput: List<String>) {

    fun totalCalibrationValuesPart2(): Int {
        return testInput.sumOf { lineValueOfPart2(it) }
    }

    fun firstLineValue(): Int {
        return lineValueOf(testInput.first())
    }

    fun totalCalibrationValues(): Int {
        return testInput.sumOf { lineValueOf(it) }
    }

    private fun lineValueOf(line: String): Int {
        return line.first(Char::isDigit).digitToInt() * 10 + line.last(Char::isDigit).digitToInt()
    }

    companion object {
        fun digitAndWordsMap(): Map<String, Int> {
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
            ).mapIndexed { index, value -> Pair(value, index + 1) }
            val digitPairs = (1..9).map { it.toString() }.mapIndexed { index, value -> Pair(value, index + 1) }
            return digitWordsPairs.union(digitPairs).toMap()
        }

        private fun digitsAndWords(): Set<String> {
            return digitAndWordsMap().keys
        }

        fun firstMatch(line: String): String {
            return line.findAnyOf(digitsAndWords())!!.second
        }

        fun lastMatch(line: String): String {
            return line.findLastAnyOf(digitsAndWords())!!.second
        }

        fun lineValueOfPart2(line: String): Int {
            val firstValue = 10 * digitAndWordsMap()[firstMatch(line)]!!
            val secondValue = digitAndWordsMap()[lastMatch(line)]!!
            return firstValue + secondValue
        }
    }

}


