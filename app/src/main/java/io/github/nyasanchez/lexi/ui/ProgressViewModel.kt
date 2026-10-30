package io.github.nyasanchez.lexi.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.nyasanchez.lexi.data.progress.ProgressDatabase
import io.github.nyasanchez.lexi.data.progress.ProgressRepository
import io.github.nyasanchez.lexi.data.progress.ProgressSnapshot
import io.github.nyasanchez.lexi.data.progress.SavedGame
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProgressUiState {
    data object Loading : ProgressUiState
    data class Ready(val data: ProgressSnapshot) : ProgressUiState
    data object Error : ProgressUiState
}

enum class SaveStatus { IDLE, SAVING, SAVED, ERROR }
data class SaveUiState(val sessionId: String = "", val status: SaveStatus = SaveStatus.IDLE)

/** Compartido por la actividad: una rotación no cancela el guardado. Room evita duplicados. */
class ProgressViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProgressRepository(ProgressDatabase.get(application).progressDao())
    private val mutableProgress = MutableStateFlow<ProgressUiState>(ProgressUiState.Loading)
    val progress = mutableProgress.asStateFlow()
    private val mutableSave = MutableStateFlow(SaveUiState())
    val save = mutableSave.asStateFlow()
    private var observation: Job? = null

    init { reload() }

    fun reload() {
        observation?.cancel()
        mutableProgress.value = ProgressUiState.Loading
        observation = viewModelScope.launch {
            try {
                repository.observe().collect { mutableProgress.value = ProgressUiState.Ready(it) }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                mutableProgress.value = ProgressUiState.Error
            }
        }
    }

    fun save(game: SavedGame) {
        val previous = mutableSave.value
        if (previous.sessionId == game.sessionId && previous.status in listOf(SaveStatus.SAVING, SaveStatus.SAVED)) return
        mutableSave.value = SaveUiState(game.sessionId, SaveStatus.SAVING)
        viewModelScope.launch {
            try {
                repository.save(game)
                mutableSave.value = SaveUiState(game.sessionId, SaveStatus.SAVED)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                mutableSave.value = SaveUiState(game.sessionId, SaveStatus.ERROR)
            }
        }
    }
}
