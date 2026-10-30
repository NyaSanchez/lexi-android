package io.github.nyasanchez.lexi.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.nyasanchez.lexi.R
import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage
import io.github.nyasanchez.lexi.domain.game.GameState
import io.github.nyasanchez.lexi.domain.game.GameStatus
import io.github.nyasanchez.lexi.domain.game.WordGame
import io.github.nyasanchez.lexi.ui.theme.LexiTheme
import java.util.Locale

/** Solo se presenta al terminar. Los ejemplos proceden del mismo ejercicio que se acaba de jugar. */
@Composable
internal fun PracticeResultScreen(
    state: GameState,
    position: Int,
    total: Int,
    onNext: () -> Unit,
    onGoBack: () -> Unit,
    saveStatus: SaveStatus = SaveStatus.IDLE,
    onRetrySave: () -> Unit = {},
) {
    require(state.status != GameStatus.IN_PROGRESS)
    val solution = requireNotNull(state.solution)
    var showAttempts by rememberSaveable(state.entryId, state.answerLanguage) { mutableStateOf(false) }
    val attemptsDescription = stringResource(if (showAttempts) R.string.attempts_expanded else R.string.attempts_collapsed)
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.widthIn(max = 560.dp).fillMaxSize().testTag("practice_list"),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item(key = "header") {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onGoBack, enabled = saveStatus != SaveStatus.SAVING) {
                        Icon(painterResource(R.drawable.ic_arrow_back), stringResource(R.string.back_home))
                    }
                    Box(Modifier.weight(1f)) { LexiBrand() }
                    Text(stringResource(R.string.word_position, position, total), style = MaterialTheme.typography.labelLarge)
                }
            }
            if (saveStatus != SaveStatus.IDLE) {
                item(key = "save_status") {
                    Column(Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite },
                        verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            if (saveStatus == SaveStatus.SAVING) CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                            Text(stringResource(when (saveStatus) {
                                SaveStatus.SAVING -> R.string.saving_result
                                SaveStatus.SAVED -> R.string.result_saved
                                else -> R.string.save_result_error
                            }), style = MaterialTheme.typography.bodyMedium,
                                color = if (saveStatus == SaveStatus.ERROR) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        if (saveStatus == SaveStatus.ERROR) TextButton(onClick = onRetrySave) { Text(stringResource(R.string.retry_save)) }
                    }
                }
            }
            item(key = "summary") {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ) {
                    Column(Modifier.fillMaxWidth().padding(22.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            stringResource(if (state.status == GameStatus.WON) R.string.game_won else R.string.game_lost),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.semantics { heading(); liveRegion = LiveRegionMode.Polite },
                        )
                        Text(
                            if (state.status == GameStatus.WON) {
                                pluralStringResource(R.plurals.result_attempts, state.attemptsUsed, state.attemptsUsed)
                            } else stringResource(R.string.result_lost_description),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Text(
                            stringResource(R.string.result_language_pair,
                                languageName(solution.answerLanguage), languageName(solution.clueLanguage)),
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Text(
                            stringResource(R.string.game_solution, solution.answer.uppercase(Locale.ROOT), solution.translation),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            item(key = "examples") {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                ) {
                    Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(painterResource(R.drawable.ic_book), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Text(stringResource(R.string.result_example_title), style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold, modifier = Modifier.semantics { heading() })
                        }
                        ExampleText(solution.answerLanguage, solution.example)
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        ExampleText(solution.clueLanguage, solution.translatedExample)
                    }
                }
            }
            item(key = "attempts_toggle") {
                TextButton(
                    onClick = { showAttempts = !showAttempts },
                    modifier = Modifier.fillMaxWidth().semantics { stateDescription = attemptsDescription },
                ) {
                    Text(stringResource(if (showAttempts) R.string.hide_attempts else R.string.show_attempts))
                }
            }
            if (showAttempts) {
                item(key = "attempts") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(stringResource(R.string.attempts_used, state.attemptsUsed, WordGame.MAX_ATTEMPTS),
                            style = MaterialTheme.typography.titleSmall)
                        GuessBoard(state)
                        LetterLegend()
                    }
                }
            }
            item(key = "actions") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onNext, enabled = saveStatus == SaveStatus.SAVED || saveStatus == SaveStatus.IDLE,
                        modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
                        shape = RoundedCornerShape(14.dp)) {
                        Text(stringResource(R.string.next_word), fontWeight = FontWeight.Bold)
                        Spacer(Modifier.size(10.dp))
                        Icon(painterResource(R.drawable.ic_arrow_forward), contentDescription = null)
                    }
                    TextButton(onClick = onGoBack, enabled = saveStatus != SaveStatus.SAVING, modifier = Modifier.fillMaxWidth()) {
                        Text(stringResource(R.string.back_home))
                    }
                }
            }
        }
    }
}

@Composable
private fun ExampleText(language: VocabularyLanguage, example: String) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(languageName(language), style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        Text(example, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun languageName(language: VocabularyLanguage): String = stringResource(when (language) {
    VocabularyLanguage.ENGLISH -> R.string.language_english
    VocabularyLanguage.SPANISH -> R.string.language_spanish
})

@Preview(showBackground = true, widthDp = 360, heightDp = 900, name = "Resultado claro")
@Preview(showBackground = true, widthDp = 360, heightDp = 900, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Resultado oscuro")
@Preview(showBackground = true, widthDp = 320, heightDp = 900, fontScale = 1.5f, name = "Resultado texto grande")
@Composable
private fun ResultPreview() {
    val exercise = requireNotNull(LocalVocabularyRepository().findById("house")).exerciseFor(VocabularyLanguage.ENGLISH)
    LexiTheme {
        PracticeResultScreen(WordGame.restore(exercise, listOf("SHARE", "HOUSE")).state, 1, 30, {}, {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 900, name = "Resultado sin acierto")
@Composable
private fun LostResultPreview() {
    val exercise = requireNotNull(LocalVocabularyRepository().findById("house")).exerciseFor(VocabularyLanguage.SPANISH)
    LexiTheme {
        PracticeResultScreen(WordGame.restore(exercise, List(6) { "COSA" }).state, 1, 30, {}, {})
    }
}
