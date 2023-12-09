package day08

class ElfMap(val fileInput: List<String>) {

    private val linePattern = """(\w+)\s*=\s*\((\w+),\s*(\w+)\)""".toRegex()

    private val instructions: String = fileInput.first()

    private val mapLines = fileInput.drop(2)
        .mapNotNull { linePattern.find(it)?.groupValues }
        .associate { it[1] to MapLine(it[2], it[3]) }

    fun calculateStepsToEnd(): Long {
        return calculateStepsToEndUsing(instructions)
    }

    private fun calculateStepsRepetitionsToEndUsing(startLocations: List<String>, instructions: String): List<Long> {
        var locations = startLocations
        val startLocationsByMatches = List(startLocations.size) { index -> Pair(index, 0L) }
            .associate { it }
            .toMutableMap()

        val instructionsLength = instructions.length.toLong()

        var steps = 0L
        while (startLocationsByMatches.any { it.value == 0L }) {

            val instructionNumber = (steps % instructionsLength).toInt()
            val instruction = instructions[instructionNumber].toString()

            locations = locations.map { navigateStep(it, instruction) }
            steps++

            locations.forEachIndexed { index, it -> if (it.endsWith("Z")) startLocationsByMatches[index] = steps }

        }

        return startLocationsByMatches.values.map { it }
    }

    fun calculateStepsToEndUsing(instructions: String): Long {
        val repeatingSteps = calculateStepsRepetitionsToEndUsing(listOf("AAA"), instructions)
        return findLowestCommonMultiple(repeatingSteps)
    }

    fun navigateStep(location: String, instruction: String): String {
        return mapLines[location]!!.navigate(instruction)
    }

    fun calculateGhostStepsToEnd(): Long {
        val startLocations = mapLines.entries.filter { it.key.endsWith("A") }.map { it.key }
        val repeatingSteps = calculateStepsRepetitionsToEndUsing(startLocations, instructions).map { it }
        return findLowestCommonMultiple(repeatingSteps)
    }
    private fun greatestCommonDivisor(a: Long, b: Long): Long {
        return if (b == 0L) a else greatestCommonDivisor(b, a % b)
    }

    private fun lowestCommonMultiple(a: Long, b: Long): Long {
        return a / greatestCommonDivisor(a, b) * b
    }

    private fun findLowestCommonMultiple(numbers: List<Long>): Long {
        if (numbers.isEmpty()) return 0
        return numbers.reduce { acc, num -> lowestCommonMultiple(acc, num) }
    }

}

class MapLine(private val leftLocation: String, private val rightLocation: String) {
    fun navigate(instruction: String): String {
        return when (instruction) {
            "L" -> leftLocation
            "R" -> rightLocation
            else -> error("Invalid instruction")
        }
    }
}
