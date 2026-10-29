package io.github.nyasanchez.lexi.domain.game

import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage
import java.text.Normalizer
import java.util.Locale

/** Las tildes combinantes de algunos teclados se convierten a su forma precompuesta. */
internal object GuessInput {
    private val englishLetters = Regex("[A-Za-z]+")
    private val spanishLetters = Regex("[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+")

    fun composeAndTrim(raw: String): String = Normalizer.normalize(raw.trim(), Normalizer.Form.NFC)

    fun hasValidLetters(word: String, language: VocabularyLanguage): Boolean = when (language) {
        VocabularyLanguage.ENGLISH -> englishLetters.matches(word)
        VocabularyLanguage.SPANISH -> spanishLetters.matches(word)
    }

    // Validar antes de transformar: caracteres como ß pueden expandirse al pasar a mayúsculas.
    fun uppercase(validWord: String): String = validWord.uppercase(Locale.ROOT)
}
