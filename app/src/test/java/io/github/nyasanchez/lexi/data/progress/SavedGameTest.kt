package io.github.nyasanchez.lexi.data.progress

import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage
import io.github.nyasanchez.lexi.domain.game.GameStatus
import io.github.nyasanchez.lexi.domain.game.WordGame
import java.time.Instant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class SavedGameTest {
    private val exercise = requireNotNull(LocalVocabularyRepository().findById("house"))
        .exerciseFor(VocabularyLanguage.ENGLISH)
    private val instant = Instant.parse("2026-10-30T02:00:00Z").toEpochMilli()
    private val zone = "America/Mexico_City"
    private fun won() = WordGame.restore(exercise, listOf("SHARE", "HOUSE")).state
    private fun record() = SavedGame.from(won(), "session-a", instant, zone)

    @Test fun aWinKeepsWordDirectionAttemptsAndOriginalDate() {
        val saved = record()
        assertTrue(saved.won)
        assertEquals(2, saved.attempts)
        assertEquals("house", saved.answer)
        assertEquals("casa", saved.translation)
        assertEquals("ENGLISH", saved.answerLanguage)
        assertEquals(instant, saved.completedAt)
        assertEquals("2026-10-29", saved.localDate)
        assertEquals(zone, saved.zoneId)
    }

    @Test fun aLossNeverBecomesAWinWhenTheSolutionIsRevealed() {
        val lost = WordGame.restore(exercise, List(6) { "SHARE" }).state
        val saved = SavedGame.from(lost, "loss", instant, zone)
        assertFalse(saved.won)
        assertEquals(6, saved.attempts)
    }

    @Test fun unfinishedAndAlteredStatesCannotBecomeResults() {
        val active = WordGame.restore(exercise, listOf("SHARE")).state
        assertThrows(IllegalArgumentException::class.java) { SavedGame.from(active, "a", instant, zone) }
        assertThrows(IllegalArgumentException::class.java) { SavedGame.from(won().copy(status = GameStatus.LOST), "a", instant, zone) }
        assertThrows(IllegalArgumentException::class.java) { SavedGame.from(won().copy(entryId = "apple"), "a", instant, zone) }
    }

    @Test fun restoringTheSameSessionProducesTheIdenticalRecord() {
        val restored = WordGame.restore(exercise, won().guesses.map { it.word }).state
        assertEquals(record(), SavedGame.from(restored, "session-a", instant, zone))
        assertNotEquals(record(), SavedGame.from(restored, "session-b", instant, zone))
    }

    @Test fun reverseModeKeepsItsOwnAnswerAndTranslation() {
        val reverse = requireNotNull(LocalVocabularyRepository().findById("house")).exerciseFor(VocabularyLanguage.SPANISH)
        val saved = SavedGame.from(WordGame.restore(reverse, listOf("CASA")).state, "spanish", instant, zone)
        assertEquals("SPANISH", saved.answerLanguage)
        assertEquals("casa", saved.answer)
        assertEquals("house", saved.translation)
    }

    @Test fun dateIsDerivedFromTheCapturedZoneNotTheCurrentLocale() {
        val utc = SavedGame.from(won(), "utc", instant, "UTC")
        assertEquals("2026-10-30", utc.localDate)
        assertEquals("2026-10-29", record().localDate)
    }

    @Test fun inconsistentStoredFieldsAreRejected() {
        val saved = record()
        assertThrows(IllegalArgumentException::class.java) { saved.copy(sessionId = "") }
        assertThrows(IllegalArgumentException::class.java) { saved.copy(attempts = 7) }
        assertThrows(IllegalArgumentException::class.java) { saved.copy(won = false) }
        assertThrows(IllegalArgumentException::class.java) { saved.copy(localDate = "2026-10-30") }
        assertThrows(IllegalArgumentException::class.java) { saved.copy(answerLanguage = "OTHER") }
    }
}
