package io.github.nyasanchez.lexi.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import io.github.nyasanchez.lexi.R
import io.github.nyasanchez.lexi.data.progress.SavedGame
import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.domain.game.GameStatus
import io.github.nyasanchez.lexi.domain.game.GuessError
import io.github.nyasanchez.lexi.domain.game.SubmissionResult
import io.github.nyasanchez.lexi.domain.game.WordGame
import java.time.ZoneId
import java.util.UUID

/**
 * Vuelve a jugar una palabra perdida, con seis intentos nuevos. Al terminar guarda la partida igual que la práctica
 * normal: si se acierta, queda como partida ganada (la palabra sale de Repaso y cuenta en el nivel que le corresponde);
 * si no, sigue pendiente. Abandonarla a medias no suma victoria ni derrota.
 */
@Composable
fun ReviewPracticeScreen(
    mode: PracticeMode,
    entryId: String,
    onNextWord: () -> Unit,
    onGoBack: () -> Unit,
    saveState: SaveUiState,
    onSave: (SavedGame) -> Unit,
) {
    val entry = remember(entryId) { LocalVocabularyRepository().findById(entryId) }
    if (entry == null) {
        // Una actualización del catálogo pudo quitar la palabra.
        PracticeEmptyScreen(mode, onGoBack)
        return
    }
    val exercise = remember(entry, mode) { entry.exerciseFor(mode.answerLanguage) }

    var attempts by rememberSaveable(mode.id, entry.id, stateSaver = AttemptsSaver) {
        mutableStateOf(emptyList<String>())
    }
    val input = rememberSaveable(mode.id, entry.id, saver = TextFieldState.Saver) { TextFieldState() }
    var showResult by rememberSaveable(mode.id, entry.id) { mutableStateOf(false) }
    var errorName by rememberSaveable(mode.id, entry.id) { mutableStateOf<String?>(null) }
    var rejectedDraft by rememberSaveable(mode.id, entry.id) { mutableStateOf<String?>(null) }
    var showUnsavedDialog by rememberSaveable { mutableStateOf(false) }
    var showAbandonDialog by rememberSaveable { mutableStateOf(false) }
    val game = remember(exercise, attempts) {
        runCatching { WordGame.restore(exercise, attempts) }.getOrElse { WordGame(exercise) }
    }
    val state = game.state
    // Cada reintento es una partida nueva con su propio identificador: Room evita duplicados al recuperar la pantalla.
    val sessionId = rememberSaveable(mode.id, entry.id) { UUID.randomUUID().toString() }
    var completedAt by rememberSaveable(mode.id, entry.id) { mutableLongStateOf(0L) }
    var completedZone by rememberSaveable(mode.id, entry.id) { mutableStateOf("") }

    val completedGame = remember(state, sessionId, completedAt, completedZone) {
        if (state.status != GameStatus.IN_PROGRESS && completedAt > 0) {
            SavedGame.from(state, sessionId, completedAt, completedZone)
        } else null
    }
    val saveStatus = when {
        completedGame == null -> SaveStatus.IDLE
        saveState.sessionId == sessionId -> saveState.status
        else -> SaveStatus.SAVING
    }
    LaunchedEffect(completedGame) {
        completedGame?.let(onSave)
    }

    val keyboard = LocalSoftwareKeyboardController.current
    val focus = LocalFocusManager.current

    fun leave() {
        keyboard?.hide()
        focus.clearFocus()
        onGoBack()
    }
    fun requestExit() {
        when {
            saveStatus == SaveStatus.SAVING -> Unit
            saveStatus == SaveStatus.ERROR -> showUnsavedDialog = true
            state.status == GameStatus.IN_PROGRESS && attempts.isNotEmpty() -> showAbandonDialog = true
            else -> leave()
        }
    }
    BackHandler { requestExit() }

    PracticeContent(
        mode = mode,
        level = entry.level,
        cefr = entry.cefr,
        state = state,
        position = null,
        total = null,
        input = input,
        error = errorName?.takeIf { rejectedDraft == input.text.toString() }?.let { GuessError.valueOf(it) },
        showResult = showResult,
        onShowResult = { showResult = true },
        onSubmit = {
            when (val result = game.submit(input.text.toString())) {
                is SubmissionResult.Accepted -> {
                    if (result.state.status != GameStatus.IN_PROGRESS) {
                        completedAt = System.currentTimeMillis()
                        completedZone = ZoneId.systemDefault().id
                    }
                    attempts = result.state.guesses.map { it.word }
                    input.clearText()
                    errorName = null
                    keyboard?.hide()
                    focus.clearFocus()
                }
                is SubmissionResult.Rejected -> {
                    rejectedDraft = input.text.toString()
                    errorName = result.error.name
                }
            }
        },
        onNext = {
            keyboard?.hide()
            focus.clearFocus()
            onNextWord()
        },
        onGoBack = ::requestExit,
        saveStatus = saveStatus,
        onRetrySave = { completedGame?.let(onSave) },
        backLabel = R.string.review_back,
    )

    if (showUnsavedDialog || showAbandonDialog) {
        val unsaved = showUnsavedDialog
        AlertDialog(
            onDismissRequest = { showUnsavedDialog = false; showAbandonDialog = false },
            title = { Text(stringResource(if (unsaved) R.string.leave_unsaved_title else R.string.leave_game_title)) },
            text = {
                Text(stringResource(if (unsaved) R.string.leave_unsaved_description else R.string.leave_game_description))
            },
            confirmButton = {
                TextButton(onClick = { showUnsavedDialog = false; showAbandonDialog = false; leave() }) {
                    Text(stringResource(R.string.leave_game))
                }
            },
            dismissButton = {
                TextButton(onClick = { showUnsavedDialog = false; showAbandonDialog = false }) {
                    Text(stringResource(R.string.keep_playing))
                }
            },
        )
    }
}
