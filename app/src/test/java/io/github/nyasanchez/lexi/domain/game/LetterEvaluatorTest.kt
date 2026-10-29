package io.github.nyasanchez.lexi.domain.game

import io.github.nyasanchez.lexi.domain.game.LetterMark.ABSENT
import io.github.nyasanchez.lexi.domain.game.LetterMark.CORRECT
import io.github.nyasanchez.lexi.domain.game.LetterMark.PRESENT
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class LetterEvaluatorTest {
    private fun marks(target: String, guess: String) = LetterEvaluator.evaluate(target, guess).map { it.mark }

    @Test
    fun houseAndShareMatchTheDesignExample() {
        assertEquals(listOf(PRESENT, PRESENT, ABSENT, ABSENT, CORRECT), marks("HOUSE", "SHARE"))
    }

    @Test
    fun casaAndCosaMatchTheReverseModeExample() {
        assertEquals(listOf(CORRECT, ABSENT, CORRECT, CORRECT), marks("CASA", "COSA"))
    }

    @Test
    fun appleAndPapalDoNotCountTheExtraATwice() {
        assertEquals(listOf(PRESENT, PRESENT, CORRECT, ABSENT, PRESENT), marks("APPLE", "PAPAL"))
    }

    @Test
    fun exactPositionsReserveLettersBeforeEarlierIncorrectPositions() {
        assertEquals(listOf(CORRECT, ABSENT, ABSENT, CORRECT, ABSENT), marks("ARRAY", "AAAAA"))
    }

    @Test
    fun remainingRepeatedLettersAreAssignedFromLeftToRight() {
        assertEquals(listOf(PRESENT, CORRECT, ABSENT, ABSENT, ABSENT), marks("EERIE", "RETRY"))
    }

    @Test
    fun aCorrectWordMarksEveryPositionCorrect() {
        val result = LetterEvaluator.evaluate("HOUSE", "HOUSE")
        assertEquals("HOUSE", result.map { it.letter }.joinToString(""))
        assertEquals(List(5) { CORRECT }, result.map { it.mark })
    }

    @Test
    fun lettersMissingFromTheTargetAreAbsent() {
        assertEquals(List(5) { ABSENT }, marks("APPLE", "BOUND"))
    }

    @Test
    fun lettersInDifferentPositionsArePresent() {
        assertEquals(List(4) { PRESENT }, marks("ABCD", "BCDA"))
    }

    @Test
    fun emptyWordsAndDifferentLengthsAreNotEvaluated() {
        assertThrows(IllegalArgumentException::class.java) { LetterEvaluator.evaluate("", "") }
        assertThrows(IllegalArgumentException::class.java) { LetterEvaluator.evaluate("CAT", "CATS") }
        assertThrows(IllegalArgumentException::class.java) { LetterEvaluator.evaluate("CAT", "") }
    }

    @Test
    fun everyFourLetterCombinationRespectsPositionAndAvailableLetterCounts() {
        // 81 objetivos por 81 intentos: 6.561 combinaciones con letras repetidas.
        val alphabet = listOf('A', 'B', 'C')
        val words = (1..4).fold(listOf("")) { prefixes, _ ->
            prefixes.flatMap { prefix -> alphabet.map { prefix + it } }
        }
        words.forEach { target ->
            words.forEach { guess ->
                val result = LetterEvaluator.evaluate(target, guess)
                result.forEachIndexed { index, letter ->
                    assertEquals("$target/$guess en $index", target[index] == guess[index], letter.mark == CORRECT)
                }
                alphabet.forEach { character ->
                    val expectedMatches = minOf(target.count { it == character }, guess.count { it == character })
                    val actualMatches = result.count { it.letter == character && it.mark != ABSENT }
                    assertEquals("$target/$guess para $character", expectedMatches, actualMatches)
                }
            }
        }
    }
}
