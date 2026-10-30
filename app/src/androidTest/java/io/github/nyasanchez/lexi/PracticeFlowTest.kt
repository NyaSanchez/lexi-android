package io.github.nyasanchez.lexi

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import java.io.File
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PracticeFlowTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    private fun start(spanish: Boolean = false) {
        if (spanish) compose.onNodeWithText("Definition in English").performScrollTo().performClick()
        compose.onNodeWithText("Empezar").performScrollTo().performClick()
    }

    private fun input(text: String) {
        compose.onNodeWithTag("practice_list").performScrollToNode(hasSetTextAction())
        compose.onNode(hasSetTextAction()).performTextReplacement(text)
    }

    private fun submit(text: String) {
        input(text)
        compose.onNode(hasSetTextAction()).performImeAction()
        compose.waitForIdle()
    }

    private fun show(text: String) {
        compose.onNodeWithTag("practice_list").performScrollToNode(hasText(text))
        compose.onNodeWithText(text).assertIsDisplayed()
    }

    private fun next() {
        show("Siguiente palabra")
        compose.onNodeWithText("Siguiente palabra").performClick()
    }

    private fun screenshot(name: String) {
        val bitmap = compose.onRoot().captureToImage().asAndroidBitmap()
        File(compose.activity.cacheDir, name).outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
    }

    @Test
    fun invalidInputAndRepeatedLetterHintsSurviveActivityRecreation() {
        start()
        compose.onNodeWithText("Escribe en inglés").assertIsDisplayed()
        submit("HO")
        compose.onNodeWithText("La respuesta debe tener 5 letras. No perdiste un intento.").assertExists()
        show("Intentos: 0 de 6")
        submit("SHARE")
        val row = "Intento 1: SHARE. Letra 1: S, Otra posición; Letra 2: H, Otra posición; Letra 3: A, Sin coincidencia; Letra 4: R, Sin coincidencia; Letra 5: E, Posición correcta"
        compose.onNodeWithContentDescription(row).assertIsDisplayed()
        screenshot("lexi-board.png")
        input("HOU")
        compose.activityRule.scenario.recreate()
        compose.onNodeWithTag("practice_list").performScrollToNode(hasSetTextAction())
        compose.onNode(hasSetTextAction()).assertTextContains("HOU")
        show("Intentos: 1 de 6")
        compose.onNodeWithContentDescription(row).assertExists()
        submit("HOUSE")
        show("¡Acertaste!")
        screenshot("lexi-win.png")
        next()
        show("Palabra 2 de 30")
        show("Intentos: 0 de 6")
        submit("PAPAL")
        compose.onNodeWithTag("practice_list").performScrollToNode(hasContentDescription("Intento 1: PAPAL", substring = true))
        compose.onNodeWithContentDescription("Letra 4: A, Sin coincidencia", substring = true).assertIsDisplayed()
    }

    @Test
    fun spanishModeAcceptsAccentsAndUsesSpanishWordLengths() {
        start(spanish = true)
        compose.onNodeWithText("Escribe en español").assertIsDisplayed()
        compose.onNodeWithText("4 letras").assertIsDisplayed()
        submit("CASA")
        show("¡Acertaste!")
        show("Esta es mi casa.")
        show("This is my house.")
        next()
        submit("MANZANA")
        show("¡Acertaste!")
        next()
        submit("ARBOL")
        show("Intentos: 1 de 6")
        compose.onNodeWithContentDescription("Letra 1: A, Sin coincidencia", substring = true).assertExists()
        submit("a\u0301rbol")
        show("¡Acertaste!")
        compose.onNodeWithText("ÁRBOL · tree").assertIsDisplayed()
        show("El árbol da sombra.")
        show("The tree gives shade.")
    }

    @Test
    fun sixWrongAnswersShowLossAndNextWordStartsClean() {
        start()
        repeat(6) { submit("SHARE") }
        show("Intentos agotados")
        compose.onNodeWithText("HOUSE · casa").assertIsDisplayed()
        compose.onNode(hasSetTextAction()).assertDoesNotExist()
        show("This is my house.")
        show("Esta es mi casa.")
        screenshot("lexi-loss.png")
        next()
        show("Intentos: 0 de 6")
        submit("APPLE")
        show("¡Acertaste!")
    }

    @Test
    fun learningResultRestoresAndNextWordHidesThePreviousExamples() {
        start()
        compose.onNodeWithText("This is my house.").assertDoesNotExist()
        submit("HOUSE")
        show("Lo resolviste en 1 intento.")
        show("This is my house.")
        show("Esta es mi casa.")
        show("Ver mis intentos")
        compose.onNodeWithText("Ver mis intentos").performClick()
        show("Intentos: 1 de 6")
        compose.activityRule.scenario.recreate()
        show("Ocultar mis intentos")
        compose.onNodeWithText("Ocultar mis intentos").performClick()
        show("This is my house.")
        next()
        show("Palabra 2 de 30")
        compose.onNodeWithText("This is my house.").assertDoesNotExist()
        compose.onNodeWithText("The apple is on the table.").assertDoesNotExist()
        submit("APPLE")
        show("The apple is on the table.")
        show("La manzana está sobre la mesa.")
    }

    @Test
    fun exitCanBeCancelledWithoutLosingTheAttempt() {
        start()
        submit("SHARE")
        compose.onNodeWithTag("practice_list").performScrollToNode(hasContentDescription("Volver al inicio"))
        compose.onNodeWithContentDescription("Volver al inicio").performClick()
        compose.onNodeWithText("¿Salir de esta partida?").assertIsDisplayed()
        compose.onNodeWithText("Seguir jugando").performClick()
        show("Intentos: 1 de 6")
        compose.onNodeWithTag("practice_list").performScrollToNode(hasContentDescription("Volver al inicio"))
        compose.onNodeWithContentDescription("Volver al inicio").performClick()
        compose.onNodeWithText("Salir").performClick()
        compose.onNodeWithText("Tu práctica de hoy").assertIsDisplayed()
    }
}
