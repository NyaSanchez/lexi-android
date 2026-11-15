package io.github.nyasanchez.lexi.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.nyasanchez.lexi.R
import io.github.nyasanchez.lexi.domain.review.ReviewItem

/**
 * Palabras en las que se agotaron los intentos y todavía no se han acertado en ese modo. Cada tarjeta muestra
 * la definición, nunca la respuesta, y permite volver a intentarla.
 */
@Composable
fun ReviewScreen(
    pending: List<ReviewItem>?,
    onRetryWord: (ReviewItem) -> Unit,
    onGoHome: () -> Unit,
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier.widthIn(max = 560.dp).fillMaxSize().testTag("review_list"),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    LexiBrand()
                    Text(stringResource(R.string.review_title), style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold, modifier = Modifier.semantics { heading() })
                }
            }
            when {
                pending == null -> item {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator()
                        Text(stringResource(R.string.review_loading))
                    }
                }
                pending.isEmpty() -> item {
                    EmptyState(R.drawable.ic_book, stringResource(R.string.review_empty_title), stringResource(R.string.review_empty_description))
                }
                else -> {
                    item {
                        Text(pluralStringResource(R.plurals.review_pending, pending.size, pending.size),
                            style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    items(pending, key = { it.key }) { word -> ReviewCard(word, onRetry = { onRetryWord(word) }) }
                }
            }
            item {
                Button(onClick = onGoHome, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
                    Text(stringResource(R.string.review_go_home))
                }
            }
        }
    }
}

@Composable
private fun ReviewCard(word: ReviewItem, onRetry: () -> Unit) {
    val mode = PracticeMode.forAnswerLanguage(word.answerLanguage)
    val exercise = word.exercise
    val retryDescription = stringResource(R.string.review_retry_description, exercise.definition)
    Surface(shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)) {
        Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(stringResource(mode.subtitle), style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary)
            Text(exercise.definition, style = MaterialTheme.typography.titleMedium)
            Text(
                stringResource(R.string.practice_level_label, stringResource(word.entry.level.labelRes), word.entry.cefr.name) +
                    " · " + pluralStringResource(R.plurals.word_length, exercise.letterCount, exercise.letterCount),
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp).semantics { contentDescription = retryDescription },
                shape = RoundedCornerShape(14.dp),
            ) {
                Text(stringResource(R.string.review_retry), fontWeight = FontWeight.Bold)
            }
        }
    }
}
