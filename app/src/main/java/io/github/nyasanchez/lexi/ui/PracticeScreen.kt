package io.github.nyasanchez.lexi.ui

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.nyasanchez.lexi.R
import io.github.nyasanchez.lexi.data.vocabulary.CefrLevel
import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLevel
import io.github.nyasanchez.lexi.domain.level.LevelStatus
import io.github.nyasanchez.lexi.data.progress.PracticeSessionStore
import io.github.nyasanchez.lexi.data.progress.SavedGame
import java.time.ZoneId
import java.util.UUID
import io.github.nyasanchez.lexi.domain.game.GameState
import io.github.nyasanchez.lexi.domain.game.GameStatus
import io.github.nyasanchez.lexi.domain.game.GuessError
import io.github.nyasanchez.lexi.domain.game.SubmissionResult
import io.github.nyasanchez.lexi.domain.game.WordGame
import io.github.nyasanchez.lexi.ui.theme.LexiTheme

private val AttemptsSaver = listSaver<List<String>, String>(save = { it }, restore = { it.toList() })

@Composable
fun PracticeScreen(
    mode: PracticeMode,
    level: VocabularyLevel,
    levels: List<LevelStatus>,
    onGoBack: () -> Unit,
    saveState: SaveUiState,
    onSave: (SavedGame) -> Unit,
    onSwitchLevel: (VocabularyLevel) -> Unit,
) {
    // Solo las palabras del nivel elegido, en orden: primero el subnivel más sencillo (A1, B1, C1).
    val entries = remember(level) { LocalVocabularyRepository().all(level) }
    if (entries.isEmpty()) {
        PracticeEmptyScreen(mode, onGoBack)
        return
    }

    val context = LocalContext.current
    val store = remember { PracticeSessionStore(context) }
    // Cada modo y nivel recuerda su propia palabra.
    val sessionKey = remember(mode, level) { PracticeSessionStore.key(mode.id, level) }

    // Al abrir la práctica se continúa en la palabra donde se quedó la persona en este modo y nivel.
    var entryId by rememberSaveable(sessionKey) {
        mutableStateOf(
            store.load(sessionKey)?.entryId?.takeIf { id -> entries.any { it.id == id } }
                ?: entries.first().id
        )
    }
    val entry = entries.firstOrNull { it.id == entryId } ?: entries.first()
    val exercise = remember(entry, mode) { entry.exerciseFor(mode.answerLanguage) }

    // Lo guardado solo vale si corresponde a la palabra actual.
    fun saved() = store.load(sessionKey)?.takeIf { it.entryId == entry.id }

    // Guardar solo IDs, texto y hasta seis respuestas; el motor reconstruye las pistas.
    var attempts by rememberSaveable(mode.id, entry.id, stateSaver = AttemptsSaver) {
        mutableStateOf(saved()?.attempts ?: emptyList<String>())
    }
    val input = rememberSaveable(mode.id, entry.id, saver = TextFieldState.Saver) { TextFieldState() }
    var showResult by rememberSaveable(mode.id, entry.id) { mutableStateOf(false) }
    var errorName by rememberSaveable(mode.id, entry.id) { mutableStateOf<String?>(null) }
    var rejectedDraft by rememberSaveable(mode.id, entry.id) { mutableStateOf<String?>(null) }
    var showExitDialog by rememberSaveable { mutableStateOf(false) }
    val game = remember(exercise, attempts) {
        // Una actualización del catálogo puede invalidar el estado guardado antiguo.
        runCatching { WordGame.restore(exercise, attempts) }.getOrElse { WordGame(exercise) }
    }
    val state = game.state
    val sessionId = rememberSaveable(mode.id, entry.id) {
        saved()?.sessionId ?: UUID.randomUUID().toString()
    }
    var completedAt by rememberSaveable(mode.id, entry.id) { mutableLongStateOf(saved()?.completedAt ?: 0L) }
    var completedZone by rememberSaveable(mode.id, entry.id) { mutableStateOf(saved()?.zoneId ?: "") }

    // Cada cambio de palabra o de intentos queda guardado para poder continuar después.
    LaunchedEffect(sessionKey, entry.id, attempts, sessionId, completedAt, completedZone) {
        store.save(
            sessionKey,
            PracticeSessionStore.Session(entry.id, attempts, sessionId, completedAt, completedZone),
        )
    }

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
    // Salir ya no pierde la partida; solo se avisa si el resultado no pudo guardarse.
    fun requestExit() {
        if (saveStatus == SaveStatus.SAVING) return
        if (saveStatus == SaveStatus.ERROR) showExitDialog = true else leave()
    }
    BackHandler { requestExit() }

    // Estado de este nivel y del siguiente, para ofrecer avanzar cuando se alcanza la meta.
    val levelStatus = levels.firstOrNull { it.level == level }
    val nextLevel = levels.firstOrNull { it.level.ordinal == level.ordinal + 1 }?.takeIf { it.unlocked }?.level

    PracticeContent(
        mode = mode,
        level = level,
        cefr = entry.cefr,
        state = state,
        position = entries.indexOf(entry) + 1,
        total = entries.size,
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
            entryId = entries[(entries.indexOf(entry) + 1) % entries.size].id
        },
        onGoBack = ::requestExit,
        saveStatus = saveStatus,
        onRetrySave = { completedGame?.let(onSave) },
        levelStatus = levelStatus,
        nextLevel = nextLevel,
        onSwitchLevel = onSwitchLevel,
    )
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(stringResource(R.string.leave_unsaved_title)) },
            text = { Text(stringResource(R.string.leave_unsaved_description)) },
            confirmButton = {
                TextButton(onClick = { showExitDialog = false; leave() }) {
                    Text(stringResource(R.string.leave_game))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(stringResource(R.string.keep_playing))
                }
            },
        )
    }
}

