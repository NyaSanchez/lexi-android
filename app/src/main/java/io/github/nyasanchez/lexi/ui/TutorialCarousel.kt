package io.github.nyasanchez.lexi.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.nyasanchez.lexi.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TutorialCarousel(onDismiss: () -> Unit) {
    // Lista de las imágenes temporales
    val pages = listOf(
        R.drawable.tutorial_1,
        R.drawable.tutorial_2,
        R.drawable.tutorial_3
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    // Fondo semitransparente que cubre toda la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            // El Carrusel deslizable
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                Image(
                    painter = painterResource(id = pages[page]),
                    contentDescription = "Tutorial página ${page + 1}",
                    contentScale = ContentScale.Fit, // Asegura que la imagen no se recorte
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Indicadores de página (los puntitos)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(pages.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) {
                        MaterialTheme.colorScheme.primary // Punto activo
                    } else {
                        Color.Gray // Punto inactivo
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
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                // Si es la última página dice "Empezar", si no, dice "Omitir tutorial"
                val buttonText = if (pagerState.currentPage == pages.lastIndex) "Empezar práctica" else "Omitir tutorial"
                Text(text = buttonText)
            }
        }
    }
}