class CalibrationDocument(testInput: List<String>) {

    val firstLineValue = lineValueOf(testInput.first())

    val totalCalibrationValues = testInput.sumOf { lineValueOf(it) }
    private fun lineValueOf(line: String): Int { return line.first(Char::isDigit).digitToInt() * 10 + line.last(Char::isDigit).digitToInt() }
}