@Composable
private fun PracticeContent(
    mode: PracticeMode,
    level: VocabularyLevel,
    cefr: CefrLevel,
    state: GameState,
    position: Int,
    total: Int,
    input: TextFieldState,
    error: GuessError?,
    showResult: Boolean,
    onShowResult: () -> Unit,
    onSubmit: () -> Unit,
    onNext: () -> Unit,
    onGoBack: () -> Unit,
    saveStatus: SaveStatus = SaveStatus.IDLE,
    onRetrySave: () -> Unit = {},
    levelStatus: LevelStatus? = null,
    nextLevel: VocabularyLevel? = null,
    onSwitchLevel: (VocabularyLevel) -> Unit = {},
) {
    if (state.status != GameStatus.IN_PROGRESS && showResult) {
        PracticeResultScreen(
            state = state, 
            position = position, 
            total = total, 
            onNext = onNext, 
            onGoBack = onGoBack, 
            saveStatus = saveStatus, 
            onRetrySave = onRetrySave,
            levelStatus = levelStatus, 
            nextLevel = nextLevel, 
            onSwitchLevel = onSwitchLevel
        )
        return
    }
    
    val listState = rememberLazyListState()
    val errorText = guessErrorText(mode, state.wordLength, error)
    // Al enviar una respuesta incorrecta, volver a las pistas; al cambiar de palabra, a la definición.
    LaunchedEffect(state.entryId, state.attemptsUsed) {
        when {
            state.attemptsUsed > 0 -> listState.animateScrollToItem(2)
            else -> listState.scrollToItem(0)
        }
    }
    Box(Modifier.fillMaxSize().imePadding(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            state = listState,
            modifier = Modifier.widthIn(max = 560.dp).fillMaxSize().testTag("practice_list"),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onGoBack) {
                        Icon(painterResource(R.drawable.ic_arrow_back), stringResource(R.string.back_home))
                    }
                    Box(Modifier.weight(1f)) { LexiBrand() }
                    Text(stringResource(R.string.word_position, position, total), style = MaterialTheme.typography.labelLarge)
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(stringResource(mode.instruction), style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold, modifier = Modifier.semantics { heading() })
                    Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                        Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(stringResource(mode.title), style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text(stringResource(R.string.practice_level_label, stringResource(level.labelRes), cefr.name),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text(state.definition, style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text(pluralStringResource(R.plurals.word_length, state.wordLength, state.wordLength),
                                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(stringResource(R.string.attempts_used, state.attemptsUsed, WordGame.MAX_ATTEMPTS),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
                    if (state.status == GameStatus.IN_PROGRESS) {
                        Text(stringResource(R.string.board_input_hint), style = MaterialTheme.typography.bodySmall)
                    }
                    GuessBoard(state, input, errorText, onSubmit)
                    if (state.status == GameStatus.IN_PROGRESS) {
                        GuessSubmit(mode, state.wordLength, errorText, onSubmit)
                    } else {
                        Text(stringResource(if (state.status == GameStatus.WON) R.string.game_won else R.string.game_lost),
                            style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
                        Button(onClick = onShowResult, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)) {
                            Text(stringResource(R.string.view_result))
                        }
                    }
                }
            }
            item { LetterLegend() }
        }
    }
}

@Composable
private fun guessErrorText(mode: PracticeMode, length: Int, error: GuessError?): String? = when (error) {
    GuessError.EMPTY -> stringResource(R.string.guess_empty)
    GuessError.WRONG_LENGTH -> pluralStringResource(R.plurals.guess_wrong_length, length, length)
    GuessError.INVALID_CHARACTERS -> stringResource(
        if (mode == PracticeMode.ENGLISH) R.string.guess_english_letters else R.string.guess_spanish_letters)
    GuessError.GAME_FINISHED -> stringResource(R.string.guess_finished)
    null -> null
}

@Composable
private fun GuessSubmit(mode: PracticeMode, length: Int, errorText: String?, onSubmit: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(errorText ?: pluralStringResource(
            if (mode == PracticeMode.ENGLISH) R.plurals.guess_hint_english else R.plurals.guess_hint_spanish, length, length),
            style = MaterialTheme.typography.bodySmall,
            color = if (errorText != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
        Button(onClick = onSubmit, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
            shape = RoundedCornerShape(14.dp)) {
            Text(stringResource(R.string.check_answer), fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 900, name = "Partida clara")
@Preview(showBackground = true, widthDp = 360, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Partida oscura")
@Preview(showBackground = true, widthDp = 320, heightDp = 900, fontScale = 1.5f, name = "Partida texto grande")
@Composable
private fun PracticePreview() {
    val exercise = requireNotNull(LocalVocabularyRepository().findById("house")).exerciseFor(PracticeMode.ENGLISH.answerLanguage)
    LexiTheme {
        PracticeContent(
            mode = PracticeMode.ENGLISH, 
            level = VocabularyLevel.BASIC, 
            cefr = CefrLevel.A1,
            state = WordGame.restore(exercise, listOf("SHARE")).state,
            position = 1, 
            total = 100, 
            input = rememberTextFieldState("HOU"), 
            error = null, 
            showResult = false, 
            onShowResult = {}, 
            onSubmit = {}, 
            onNext = {}, 
            onGoBack = {}
        )
    }
}