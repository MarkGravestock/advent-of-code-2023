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

    fun calculateStepsToEndUsing(startLocations: List<String>, instructions: String): Long {
        var steps = 0L
        var locations = startLocations
        val startLocationsCount = startLocations.count()
        val instructionsLength = instructions.length.toLong()
        var instructionNumber: Int
        var instruction: String

        while (locations.count { it.endsWith("Z") } != startLocationsCount) {

            instructionNumber = (steps % instructionsLength).toInt()
            instruction = instructions[instructionNumber].toString()
            locations = locations.map { navigateStep(it, instruction) }
            steps++

            if (steps % 1_000_000L == 0L) println(String.format("%,d", steps))
        }

        return steps
    }

    fun calculateStepsRepetitionsToEndUsing(startLocations: List<String>, instructions: String): List<List<Long>> {
        var steps = 0L
        var locations = startLocations
        val startLocationsByMatches = startLocations.mapIndexed { index, _ -> Pair(index, mutableListOf<Long>()) }.associate { it }.toMutableMap()
        val instructionsLength = instructions.length.toLong()
        var instructionNumber: Int
        var instruction: String

        while (startLocationsByMatches.any { it.value.count() < 4 }) {

            instructionNumber = (steps % instructionsLength).toInt()
            instruction = instructions[instructionNumber].toString()

            locations = locations.map { navigateStep(it, instruction) }

            locations.forEachIndexed { index, it -> if (it.endsWith("Z")) startLocationsByMatches[index]?.add(steps) }

            steps++

            if (steps % 1_000_000L == 0L) println(String.format("%,d", steps))
        }

        return startLocationsByMatches.values.map { it.windowed(2, 1) { window -> window[1] - window[0] } }
    }

    fun calculateStepsToEndUsing(instructions: String): Long {
        return calculateStepsToEndUsing(listOf("AAA"), instructions)
    }

    fun navigateStep(location: String, instruction: String): String {
        return mapLines[location]!!.navigate(instruction)
    }

    fun calculateGhostStepsToEnd(): Long {
        val startLocations = mapLines.entries.filter { it.key.endsWith("A") }.map { it.key }
        var repeatingSteps = calculateStepsRepetitionsToEndUsing(startLocations, instructions).map { it.first() }
        return findLowestCommonMultiple(repeatingSteps)
    }
    fun greatestCommonDivisor(a: Long, b: Long): Long {
        return if (b == 0L) a else greatestCommonDivisor(b, a % b)
    }

    fun lowestCommonMultiple(a: Long, b: Long): Long {
        return a / greatestCommonDivisor(a, b) * b
    }

    fun findLowestCommonMultiple(numbers: List<Long>): Long {
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
