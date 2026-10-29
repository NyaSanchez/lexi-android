package io.github.nyasanchez.lexi.data.vocabulary

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class VocabularyEntryTest {
    private val repository = LocalVocabularyRepository()
    private val house = requireNotNull(repository.findById("house"))

    @Test
    fun englishAnswerUsesSpanishDefinitionAndEnglishExample() {
        val exercise = house.exerciseFor(VocabularyLanguage.ENGLISH)

        assertEquals(VocabularyLanguage.SPANISH, exercise.clueLanguage)
        assertEquals(VocabularyLanguage.ENGLISH, exercise.answerLanguage)
        assertEquals("Edificio donde vive una persona o una familia.", exercise.definition)
        assertEquals("house", exercise.answer)
        assertEquals(5, exercise.letterCount)
        assertEquals("casa", exercise.translation)
        assertEquals("This is my house.", exercise.example)
        assertEquals("Esta es mi casa.", exercise.translatedExample)
    }

    @Test
    fun spanishAnswerReversesDefinitionTranslationAndExample() {
        val exercise = house.exerciseFor(VocabularyLanguage.SPANISH)

        assertEquals(VocabularyLanguage.ENGLISH, exercise.clueLanguage)
        assertEquals(VocabularyLanguage.SPANISH, exercise.answerLanguage)
        assertEquals("A building where a person or a family lives.", exercise.definition)
        assertEquals("casa", exercise.answer)
        assertEquals(4, exercise.letterCount)
        assertEquals("house", exercise.translation)
        assertEquals("Esta es mi casa.", exercise.example)
        assertEquals("This is my house.", exercise.translatedExample)
    }

    @Test
    fun accentsAndEnyeArePreservedAndCountAsOneLetter() {
        val examples = mapOf(
            "tree" to ("árbol" to 5),
            "pencil" to ("lápiz" to 5),
            "bird" to ("pájaro" to 6),
            "mountain" to ("montaña" to 7),
            "year" to ("año" to 3),
        )
        examples.forEach { (id, expected) ->
            val exercise = requireNotNull(repository.findById(id)).exerciseFor(VocabularyLanguage.SPANISH)
            assertEquals(id, expected.first, exercise.answer)
            assertEquals(id, expected.second, exercise.letterCount)
        }
        val apple = requireNotNull(repository.findById("apple")).exerciseFor(VocabularyLanguage.ENGLISH)
        assertEquals("apple", apple.answer)
        assertEquals(5, apple.letterCount)
    }

    @Test
    fun missingEditorialContentIsRejected() {
        assertThrows(IllegalArgumentException::class.java) { house.spanish.copy(word = " ") }
        assertThrows(IllegalArgumentException::class.java) { house.spanish.copy(definition = "\n") }
        assertThrows(IllegalArgumentException::class.java) { house.english.copy(example = "") }
    }

    @Test
    fun sourceWordsRequireOneLowercaseWordWithValidLanguageCharacters() {
        listOf("ice cream", "house!", "house2", "House", "árbol").forEach { invalid ->
            assertThrows(IllegalArgumentException::class.java) {
                house.copy(english = house.english.copy(word = invalid))
            }
        }
        listOf("casa azul", "casa-", "CASA", "a\u0301rbol").forEach { invalid ->
            assertThrows(IllegalArgumentException::class.java) {
                house.copy(spanish = house.spanish.copy(word = invalid))
            }
        }
        assertThrows(IllegalArgumentException::class.java) { house.spanish.copy(word = " casa ") }
    }

    @Test
    fun stableIdentifiersCannotBeBlankOrContainSpaces() {
        listOf("", " ", "house one", "House", "casa/house").forEach { invalid ->
            assertThrows(IllegalArgumentException::class.java) { house.copy(id = invalid) }
        }
    }
}
