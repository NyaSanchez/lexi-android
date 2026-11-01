package io.github.nyasanchez.lexi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.nyasanchez.lexi.R
import io.github.nyasanchez.lexi.data.progress.ProgressSummary
import io.github.nyasanchez.lexi.data.progress.SavedGame
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ProgressScreen(state: ProgressUiState, streak: Int, onRetry: () -> Unit, onGoHome: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier.widthIn(max = 560.dp).fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    LexiBrand()
                    Text(stringResource(R.string.progress_title), style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold, modifier = Modifier.semantics { heading() })
                }
            }
            when (state) {
                ProgressUiState.Loading -> item {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator()
                        Text(stringResource(R.string.progress_loading))
                    }
                }
                ProgressUiState.Error -> item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(stringResource(R.string.progress_load_error), color = MaterialTheme.colorScheme.error)
                        TextButton(onClick = onRetry) { Text(stringResource(R.string.retry_load)) }
                    }
                }
                is ProgressUiState.Ready -> {
                    if (state.data.summary.total == 0L) {
                        item { EmptyState(R.drawable.ic_book, stringResource(R.string.history_empty_title), stringResource(R.string.history_empty_description)) }
                    } else {
                        item { ProgressTotals(state.data.summary, streak) }
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(stringResource(R.string.recent_games), style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold, modifier = Modifier.semantics { heading() })
                                Text(stringResource(R.string.recent_games_description), style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        items(state.data.recent, key = { it.sessionId }) { game -> HistoryCard(game) }
                    }
                }
            }
            item {
                Button(onClick = onGoHome, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
                    Text(stringResource(R.string.choose_practice))
                }
            }
        }
    }
}

@Composable
private fun ProgressTotals(summary: ProgressSummary, streak: Int) {
    Surface(shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.primaryContainer) {
        Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text(stringResource(R.string.progress_summary), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            // 1. Agregamos SOLO UNA VEZ la racha actual hasta arriba
            Row(Modifier.fillMaxWidth().semantics(mergeDescendants = true) {},
                horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.daily_streak_title), Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
                Text(streak.toString(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            // 2. Restauramos la lista original que tenías para los otros 4 totales
            listOf(R.string.total_games to summary.total, R.string.total_wins to summary.wins,
                R.string.total_losses to summary.losses, R.string.unique_words to summary.uniqueWords).forEach { (label, count) ->
                Row(Modifier.fillMaxWidth().semantics(mergeDescendants = true) {},
                    horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(label), Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
                    Text(count.toString(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
            }

            Text(stringResource(R.string.unique_words_description), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun HistoryCard(game: SavedGame) {
    val locale = LocalConfiguration.current.locales[0]
    val language = stringResource(if (game.answerLanguage == VocabularyLanguage.ENGLISH.name) R.string.language_english else R.string.language_spanish)
    val date = Instant.ofEpochMilli(game.completedAt).atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT).withLocale(locale))
    Surface(shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)) {
        Column(Modifier.fillMaxWidth().padding(18.dp).semantics(mergeDescendants = true) {}, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(stringResource(R.string.game_solution, game.answer.uppercase(Locale.ROOT), game.translation),
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.history_outcome, stringResource(if (game.won) R.string.history_won else R.string.history_lost), language),
                color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleSmall)
            Text(pluralStringResource(R.plurals.history_attempts, game.attempts, game.attempts), style = MaterialTheme.typography.bodyMedium)
            Text(date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}