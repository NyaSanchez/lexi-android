package io.github.nyasanchez.lexi.data.progress

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: SavedGame): Long

    @Query("SELECT * FROM completed_games WHERE sessionId = :id")
    suspend fun find(id: String): SavedGame?

    @Transaction
    suspend fun saveOnce(game: SavedGame) {
        if (insert(game) == -1L) {
            check(find(game.sessionId) == game) { "El identificador ya corresponde a otro resultado." }
        }
    }

    @Query("SELECT COUNT(*) FROM completed_games")
    fun observeCount(): Flow<Long>

    @Query(
        """
        SELECT COUNT(*) AS total,
            COALESCE(SUM(CASE WHEN won = 1 THEN 1 ELSE 0 END), 0) AS wins,
            COALESCE(SUM(CASE WHEN won = 0 THEN 1 ELSE 0 END), 0) AS losses,
            COUNT(DISTINCT CASE WHEN won = 1 THEN entryId ELSE NULL END) AS uniqueWords
        FROM completed_games
    """
    )
    suspend fun summary(): ProgressSummary

    @Query("SELECT * FROM completed_games ORDER BY completedAt DESC, sessionId ASC LIMIT 20")
    suspend fun recent(): List<SavedGame>

    @Transaction
    suspend fun snapshot(): ProgressSnapshot = ProgressSnapshot(summary(), recent())

    @Query("SELECT DISTINCT localDate FROM completed_games WHERE won = 1 ORDER BY localDate DESC")
    fun observeWinningDates(): Flow<List<String>>

    /** Palabras acertadas al menos una vez, con el idioma en que se respondió (el modo). Solo una consulta: no cambia el esquema. */
    @Query("SELECT DISTINCT entryId, answerLanguage FROM completed_games WHERE won = 1")
    fun observeWonEntries(): Flow<List<WonEntry>>
}