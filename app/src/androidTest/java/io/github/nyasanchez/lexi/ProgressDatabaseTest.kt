package io.github.nyasanchez.lexi

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.nyasanchez.lexi.data.progress.ProgressDatabase
import io.github.nyasanchez.lexi.data.progress.SavedGame
import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage
import io.github.nyasanchez.lexi.domain.game.WordGame
import java.util.UUID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/** Archivos temporales propios: estas pruebas no usan ni borran lexi-progress.db. */
@RunWith(AndroidJUnit4::class)
class ProgressDatabaseTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val name = "lexi-test-${UUID.randomUUID()}.db"
    private lateinit var database: ProgressDatabase
    private fun open() = Room.databaseBuilder(context, ProgressDatabase::class.java, name).build()
    private fun game(id: String = "one", spanish: Boolean = false, won: Boolean = true): SavedGame {
        val exercise = requireNotNull(LocalVocabularyRepository().findById("house"))
            .exerciseFor(if (spanish) VocabularyLanguage.SPANISH else VocabularyLanguage.ENGLISH)
        val guesses = if (won) listOf(exercise.answer) else List(6) { if (spanish) "COSA" else "SHARE" }
        return SavedGame.from(WordGame.restore(exercise, guesses).state, id, 1793325600000L, "UTC")
    }
    @Before fun setUp() { database = open() }
    @After fun tearDown() { database.close(); context.deleteDatabase(name) }

    @Test fun duplicateResultIsStoredOnceAndSurvivesReopening() = runBlocking {
        val result = game()
        database.progressDao().saveOnce(result)
        database.progressDao().saveOnce(result)
        database.close()
        database = open()
        assertEquals(result, database.progressDao().find(result.sessionId))
        assertEquals(1L, database.progressDao().snapshot().summary.total)
    }

    @Test fun anIdCannotOverwriteAnExistingResult() = runBlocking {
        val original = game()
        database.progressDao().saveOnce(original)
        try {
            database.progressDao().saveOnce(game(spanish = true))
            fail("Se esperaba un conflicto de identificador")
        } catch (_: IllegalStateException) { }
        assertEquals(original, database.progressDao().find(original.sessionId))
    }

    @Test fun totalsIncludeAllRowsWhileRecentHistoryIsBounded() = runBlocking {
        repeat(21) { database.progressDao().saveOnce(game("win-$it", spanish = it % 2 == 0)) }
        database.progressDao().saveOnce(game("loss", won = false))
        val snapshot = database.progressDao().snapshot()
        assertEquals(22L, snapshot.summary.total)
        assertEquals(21L, snapshot.summary.wins)
        assertEquals(1L, snapshot.summary.losses)
        assertEquals(1L, snapshot.summary.uniqueWords)
        assertEquals(20, snapshot.recent.size)
    }

    @Test fun aLostWordIsPendingUntilItIsWonInThatSameMode() = runBlocking {
        val dao = database.progressDao()
        dao.saveOnce(game("loss-en", won = false))
        dao.saveOnce(game("loss-es", spanish = true, won = false))
        assertEquals(setOf("ENGLISH", "SPANISH"), dao.observeReviewEntries().first().map { it.answerLanguage }.toSet())

        // Acertarla en inglés la saca de Repaso en ese modo, pero sigue pendiente en español.
        dao.saveOnce(game("win-en"))
        val pending = dao.observeReviewEntries().first()
        assertEquals(listOf("SPANISH"), pending.map { it.answerLanguage })
        assertEquals(listOf("house"), pending.map { it.entryId })
    }

    @Test fun repeatedLossesOfTheSameWordAreListedOnce() = runBlocking {
        val dao = database.progressDao()
        dao.saveOnce(game("loss-1", won = false))
        dao.saveOnce(game("loss-2", won = false))
        assertEquals(1, dao.observeReviewEntries().first().size)
    }

    @Test fun aWordWonBeforeItWasLostNeverEntersReview() = runBlocking {
        val dao = database.progressDao()
        dao.saveOnce(game("win"))
        dao.saveOnce(game("loss", won = false))
        assertEquals(emptyList<Any>(), dao.observeReviewEntries().first())
    }
}
