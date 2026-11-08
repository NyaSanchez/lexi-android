package io.github.nyasanchez.lexi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ContributorsScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Equipo de Desarrollo",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
        )

        ContributorCard(
            name = "Angel Isai Orozco Aguilar",
            role = "Desarrollo",
            github = "@GoldPanter21"
        )

        Spacer(modifier = Modifier.height(16.dp))

        ContributorCard(
            name = "Angel Ricardo Téllez Girón Castro",
            role = "Desarrollo",
            github = "@Angelricardotgc"
        )

        Spacer(modifier = Modifier.height(16.dp))

        ContributorCard(
            name = "Zyanya Maxi Sánchez Valadez",
            role = "Desarrollo",
            github = "@NyaSanchez"
        )
    }
}

@Composable
private fun ContributorCard(name: String, role: String, github: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(text = role, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (github.isNotEmpty()) {
                Text(
                    text = github,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}