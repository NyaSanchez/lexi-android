package io.github.nyasanchez.lexi.data.progress

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProgressRepository(private val dao: ProgressDao) {
    // Cada invalidación obtiene totales y últimas partidas dentro de una misma transacción.
    fun observe(): Flow<ProgressSnapshot> = dao.observeCount().map { dao.snapshot() }
    suspend fun save(game: SavedGame) = dao.saveOnce(game)
}
