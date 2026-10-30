package io.github.nyasanchez.lexi.domain.game

import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class GameRestoreTest {
    private val exercise = requireNotNull(LocalVocabularyRepository().findById("house"))
        .exerciseFor(VocabularyLanguage.ENGLISH)

    @Test
    fun restoredRoundPreservesColorsAndCanContinue() {
        val original = WordGame(exercise)
        original.submit("SHARE")
        val restored = WordGame.restore(exercise, original.state.guesses.map { it.word })
        assertEquals(original.state, restored.state)
        restored.submit("HOUSE")
        assertEquals(GameStatus.WON, restored.state.status)
        assertEquals(2, restored.state.attemptsUsed)
        assertEquals(GameStatus.IN_PROGRESS, original.state.status)
    }

    @Test
    fun restoredWinAndLossRemainTerminal() {
        val won = WordGame.restore(exercise, listOf("HOUSE"))
        assertEquals(GameStatus.WON, won.state.status)
        assertEquals(GuessError.GAME_FINISHED, (won.submit("HOUSE") as SubmissionResult.Rejected).error)
        val lost = WordGame.restore(exercise, List(6) { "SHARE" })
        assertEquals(GameStatus.LOST, lost.state.status)
        assertEquals(exercise, lost.state.solution)
        assertEquals(GuessError.GAME_FINISHED, (lost.submit("HOUSE") as SubmissionResult.Rejected).error)
    }

    @Test
    fun invalidOrPostGameHistoryCannotBeRestored() {
        listOf(listOf("HO"), listOf("H0USE"), listOf("HOUSE", "SHARE"), List(7) { "SHARE" }).forEach { history ->
            assertThrows(IllegalArgumentException::class.java) { WordGame.restore(exercise, history) }
        }
    }

    @Test
    fun spanishRestoresAccentedAnswersAndEmptyHistoryStartsClean() {
        val tree = requireNotNull(LocalVocabularyRepository().findById("tree")).exerciseFor(VocabularyLanguage.SPANISH)
        val restored = WordGame.restore(tree, listOf("ARBOL", "ÁRBOL"))
        assertEquals(GameStatus.WON, restored.state.status)
        assertEquals(2, restored.state.attemptsUsed)
        assertEquals(LetterMark.ABSENT, restored.state.guesses.first().letters.first().mark)
        assertEquals(WordGame(exercise).state, WordGame.restore(exercise, emptyList()).state)
    }
}
