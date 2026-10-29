package io.github.nyasanchez.lexi.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.nyasanchez.lexi.R

@Composable
fun ReviewScreen(onGoHome: () -> Unit) {
    ScreenColumn {
        LexiBrand()
        Text(stringResource(R.string.review_title), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.semantics { heading() })
        EmptyState(R.drawable.ic_book, stringResource(R.string.review_empty_title), stringResource(R.string.review_empty_description))
        Button(onClick = onGoHome, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
            Text(stringResource(R.string.choose_practice))
        }
    }
}
