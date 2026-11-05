package io.github.nyasanchez.lexi.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import io.github.nyasanchez.lexi.R
import io.github.nyasanchez.lexi.domain.game.GameState
import io.github.nyasanchez.lexi.domain.game.GameStatus
import io.github.nyasanchez.lexi.domain.game.LetterMark
import io.github.nyasanchez.lexi.domain.game.WordGame

private data class LetterStyle(val background: Color, val foreground: Color, val symbol: String)

@Composable
private fun letterStyle(mark: LetterMark?): LetterStyle {
    val dark = isSystemInDarkTheme()
    return when (mark) {
        LetterMark.CORRECT -> if (dark) LetterStyle(Color(0xFF123C2B), Color(0xFFB9F6D0), "✓")
            else LetterStyle(Color(0xFFDCFCE7), Color(0xFF14532D), "✓")
        LetterMark.PRESENT -> if (dark) LetterStyle(Color(0xFF4A3509), Color(0xFFFFE29A), "↔")
            else LetterStyle(Color(0xFFFEF3C7), Color(0xFF78350F), "↔")
        LetterMark.ABSENT -> if (dark) LetterStyle(Color(0xFF4D2024), Color(0xFFFFCDD2), "×")
            else LetterStyle(Color(0xFFFEE2E2), Color(0xFF991B1B), "×")
        null -> LetterStyle(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.onSurface, "")
    }
}

@Composable
private fun markLabel(mark: LetterMark) = stringResource(when (mark) {
    LetterMark.CORRECT -> R.string.letter_correct
    LetterMark.PRESENT -> R.string.letter_present
    LetterMark.ABSENT -> R.string.letter_absent
})

@Composable
internal fun GuessBoard(
    state: GameState,
    input: TextFieldState? = null,
    errorText: String? = null,
    onSubmit: () -> Unit = {},
) {
    val fontScale = LocalDensity.current.fontScale.coerceAtLeast(1f)
    BoxWithConstraints(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        val gap = 4.dp
        val tileWidth = ((maxWidth - gap * (state.wordLength - 1)) / state.wordLength)
            .coerceIn((36 * fontScale).dp, (52 * fontScale).dp)
        // El tablero entero se desplaza junto si el tamaño de letra impide que quepan las columnas.
        Column(Modifier.horizontalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            repeat(WordGame.MAX_ATTEMPTS) { row ->
                val guess = state.guesses.getOrNull(row)
                val active = row == state.attemptsUsed && state.status == GameStatus.IN_PROGRESS
                val description = if (guess == null) {
                    stringResource(if (active) R.string.board_active_row else R.string.board_empty_row, row + 1)
                } else {
                    val letters = guess.letters.mapIndexed { index, letter ->
                        stringResource(R.string.board_letter, index + 1, letter.letter.toString(), markLabel(letter.mark))
                    }.joinToString("; ")
                    stringResource(R.string.board_evaluated_row, row + 1, guess.word, letters)
                }
                if (active && input != null) {
                    GuessRowEditor(input, state.wordLength, row, tileWidth, (52 * fontScale).dp,
                        gap, description, errorText, onSubmit)
                } else Row(
                    modifier = Modifier.testTag("board_row_$row").clearAndSetSemantics { contentDescription = description },
                    horizontalArrangement = Arrangement.spacedBy(gap),
                ) {
                    repeat(state.wordLength) { index ->
                        val letter = guess?.letters?.get(index)
                        GuessTile(letter?.letter?.toString().orEmpty(), letter?.mark, active,
                            tileWidth = tileWidth, tileHeight = (52 * fontScale).dp,
                            modifier = Modifier.testTag("board_cell_${row}_$index"))
                    }
                }
            }
        }
    }
}

@Composable
internal fun GuessTile(
    letter: String,
    mark: LetterMark?,
    active: Boolean,
    tileWidth: Dp,
    tileHeight: Dp,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    val style = letterStyle(mark)
    Surface(
        modifier = modifier.width(tileWidth).height(tileHeight),
        shape = RoundedCornerShape(8.dp),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else style.background,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else style.foreground,
        border = BorderStroke(if (active) 2.dp else 1.dp,
            if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(letter, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            if (mark != null) Text(style.symbol, fontSize = 12.sp, lineHeight = 14.sp, fontWeight = FontWeight.Bold)
            else if (selected) Text("▁", fontSize = 12.sp, lineHeight = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
internal fun LetterLegend() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        LetterMark.entries.forEach { mark ->
            val style = letterStyle(mark)
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                Surface(shape = RoundedCornerShape(4.dp), color = style.background, contentColor = style.foreground) {
                    Text(style.symbol, modifier = Modifier.width(24.dp).clearAndSetSemantics {},
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold)
                }
                Text(markLabel(mark), style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
