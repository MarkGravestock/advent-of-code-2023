import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

fun dayFilePart(day: Int) = "Day0${day}"

fun readInputForDay(day: Int) = readInput(dayFilePart(day))

fun readTestInputForDay(day: Int) = readInput("${dayFilePart(day)}_test")

fun readTestInputForDay(day: Int, number: Int) = readInput("${dayFilePart(day)}_test${number}")
/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/main/resources/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

const val logEnabled = true
fun Any?.log(): () -> Unit = { if (logEnabled) this.println() else Unit }
