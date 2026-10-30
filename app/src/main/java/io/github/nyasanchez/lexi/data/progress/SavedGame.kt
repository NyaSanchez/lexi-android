package io.github.nyasanchez.lexi.data.progress

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage
import io.github.nyasanchez.lexi.domain.game.GameState
import io.github.nyasanchez.lexi.domain.game.GameStatus
import io.github.nyasanchez.lexi.domain.game.WordGame
import java.time.Instant
import java.time.ZoneId

/** Resultado de una partida terminada; nunca almacena el borrador ni intentos abandonados. */
@Entity(tableName = "completed_games", indices = [Index(value = ["completedAt"])])
data class SavedGame(
    @PrimaryKey val sessionId: String,
    val entryId: String,
    val answerLanguage: String,
    val answer: String,
    val translation: String,
    val won: Boolean,
    val attempts: Int,
    val completedAt: Long,
    val localDate: String,
    val zoneId: String,
) {
    init {
        require(sessionId.isNotBlank() && entryId.isNotBlank())
        require(answer.isNotBlank() && translation.isNotBlank())
        VocabularyLanguage.valueOf(answerLanguage)
        require(attempts in 1..WordGame.MAX_ATTEMPTS)
        require(won || attempts == WordGame.MAX_ATTEMPTS)
        require(completedAt >= 0)
        require(localDate == Instant.ofEpochMilli(completedAt).atZone(ZoneId.of(zoneId)).toLocalDate().toString())
    }

    companion object {
        fun from(state: GameState, sessionId: String, completedAt: Long, zoneId: String): SavedGame {
            require(state.status != GameStatus.IN_PROGRESS) { "Solo se guardan partidas terminadas." }
            val solution = requireNotNull(state.solution)
            // Impide registrar estados incompletos o alterados fuera del motor.
            require(WordGame.restore(solution, state.guesses.map { it.word }).state == state)
            return SavedGame(
                sessionId, state.entryId, state.answerLanguage.name, solution.answer, solution.translation,
                state.status == GameStatus.WON, state.attemptsUsed, completedAt,
                Instant.ofEpochMilli(completedAt).atZone(ZoneId.of(zoneId)).toLocalDate().toString(), zoneId,
            )
        }
    }
}

data class ProgressSummary(val total: Long, val wins: Long, val losses: Long, val uniqueWords: Long)
data class ProgressSnapshot(val summary: ProgressSummary, val recent: List<SavedGame>)
