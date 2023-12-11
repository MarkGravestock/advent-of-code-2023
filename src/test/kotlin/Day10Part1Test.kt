import day10.Coordinate
import day10.PipeMap
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe


class Day10Part1Test : FunSpec({
    context("Pipe Map") {

        val fileInput = readTestInputForDay(10)
        val sut = PipeMap(fileInput)

        test("It can count lines") {
            sut.start() shouldBe Coordinate(1, 1)
        }

        test("It can find moves") {
            sut.movesAt(Coordinate(1, 1)) shouldContainExactlyInAnyOrder listOf(Coordinate(1,2), Coordinate(2 ,1))
            sut.movesAt(Coordinate(2, 1)) shouldContainExactlyInAnyOrder listOf(Coordinate(3 ,1), Coordinate(1, 1))
            sut.movesAt(Coordinate(1, 2)) shouldContainExactlyInAnyOrder listOf(Coordinate(1 ,3), Coordinate(1, 1))
            sut.movesAt(Coordinate(1, 3)) shouldContainExactlyInAnyOrder listOf(Coordinate(1 ,2), Coordinate(2,3))
        }

        test("It can find the best next move") {
            sut.nextBestMoveAt(Coordinate(1, 2)) shouldBe Coordinate(1, 3)
        }

        test("It can find the max location") {
            sut.findMaxLocation().first shouldBe Coordinate(3, 3)
            sut.findMaxLocation().second shouldBe 4
        }
    }

    context("TestPipe Map 1") {

        val fileInput = readTestInputForDay(10, 1)
        val sut = PipeMap(fileInput)

        test("It can find the max location") {
            sut.findMaxLocation().first shouldBe Coordinate(2, 2)
            sut.findMaxLocation().second shouldBe 3
        }
    }

    context("TestPipe Map 2") {

        val fileInput = readTestInputForDay(10, 2)
        val sut = PipeMap(fileInput)

        test("It can find the max location") {
            sut.findMaxLocation().first shouldBe Coordinate(4, 2)
            sut.findMaxLocation().second shouldBe 8
        }

        test("It can find the start") {
            sut.start() shouldBe Coordinate(0, 2)
        }
    }

    context("Part 1 Map") {

        val fileInput = readInputForDay(10)
        val sut = PipeMap(fileInput)

        test("It can find the max steps") {
            sut.findMaxLocation().second shouldBe 7012
        }

        test("It can find moves") {
            sut.movesAt(Coordinate(92, 86))  shouldContainExactlyInAnyOrder listOf(Coordinate(93, 86), Coordinate(91, 86))
        }
    }
})

