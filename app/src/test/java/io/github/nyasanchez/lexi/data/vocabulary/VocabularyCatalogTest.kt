package io.github.nyasanchez.lexi.data.vocabulary

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VocabularyCatalogTest {
    private val entries = LocalVocabularyRepository().allLevels()
    private val maxLetters = 10

    private fun containsWord(text: String, word: String) =
        Regex("(?iuU)\\b${Regex.escape(word)}\\b").containsMatchIn(text)

    @Test
    fun aDefinitionNeverContainsItsOwnWord() {
        entries.forEach {
            assertFalse("${it.id}: la definición en español contiene ${it.spanish.word}",
                containsWord(it.spanish.definition, it.spanish.word))
            assertFalse("${it.id}: la definición en inglés contiene ${it.english.word}",
                containsWord(it.english.definition, it.english.word))
        }
    }

    @Test
    fun answersFitOnTheBoard() {
        entries.forEach {
            assertTrue("${it.id}: ${it.spanish.word} supera $maxLetters letras", it.spanish.word.length <= maxLetters)
            assertTrue("${it.id}: ${it.english.word} supera $maxLetters letras", it.english.word.length <= maxLetters)
        }
    }
}

