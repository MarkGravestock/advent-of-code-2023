import day02.Game
import day02.Games
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day02Part1Test : FunSpec({
    val testInputLine = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"

    test("it can find the game number") {
        val sut = Game(testInputLine)

        sut.number() shouldBe 3
    }

    test("it can find the number of turns in a game") {
        val sut = Game(testInputLine)

        sut.numberOfTurns() shouldBe 3
    }

    test("it can find the result of a turn") {
        val sut = Game(testInputLine)

        val first = sut.turns().first()

        first.red() shouldBe 20
        first.green() shouldBe 8
        first.blue() shouldBe 6
    }

    test("can check is turn is possible") {
        val sut = Game(testInputLine)
        val cubes = Game.Cubes(red = 12, green = 13, blue = 14)

        val turns = sut.turns()

        cubes.isPossibleTurn(turns.first()) shouldBe false
        cubes.isPossibleTurn(turns.second()) shouldBe true
        cubes.isPossibleTurn(turns.third()) shouldBe true
    }

    test("can check game is possible") {
        val sut = Game(testInputLine)
        val cubes = Game.Cubes(red = 12, green = 13, blue = 14)

        sut.isPossibleFor(cubes) shouldBe false
    }

    test("can sum IDs of possible games") {

        val sut = Games(readInput("Day02_test"))
        val cubes = Game.Cubes(red = 12, green = 13, blue = 14)

        sut.getTotalPossibleIdsForCubes(cubes) shouldBe 8
    }

    test("can sum IDs of possible games in the actual file") {

        val sut = Games(readInput("Day02"))
        val cubes = Game.Cubes.default()

        sut.getTotalPossibleIdsForCubes(cubes) shouldBe 2512
    }
})

private fun <T> Iterable<T>.third(): T {
    return this.elementAt(2)
}

private fun <T> Iterable<T>.second(): T {
    return this.elementAt(1)
}
