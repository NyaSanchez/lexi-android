package io.github.nyasanchez.lexi.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.text.Normalizer
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color

/** Un solo editor conserva el teclado, pegado y selección; su presentación es la fila de casillas. */
@Composable
internal fun GuessRowEditor(
    input: TextFieldState,
    length: Int,
    row: Int,
    tileWidth: Dp,
    tileHeight: Dp,
    gap: Dp,
    description: String,
    errorText: String?,
    onSubmit: () -> Unit,
) {
    val focus = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    var focused by remember { mutableStateOf(false) }
    val bringIntoView = remember { BringIntoViewRequester() }
    val density = LocalDensity.current
    val selection = input.selection
    val text = input.text.toString()
    val current = selection.min.coerceAtMost(length - 1)
    val transformation = remember(length) {
        InputTransformation {
            // NFC antes del límite: a + acento combinante ocupa una casilla, no dos.
            val normalized = Normalizer.normalize(asCharSequence().toString(), Normalizer.Form.NFC).trim()
            if (normalized.length > length) {
                // Rechazar el cambio completo: pegar una palabra larga nunca envía un prefijo distinto.
                revertAllChanges()
            } else if (normalized != asCharSequence().toString()) {
                val before = Normalizer.normalize(asCharSequence().take(this.selection.start).toString(), Normalizer.Form.NFC).trimStart()
                val after = Normalizer.normalize(asCharSequence().take(this.selection.end).toString(), Normalizer.Form.NFC).trimStart()
                replace(0, this.length, normalized)
                this.selection = TextRange(before.length.coerceAtMost(normalized.length), after.length.coerceAtMost(normalized.length))
            }
        }
    }
    val transparentColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.Transparent
    )

    LaunchedEffect(focused, current, tileWidth) {
        if (focused) {
            val left = with(density) { ((tileWidth + gap) * current).toPx() }
            bringIntoView.bringIntoView(
                Rect(
                    left,
                    0f,
                    left + with(density) { tileWidth.toPx() },
                    with(density) { tileHeight.toPx() })
            )
        }
    }
    CompositionLocalProvider(LocalTextSelectionColors provides transparentColors) {
        BasicTextField(
            state = input,
            inputTransformation = transformation,
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                autoCorrectEnabled = false,
                imeAction = ImeAction.Done,
            ),
            onKeyboardAction = { onSubmit() },
            cursorBrush = SolidColor(Color.Transparent), // Aseguramos que el cursor parpadeante no se dibuje
            modifier = Modifier.width(tileWidth * length + gap * (length - 1)).height(tileHeight)
                .testTag("board_input")
                .focusRequester(focus)
                .onFocusChanged { focused = it.isFocused }
                .bringIntoViewRequester(bringIntoView)
                .semantics {
                    contentDescription = description
                    if (errorText != null) error(errorText)
                },
            decorator = { innerTextField ->
                Box(
                    // Escudo: si el usuario toca los huecos vacíos entre las casillas, abrimos el teclado
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focus.requestFocus()
                            keyboard?.show()
                        })
                    }
                ) {
                    // Magia aquí: Reducimos el campo nativo a 0.dp.
                    // Ya no ocupa espacio en pantalla, por lo que nunca recibirá toques
                    // accidentales ni podrá generar el selector fantasma.
                    Box(Modifier.size(0.dp).alpha(0f)) { innerTextField() }

                    Row(Modifier.clearAndSetSemantics {}, horizontalArrangement = Arrangement.spacedBy(gap)) {
                        repeat(length) { index ->
                            val selected = focused && if (selection.collapsed) index == current
                            else index in selection.min until selection.max
                            GuessTile(
                                letter = text.getOrNull(index)?.uppercaseChar()?.toString().orEmpty(),
                                mark = null,
                                active = true,
                                selected = selected,
                                tileWidth = tileWidth,
                                tileHeight = tileHeight,
                                modifier = Modifier.testTag("board_cell_${row}_$index")
                                    .pointerInput(input, index) {
                                        detectTapGestures(onTap = {
                                            val position = index.coerceAtMost(input.text.length)
                                            input.edit {
                                                this.selection = if (position < length) TextRange(position, (position + 1).coerceAtMost(this.length))
                                                else TextRange(this.length)
                                            }
                                            focus.requestFocus()
                                            keyboard?.show()
                                        })
                                    },
                            )
                        }
                    }
                }
            },
        )
    }
}