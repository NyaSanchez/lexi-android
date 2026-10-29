package io.github.nyasanchez.lexi.domain.game

import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage.ENGLISH
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage.SPANISH
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class WordGameTest {
    private val repository = LocalVocabularyRepository()

    private fun exercise(id: String = "house", language: VocabularyLanguage = ENGLISH) =
        requireNotNull(repository.findById(id)).exerciseFor(language)

    private fun accepted(result: SubmissionResult): SubmissionResult.Accepted {
        assertTrue("Se esperaba un intento aceptado: $result", result is SubmissionResult.Accepted)
        return result as SubmissionResult.Accepted
    }

    private fun rejected(result: SubmissionResult, error: GuessError): SubmissionResult.Rejected {
        assertTrue("Se esperaba un rechazo: $result", result is SubmissionResult.Rejected)
        return (result as SubmissionResult.Rejected).also { assertEquals(error, it.error) }
    }

    @Test
    fun initialStateHasSixAttemptsAndDoesNotExposeTheSolution() {
        val state = WordGame(exercise()).state
        assertEquals(GameStatus.IN_PROGRESS, state.status)
        assertEquals(0, state.attemptsUsed)
        assertEquals(6, state.attemptsRemaining)
        assertEquals(5, state.wordLength)
        assertEquals(SPANISH, state.clueLanguage)
        assertEquals(ENGLISH, state.answerLanguage)
        assertEquals(exercise().definition, state.definition)
        assertNull(state.solution)
    }

    @Test
    fun blankSubmissionsDoNotUseAnAttempt() {
        val game = WordGame(exercise())
        val before = game.state
        listOf("", " ", "\n\t").forEach {
            assertEquals(before, rejected(game.submit(it), GuessError.EMPTY).state)
        }
    }

    @Test
    fun incompleteAndOverlongWordsDoNotUseAnAttempt() {
        val game = WordGame(exercise())
        listOf("H", "HOUS", "HOUSES").forEach {
            rejected(game.submit(it), GuessError.WRONG_LENGTH)
        }
        assertEquals(6, game.state.attemptsRemaining)
        assertTrue(game.state.guesses.isEmpty())
    }

    @Test
    fun numbersPunctuationInteriorSpacesAndEmojiAreRejected() {
        val game = WordGame(exercise())
        listOf("H0USE", "HOU!E", "HO SE", "HO\nSE", "🏠", "\u200BHOUSE").forEach {
            rejected(game.submit(it), GuessError.INVALID_CHARACTERS)
        }
        assertEquals(0, game.state.attemptsUsed)
    }

    @Test
    fun englishRejectsForeignLettersBeforeUppercaseCanExpandThem() {
        val game = WordGame(exercise().copy(answer = "gross"))
        listOf("groß", "housé", "niño", "fısh").forEach {
            rejected(game.submit(it), GuessError.INVALID_CHARACTERS)
        }
        assertEquals(GameStatus.IN_PROGRESS, game.state.status)
        assertEquals(0, game.state.attemptsUsed)
    }

    @Test
    fun letterCaseAndOuterWhitespaceDoNotPreventACorrectAnswer() {
        listOf("house", "HOUSE", "  HoUsE \n").forEach { input ->
            val result = accepted(WordGame(exercise()).submit(input))
            assertEquals(GameStatus.WON, result.state.status)
            assertEquals("HOUSE", result.guess.word)
            assertEquals(1, result.state.attemptsUsed)
        }
    }

    @Test
    fun aCombiningAccentFromTheKeyboardIsEquivalentToItsPrecomposedForm() {
        val game = WordGame(exercise("tree", SPANISH))
        val result = accepted(game.submit("a\u0301rBoL"))
        assertEquals(GameStatus.WON, result.state.status)
        assertEquals("ÁRBOL", result.guess.word)
        assertEquals(5, result.guess.letters.size)
    }

    @Test
    fun spanishAcceptsEnyeAndDiaeresisIncludingCombiningForms() {
        val year = accepted(WordGame(exercise("year", SPANISH)).submit("an\u0303o"))
        assertEquals(GameStatus.WON, year.state.status)
        assertEquals("AÑO", year.guess.word)

        val penguin = WordGame(exercise("year", SPANISH).copy(answer = "pingüino"))
        val result = accepted(penguin.submit("pingu\u0308ino"))
        assertEquals(GameStatus.WON, result.state.status)
        assertEquals(8, result.state.wordLength)
    }

    @Test
    fun missingAccentsOrEnyeAreValidButIncorrectAnswers() {
        val tree = WordGame(exercise("tree", SPANISH))
        val wrongTree = accepted(tree.submit("ARBOL"))
        assertEquals(GameStatus.IN_PROGRESS, wrongTree.state.status)
        assertEquals(LetterMark.ABSENT, wrongTree.guess.letters.first().mark)
        assertEquals(5, wrongTree.state.attemptsRemaining)
        assertEquals(GameStatus.WON, accepted(tree.submit("ÁRBOL")).state.status)

        val year = accepted(WordGame(exercise("year", SPANISH)).submit("ANO"))
        assertEquals(GameStatus.IN_PROGRESS, year.state.status)
        assertEquals(LetterMark.ABSENT, year.guess.letters[1].mark)
    }

    @Test
    fun invalidInputAfterAnAcceptedGuessDoesNotChangeTheHistory() {
        val game = WordGame(exercise())
        val before = accepted(game.submit("SHARE")).state
        val after = rejected(game.submit("HO"), GuessError.WRONG_LENGTH).state
        assertEquals(before, after)
        assertEquals(5, after.attemptsRemaining)
        assertNull(after.solution)
    }

    @Test
    fun validLetterCombinationsDoNotRequireADictionary() {
        val result = accepted(WordGame(exercise()).submit("ZZZZZ"))
        assertEquals(GameStatus.IN_PROGRESS, result.state.status)
        assertEquals(1, result.state.attemptsUsed)
        assertTrue(result.guess.letters.all { it.mark == LetterMark.ABSENT })
    }

    @Test
    fun repeatingAValidIncorrectGuessConsumesAnotherAttempt() {
        val game = WordGame(exercise())
        accepted(game.submit("SHARE"))
        val second = accepted(game.submit("share"))
        assertEquals(2, second.state.attemptsUsed)
        assertEquals(4, second.state.attemptsRemaining)
    }

    @Test
    fun theSixthAttemptCanStillWin() {
        val game = WordGame(exercise())
        repeat(5) { accepted(game.submit("SHARE")) }
        val result = accepted(game.submit("HOUSE"))
        assertEquals(GameStatus.WON, result.state.status)
        assertEquals(6, result.state.attemptsUsed)
        assertEquals(0, result.state.attemptsRemaining)
        assertEquals("casa", result.state.solution?.translation)
    }

    @Test
    fun sixIncorrectAttemptsLoseAndRevealTheSolutionWithoutWinning() {
        val game = WordGame(exercise())
        repeat(5) {
            val state = accepted(game.submit("SHARE")).state
            assertEquals(GameStatus.IN_PROGRESS, state.status)
            assertNull(state.solution)
        }
        val last = accepted(game.submit("SHARE")).state
        assertEquals(GameStatus.LOST, last.status)
        assertEquals(0, last.attemptsRemaining)
        assertEquals("house", last.solution?.answer)
        assertNotNull(last.solution?.example)
    }

    @Test
    fun additionalSubmissionsAfterWinningCannotRegisterAnotherWin() {
        val game = WordGame(exercise())
        val won = accepted(game.submit("HOUSE")).state
        assertEquals(won, rejected(game.submit("HOUSE"), GuessError.GAME_FINISHED).state)
        assertEquals(won, rejected(game.submit(""), GuessError.GAME_FINISHED).state)
    }

    @Test
    fun aSeventhAttemptCannotTurnALossIntoAWin() {
        val game = WordGame(exercise())
        repeat(6) { accepted(game.submit("SHARE")) }
        val lost = game.state
        assertEquals(lost, rejected(game.submit("HOUSE"), GuessError.GAME_FINISHED).state)
        assertEquals(GameStatus.LOST, game.state.status)
    }

    @Test
    fun modifyingASnapshotCannotChangeTheInternalHistory() {
        val game = WordGame(exercise())
        val result = accepted(game.submit("SHARE"))
        (result.guess.letters as MutableList<EvaluatedLetter>)[0] = EvaluatedLetter('Z', LetterMark.CORRECT)
        (result.state.guesses as MutableList<EvaluatedGuess>).clear()
        assertEquals(1, game.state.attemptsUsed)
        assertEquals('S', game.state.guesses.first().letters.first().letter)
        assertEquals(LetterMark.PRESENT, game.state.guesses.first().letters.first().mark)
    }

    @Test
    fun separateWordsAndLanguagesHaveIndependentSessions() {
        val english = WordGame(exercise())
        val before = accepted(english.submit("SHARE")).state
        val spanish = WordGame(exercise(language = SPANISH))
        assertEquals(GameStatus.WON, accepted(spanish.submit("CASA")).state.status)
        assertEquals(before, english.state)
        assertEquals(ENGLISH, english.state.answerLanguage)
        assertEquals(SPANISH, spanish.state.answerLanguage)
    }

    @Test
    fun everyVocabularyExerciseCanBeWonWithItsOwnAnswer() {
        VocabularyLanguage.entries.forEach { language ->
            repository.exercises(language).forEach { exercise ->
                val result = accepted(WordGame(exercise).submit(exercise.answer))
                assertEquals(exercise.entryId, GameStatus.WON, result.state.status)
                assertEquals(exercise.letterCount, result.guess.letters.size)
                assertTrue(result.guess.letters.all { it.mark == LetterMark.CORRECT })
                assertEquals(exercise, result.state.solution)
            }
        }
    }

    @Test
    fun malformedExercisesAreRejectedBeforeStarting() {
        listOf("", "house2", "ice cream", "árbol").forEach { invalid ->
            assertThrows(IllegalArgumentException::class.java) { WordGame(exercise().copy(answer = invalid)) }
        }
        assertThrows(IllegalArgumentException::class.java) { WordGame(exercise().copy(clueLanguage = ENGLISH)) }
    }

    @Test
    fun deviceLocaleDoesNotChangeEnglishLetterCasing() {
        val previous = Locale.getDefault()
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"))
            val result = accepted(WordGame(exercise("fish")).submit("fish"))
            assertEquals("FISH", result.guess.word)
            assertEquals(GameStatus.WON, result.state.status)
        } finally {
            Locale.setDefault(previous)
        }
    }
}
