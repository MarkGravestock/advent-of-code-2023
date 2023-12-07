    private val regex = "(\\d+)".toRegex()
    fun String.parseNumbers() = regex.findAll(this).map { it.value.toLong() }
