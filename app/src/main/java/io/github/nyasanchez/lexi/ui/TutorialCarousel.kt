package io.github.nyasanchez.lexi.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.nyasanchez.lexi.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialCarousel(onDismiss: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    // Fondo adaptable al tema (claro u oscuro) en lugar de ser totalmente negro
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.95f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = "¡Bienvenido a Lexi!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // El Carrusel nativo
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> TutorialPage(
                        title = "Elige tu práctica",
                        description = "1. Selecciona tu modo de juego preferido.\n\n2. Haz clic en 'Empezar' para iniciar la partida.\n\nNOTA. Cada modo es independiente al otro.",
                        iconRes = R.drawable.ic_book
                    )
                    1 -> TutorialPage(
                        title = "Adivina la palabra",
                        description = "Escribe la palabra correcta. Los colores te indicarán si las letras están en la posición adecuada.",
                        iconRes = R.drawable.ic_progress
                    )
                    2 -> TutorialPage(
                        title = "Mantén tu racha",
                        description = "Completa tu práctica diaria para sumar días a tu racha de estudio. ¡No te rindas!",
                        iconRes = R.drawable.ic_flame
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Indicadores de página (los puntitos)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(3) { iteration ->
                    val color = if (pagerState.currentPage == iteration) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outlineVariant
                    }
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para cerrar el tutorial
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                val buttonText = if (pagerState.currentPage == 2) "¡Empezar a jugar!" else "Omitir tutorial"
                Text(text = buttonText, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

// Componente reutilizable para cada tarjeta del tutorial
@Composable
fun TutorialPage(title: String, description: String, iconRes: Int) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}