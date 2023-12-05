package day05

class Almanac(private val fileInput: List<String>) {
    private fun extractSeedParts(line: String) = ("seeds: ((?:\\d+\\s*)+)".toRegex()).find(line)
    fun seeds(): List<Long> {
        val seeds = extractSeedParts(fileInput.first())
        return seeds!!.groups[1]?.value?.trim()?.split("\\s+".toRegex())!!.map { it.toLong() }
    }

    fun maps(): Map<String, CategoryMap> {
        val categoryMaps = mutableMapOf<String, CategoryMap>()
        var currentMap = CategoryMap.empty()

        for (line in 2..<fileInput.size) {
            val lineContent = fileInput[line]

            if (lineContent.isBlank()) continue

            if (lineContent.contains(" map:")) {
                currentMap = CategoryMap(lineContent)
                categoryMaps[lineContent] = currentMap
            } else {
                val (destinationRangeStart, sourceRangeStart, rangeLength) = lineContent.split(" ").map { it.toLong() }
                currentMap.addMapping(Mapping(destinationRangeStart, sourceRangeStart, rangeLength))
            }
        }

        return categoryMaps
    }

    fun mapOriginalSourceOf(source: Long): Long {
        var nextValue = source

        for (map in maps()) {
            nextValue = map.value.mapSourceOf(nextValue)
        }

        return nextValue
    }

    fun findLowestFinalDestination(): Long {
        return seeds().minOf { mapOriginalSourceOf(it) }
    }

    fun seedRanges(): List<LongRange> {
        return seeds().windowed(size = 2, step = 2).map { LongRange(it.first().toLong(), it.first().toLong() + it[1] - 1) }
    }

    fun seedsFromRanges(): List<Long> {
        return seedRanges().flatMap { it.toList() }
    }

    fun findLowestFinalDestinationFromRanges(): Long {
        return seedsFromRanges().minOf { mapOriginalSourceOf(it) }
    }

}

class Mapping(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long) {

    private val range = LongRange(start = sourceRangeStart, endInclusive = sourceRangeStart + rangeLength - 1)
    fun canMap(value: Long): Boolean {
        return range.contains(value)
    }

    fun mappedValue(value: Long): Long {
        return if (canMap(value)) (value - sourceRangeStart) + destinationRangeStart else value
    }

}

class CategoryMap(val line: String) {

    private val mappings: MutableList<Mapping> = mutableListOf()
    fun addMapping(mapping: Mapping) {
        mappings.add(mapping)
    }

    fun size(): Int = mappings.size
    fun mapSourceOf(value: Long): Long {
        val mapping = mappings.singleOrNull { it.canMap(value) }
        return mapping?.mappedValue(value) ?: value
    }

    companion object {
        fun empty(): CategoryMap {
            return CategoryMap("error")
        }
    }

}
