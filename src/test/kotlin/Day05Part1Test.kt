import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize

class Day05Part1Test : FunSpec({
    val day = 5

    context("Seeds logic for Almanac") {

        val fileInput = readTestInputForDay(day)
        val sut = Almanac(fileInput)

        test("It can find the seeds") {
            sut.seeds() shouldHaveSize 4
        }

        test("It can find the maps") {
            sut.maps() shouldHaveSize 7
        }

    }

})

class Almanac(private val fileInput: List<String>) {
    private fun extractSeedParts(line: String) = ("seeds: ((?:\\d+\\s*)+)".toRegex()).find(line)
    fun seeds(): List<Int> {
        val seeds = extractSeedParts(fileInput.first())
        return seeds!!.groups[1]?.value?.trim()?.split("\\s+".toRegex())!!.map { it.toInt() }
    }

    fun maps(): List<CategoryMap> {
        val categoryMaps = mutableListOf<CategoryMap>()

        for (line in 2..<fileInput.size) {
            val lineContent = fileInput[line]

            if (lineContent.isBlank()) continue
            if (lineContent.contains(" map:")) categoryMaps.add(CategoryMap(lineContent))
        }

        return categoryMaps
    }

}

class
CategoryMap(val line: String) {

}

