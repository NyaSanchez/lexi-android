package io.github.nyasanchez.lexi.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.nyasanchez.lexi.R  
import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLevel
import io.github.nyasanchez.lexi.domain.level.LevelRules
import io.github.nyasanchez.lexi.domain.level.LevelStatus
import io.github.nyasanchez.lexi.ui.theme.LexiTheme

@Composable
fun HomeScreen(
    selectedMode: PracticeMode,
    selectedLevel: VocabularyLevel,
    levels: List<LevelStatus>,
    streak: Int,
    completedToday: Boolean,
    onModeSelected: (PracticeMode) -> Unit,
    onLevelSelected: (VocabularyLevel) -> Unit,
    onStart: () -> Unit,
) {
    ScreenColumn {
        LexiBrand()
        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.semantics { heading() },
        )
        DailyGoalCard(streak = streak, completedToday = completedToday)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = stringResource(R.string.practice_mode_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Column(
                modifier = Modifier.selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                PracticeMode.entries.forEach { mode ->
                    ModeOption(mode = mode, selected = selectedMode == mode, onClick = { onModeSelected(mode) })
                }
            }
        }
        if (levels.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = stringResource(R.string.level_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Column(
                    modifier = Modifier.selectableGroup(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    levels.forEachIndexed { index, status ->
                        LevelOption(
                            status = status,
                            previous = levels.getOrNull(index - 1),
                            selected = status.level == selectedLevel,
                            onClick = { onLevelSelected(status.level) },
                        )
                    }
                }
            }
        }
        Button(
            onClick = onStart,
            modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
            shape = RoundedCornerShape(14.dp),
        ) {
            Text(stringResource(R.string.start_practice), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.size(12.dp))
            Icon(painterResource(R.drawable.ic_arrow_forward), contentDescription = null)
        }
    }
}

@Composable
private fun DailyGoalCard(streak : Int, completedToday: Boolean) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(painterResource(R.drawable.ic_flame), contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(44.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(stringResource(R.string.daily_practice_title), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                val streakText = when {
                    completedToday && streak > 0 -> stringResource(R.string.daily_streak, streak)
                    !completedToday && streak > 0 -> "¡Racha en riesgo! " + stringResource(R.string.daily_goal)
                    else -> stringResource(R.string.daily_goal)
                }
                Text(streakText, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun ModeOption(mode: PracticeMode, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Surface(
        shape = shape,
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            if (selected) 2.dp else 1.dp,
            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.clip(shape)
                .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
                .heightIn(min = 80.dp)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            RadioButton(selected = selected, onClick = null)
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(stringResource(mode.title), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(stringResource(mode.subtitle), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

/**
 * Tarjeta de un nivel. Si está bloqueada muestra cuánto falta del nivel anterior para abrirla
 * (por ejemplo «42 de 70 para desbloquear») y no se puede elegir.
 */
@Composable
private fun LevelOption(status: LevelStatus, previous: LevelStatus?, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    val locked = !status.unlocked
    val levelName = stringResource(status.level.labelRes)
    val lockedHint = if (locked && previous != null) {
        stringResource(R.string.level_locked_hint, stringResource(previous.level.labelRes), LevelRules.UNLOCK_PERCENT)
    } else null
    val lockedState = stringResource(R.string.level_state_locked)
    // stringResource es composable y no puede llamarse dentro del lambda de joinToString.
    val sublevelLabels = status.sublevels.map {
        stringResource(R.string.level_sublevel_progress, it.cefr.name, it.won, it.total)
    }

    Surface(
        shape = shape,
        color = when {
            selected -> MaterialTheme.colorScheme.primaryContainer
            locked -> MaterialTheme.colorScheme.surfaceVariant
            else -> MaterialTheme.colorScheme.surface
        },
        border = BorderStroke(
            if (selected) 2.dp else 1.dp,
            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.clip(shape)
                .selectable(selected = selected, enabled = !locked, role = Role.RadioButton, onClick = onClick)
                .semantics { if (locked) stateDescription = lockedHint ?: lockedState }
                .heightIn(min = 80.dp)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            RadioButton(selected = selected, onClick = null, enabled = !locked)
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        levelName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (locked) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        stringResource(status.level.cefrRangeRes),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                if (locked && previous != null) {
                    // Lo que falta es el avance del nivel anterior, no el de este.
                    LevelProgressBar(previous.won, previous.required)
                    Text(
                        stringResource(R.string.level_locked_progress, previous.won, previous.required),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                } else {
                    LevelProgressBar(status.won, status.total)
                    Text(
                        stringResource(R.string.level_won_progress, status.won, status.total),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        sublevelLabels.joinToString("   "),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    when {
                        status.completed -> Text(
                            stringResource(R.string.level_completed),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        status.goalReached -> Text(
                            stringResource(R.string.level_goal_reached),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LevelProgressBar(value: Int, max: Int) {
    LinearProgressIndicator(
        progress = { if (max <= 0) 0f else (value.toFloat() / max).coerceIn(0f, 1f) },
        modifier = Modifier.fillMaxWidth(),
        trackColor = MaterialTheme.colorScheme.outlineVariant,
    )
}

@Preview(showBackground = true, widthDp = 360, name = "Inicio claro")
@Preview(showBackground = true, widthDp = 360, name = "Inicio oscuro", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, widthDp = 320, fontScale = 1.5f, name = "Inicio texto grande")
@Composable
private fun HomePreview() {
    LexiTheme {
        HomeScreen(
            selectedMode = PracticeMode.ENGLISH,
            selectedLevel = VocabularyLevel.BASIC,
            levels = LevelRules.statuses(LocalVocabularyRepository().allLevels(), emptySet()),
            streak = 0,
            completedToday = false,
            onModeSelected = {},
            onLevelSelected = {},
            onStart = {},
        )
    }
}
