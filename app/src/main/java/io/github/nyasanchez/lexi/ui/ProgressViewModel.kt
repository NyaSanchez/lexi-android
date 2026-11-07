package io.github.nyasanchez.lexi.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.nyasanchez.lexi.data.progress.ProgressDatabase
import io.github.nyasanchez.lexi.data.progress.ProgressRepository
import io.github.nyasanchez.lexi.data.progress.ProgressSnapshot
import io.github.nyasanchez.lexi.data.progress.SavedGame
import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage
import io.github.nyasanchez.lexi.domain.level.LevelRules
import io.github.nyasanchez.lexi.domain.level.LevelStatus
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

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

    private val catalog = LocalVocabularyRepository().allLevels()

    /**
     * Estado de Básico, Intermedio y Avanzado para cada modo (idioma de respuesta), calculado con las
     * partidas ya guardadas. Cada modo lleva su propio avance y su propio desbloqueo.
     * Vale null mientras se lee la base de datos, para no mostrar niveles bloqueados por error.
     */
    val levels: StateFlow<Map<VocabularyLanguage, List<LevelStatus>>?> = repository.observeWonEntries()
        .map { won ->
            VocabularyLanguage.entries.associateWith { language ->
                val wonIds = won.filter { it.answerLanguage == language.name }.map { it.entryId }.toSet()
                LevelRules.statuses(catalog, wonIds)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)


    data class StreakState(val count: Int, val completedToday: Boolean)

    val streak: StateFlow<StreakState> = repository.observeWinningDates()
        .map { stringDates ->
            val validDates = stringDates.map { LocalDate.parse(it) }
                .filter { !it.isAfter(LocalDate.now()) }
                .sortedDescending()
                .distinct()

            val count = calculateStreak(validDates)
            val completedToday = validDates.isNotEmpty() && validDates.first() == LocalDate.now()

            StreakState(count, completedToday)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, StreakState(0, false))

    private fun calculateStreak(datesDesc: List<LocalDate>): Int {
        if (datesDesc.isEmpty()) return 0

        val today = LocalDate.now()
        val mostRecentWin = datesDesc.first()

        if (mostRecentWin.isBefore(today.minusDays(1))) return 0

        var currentStreak = 0
        var expectedDate = mostRecentWin

        for (date in datesDesc) {
            if (date == expectedDate) {
                currentStreak++
                expectedDate = expectedDate.minusDays(1)
            } else {
                break
            }
        }
        return currentStreak
    }
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
