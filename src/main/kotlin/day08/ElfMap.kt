package day08

import println

class ElfMap(val fileInput: List<String>) {

    private val linePattern = """(\w+)\s*=\s*\((\w+),\s*(\w+)\)""".toRegex()

    private val instructions: String = fileInput.first()

    private val mapLines = fileInput.drop(2)
        .mapNotNull { linePattern.find(it)?.groupValues }
        .associate { it[1] to MapLine(it[2], it[3]) }

    fun calculateStepsToEnd(): Long {
        return calculateStepsToEndUsing(instructions)
    }

    fun calculateStepsToEndUsing(startLocations: List<String>, instructions: String): Pair<Long, List<String>> {
        var steps = 0L
        var locations = startLocations
        val startLocationsCount = startLocations.count()
        val instructionsLength = instructions.length.toLong()
        var instructionNumber = 0
        var instruction = ""

        while (locations.count { it.endsWith("Z") } != startLocationsCount) {

            try {
                instructionNumber = (steps % instructionsLength).toInt()
                instruction = instructions[instructionNumber].toString()
                locations = locations.map { navigateStep(it, instruction) }
                steps++

                if (steps % 1_000_000L == 0L) println(String.format("%,d", steps))
            } catch (e: Exception)
            {
                println("Steps: ${steps}, Number: ${instructionNumber}, Instruction: ${instruction}")

                println("")

                locations.forEach { it.println() }
                throw e
            }

        }

        println("Steps: ${steps}, Number: ${instructionNumber}, Instruction: ${instruction}")
        locations.forEach { it.println() }

        instructionNumber = (steps % instructionsLength).toInt()
        instruction = instructions[instructionNumber].toString()
        locations = locations.map { navigateStep(it, instruction) }

        return Pair(steps, locations)
    }

    fun calculateStepsToEndUsing(instructions: String): Long {
        return calculateStepsToEndUsing(listOf("AAA"), instructions).first
    }

    fun navigateStep(location: String, instruction: String): String {
        return mapLines[location]!!.navigate(instruction)
    }

    fun calculateGhostStepsToEnd(): Long {
        val startLocations = mapLines.entries.filter { it.key.endsWith("A") }.map { it.key }
        return calculateStepsToEndUsing(startLocations, instructions).first
    }

    fun calculateGhostStepsToEndForFirst(): Long {
        val startLocations = mapLines.entries.filter { it.key.endsWith("A") }.map { it.key }

        val result1 =  calculateStepsToEndUsing(startLocations, instructions)
        val result2 = calculateStepsToEndUsing(result1.second, instructions)

        return calculateStepsToEndUsing(result2.second, instructions).first
    }

}

class MapLine(private val leftLocation: String, private val rightLocation: String) {
    fun navigate(instruction: String): String {
        return when (instruction) {
            "L" -> leftLocation
            "R" -> rightLocation
            else -> throw IllegalStateException()
        }
    }
}
