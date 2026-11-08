package io.github.nyasanchez.lexi

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.click
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.nyasanchez.lexi.data.progress.PracticeSessionStore
import io.github.nyasanchez.lexi.ui.PracticeMode
import java.util.UUID
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BoardInputTest {
    @get:Rule(order = 0) val sessions = PracticeSessionRule()
    @get:Rule(order = 1) val compose = createAndroidComposeRule<MainActivity>()

    private fun start(mode: PracticeMode = PracticeMode.ENGLISH, entry: String = "house") {
        PracticeSessionStore(compose.activity).save(mode.id,
            PracticeSessionStore.Session(entry, emptyList(), UUID.randomUUID().toString(), 0, ""))
        compose.onNodeWithText(compose.activity.getString(mode.title)).performScrollTo().performClick()
        compose.onNodeWithText("Empezar").performScrollTo().performClick()
        revealInput()
    }

    private fun revealInput() {
        compose.onNodeWithTag("practice_list").performScrollToNode(hasSetTextAction())
    }

    private fun check() {
        compose.onNodeWithTag("practice_list").performScrollToNode(hasText("Comprobar"))
        compose.onNodeWithText("Comprobar").performClick()
        compose.waitForIdle()
    }

    @Test fun lettersAreEditedInTheActiveRowAndEvaluatedOnlyOnCheck() {
        start()
        compose.onNodeWithText("Tu respuesta").assertDoesNotExist()
        compose.onNodeWithTag("board_input").performTextInput("SHARE")
        compose.onNodeWithTag("board_input").assertTextContains("SHARE")
        compose.onNodeWithContentDescription("Intento 1: SHARE", substring = true).assertDoesNotExist()
        check()
        compose.onNodeWithContentDescription("Intento 1: SHARE. Letra 1: S, Otra posición; Letra 2: H, Otra posición; Letra 3: A, Sin coincidencia; Letra 4: R, Sin coincidencia; Letra 5: E, Posición correcta").assertExists()
        revealInput()
        compose.onNodeWithTag("board_input").assertTextEquals("")
        compose.onNodeWithTag("board_input").performTextInput("HO")
        compose.activityRule.scenario.recreate()
        revealInput()
        compose.onNodeWithTag("board_input").assertTextContains("HO")
        compose.onNodeWithTag("board_input").performTextInput("USE")
        check()
        compose.onNodeWithTag("board_input").assertDoesNotExist()
        compose.onNodeWithContentDescription("Intento 2: HOUSE. Letra 1: H, Posición correcta; Letra 2: O, Posición correcta; Letra 3: U, Posición correcta; Letra 4: S, Posición correcta; Letra 5: E, Posición correcta").assertExists()
        compose.onNodeWithTag("practice_list").performScrollToNode(hasText("Ver resultado"))
        compose.onNodeWithText("Ver resultado").performClick()
        compose.onNodeWithText("HOUSE · casa").assertIsDisplayed()
    }

    @Test fun tappingAFilledTileReplacesThatLetterAndLongPasteDoesNotTruncate() {
        start()
        compose.onNodeWithTag("board_input").performTextInput("HAXSE")
        // Segunda casilla: la siguiente letra sustituye la A, no se agrega al final.
        compose.onNodeWithTag("board_input").performTouchInput { click(Offset(width * 0.3f, height / 2f)) }
        compose.onNodeWithTag("board_input").performTextInput("O")
        compose.onNodeWithTag("board_input").assertTextContains("HOXSE")
        // Seleccionar y borrar la tercera casilla, luego escribir U en ese lugar.
        compose.onNodeWithTag("board_input").performTouchInput { click(Offset(width * 0.5f, height / 2f)) }
        compose.onNodeWithTag("board_input").performTextInput("")
        compose.onNodeWithTag("board_input").assertTextContains("HOSE")
        compose.onNodeWithTag("board_input").performTextInput("U")
        compose.onNodeWithTag("board_input").assertTextContains("HOUSE")
        compose.onNodeWithTag("board_input").performTextReplacement("HOUSEX")
        compose.onNodeWithTag("board_input").assertTextContains("HOUSE")
        compose.onNodeWithContentDescription("Intento 1: HOUSE", substring = true).assertDoesNotExist()
    }

    @Test fun spanishAccentsUseOneTileAndInvalidAnswersDoNotConsumeAttempts() {
        start(PracticeMode.SPANISH, "tree")
        compose.onNodeWithTag("board_input").performTextInput("HO")
        check()
        compose.onNodeWithText("La respuesta debe tener 5 letras. No perdiste un intento.").assertExists()
        revealInput()
        compose.onNodeWithTag("board_input").assertTextContains("HO")
        compose.onNodeWithTag("board_input").performTextReplacement("12345")
        check()
        compose.onNodeWithTag("practice_list").performScrollToNode(hasText("Intentos: 0 de 6"))
        compose.onNodeWithText("Intentos: 0 de 6").assertExists()
        revealInput()
        compose.onNodeWithTag("board_input").performTextReplacement("ARBOL")
        check()
        compose.onNodeWithContentDescription("Letra 1: A, Sin coincidencia", substring = true).assertExists()
        revealInput()
        compose.onNodeWithTag("board_input").performTextInput("a\u0301rbol")
        compose.onNodeWithTag("board_input").assertTextContains("árbol")
        check()
        compose.onNodeWithContentDescription("Intento 2: ÁRBOL", substring = true).assertExists()
        compose.onNodeWithTag("practice_list").performScrollToNode(hasText("¡Acertaste!"))
        compose.onNodeWithText("¡Acertaste!").assertIsDisplayed()
    }

    @Test fun enyeUsesOneTileAndIsNotReplacedByN() {
        start(PracticeMode.SPANISH, "year")
        compose.onNodeWithTag("board_input").performTextInput("ano")
        check()
        compose.onNodeWithContentDescription("Letra 2: N, Sin coincidencia", substring = true).assertExists()
        revealInput()
        compose.onNodeWithTag("board_input").performTextInput("an\u0303o")
        compose.onNodeWithTag("board_input").assertTextContains("año")
        check()
        compose.onNodeWithContentDescription("Intento 2: AÑO", substring = true).assertExists()
    }
}
