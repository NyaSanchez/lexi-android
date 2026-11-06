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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.nyasanchez.lexi.R  
import io.github.nyasanchez.lexi.ui.theme.LexiTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun HomeScreen(
    selectedMode: PracticeMode,
    streak: Int,
    completedToday: Boolean,
    onModeSelected: (PracticeMode) -> Unit,
    onStart: () -> Unit,
) {
    // 1. Variable local para saber si el usuario ya cerró el tutorial hoy
    var hasDismissedTutorial by rememberSaveable { mutableStateOf(false) }

    // 2. Lógica para mostrarlo: racha en 0 y no haberlo cerrado
    val shouldShowTutorial = streak == 0 && !hasDismissedTutorial

    // 3. Box principal para poder encimar el tutorial
    Box(modifier = Modifier.fillMaxSize()) {

        // --- TU INTERFAZ ORIGINAL (NO SE MODIFICA NADA AQUÍ) ---
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
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(painterResource(R.drawable.ic_progress), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text(stringResource(R.string.initial_level), style = MaterialTheme.typography.titleSmall)
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

        // --- LA CAPA DEL TUTORIAL SUPERPUESTA ---
        if (shouldShowTutorial) {
            TutorialCarousel(
                onDismiss = { hasDismissedTutorial = true }
            )
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

@Preview(showBackground = true, widthDp = 360, name = "Inicio claro")
@Preview(showBackground = true, widthDp = 360, name = "Inicio oscuro", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, widthDp = 320, fontScale = 1.5f, name = "Inicio texto grande")
@Composable
private fun HomePreview() {
    LexiTheme {
        HomeScreen(
            selectedMode = PracticeMode.ENGLISH,
            streak = 0,
            completedToday = false, // <- Agrega esta línea
            onModeSelected = {},
            onStart = {}
        )
    }
}

