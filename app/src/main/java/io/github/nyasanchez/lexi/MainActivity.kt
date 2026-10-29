package io.github.nyasanchez.lexi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.nyasanchez.lexi.ui.LexiApp
import io.github.nyasanchez.lexi.ui.theme.LexiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LexiTheme {
                LexiApp()
            }
        }
    }
}
