package io.github.nyasanchez.lexi.data.vocabulary

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class LocalVocabularyRepositoryTest {
    private val repository = LocalVocabularyRepository()

    @Test
    fun initialCatalogContainsThirtyDistinctBilingualPairs() {
        val entries = repository.all()

        assertEquals(30, entries.size)
        assertEquals(entries.size, entries.map { it.id }.distinct().size)
        assertEquals(entries.size, entries.map { it.spanish.word }.distinct().size)
        assertEquals(entries.size, entries.map { it.english.word }.distinct().size)
        assertTrue(entries.all { it.level == VocabularyLevel.INITIAL })
    }

    @Test
    fun eachInitialExampleIncludesItsVocabularyWord() {
        repository.all().forEach { entry ->
            listOf(entry.spanish, entry.english).forEach { text ->
                val wholeWord = Regex("(?iuU)\\b${Regex.escape(text.word)}\\b")
                assertTrue("${entry.id}: falta ${text.word} en el ejemplo", wholeWord.containsMatchIn(text.example))
            }
        }
    }

    @Test
    fun bothDirectionsProduceCompleteExercisesForEveryEntry() {
        val english = repository.exercises(VocabularyLanguage.ENGLISH)
        val spanish = repository.exercises(VocabularyLanguage.SPANISH)
        assertEquals(60, english.size + spanish.size)
        assertEquals(english.map { it.entryId }, spanish.map { it.entryId })

        english.zip(spanish).forEach { (forward, reverse) ->
            assertEquals(forward.answer, reverse.translation)
            assertEquals(forward.translation, reverse.answer)
            assertEquals(forward.example, reverse.translatedExample)
            assertEquals(forward.translatedExample, reverse.example)
            assertNotEquals(forward.clueLanguage, forward.answerLanguage)
            assertNotEquals(reverse.clueLanguage, reverse.answerLanguage)
            assertTrue(forward.definition.isNotBlank() && reverse.definition.isNotBlank())
            assertTrue(forward.letterCount > 0 && reverse.letterCount > 0)
        }
    }

    @Test
    fun duplicateIdsAreRejectedBeforeTheyCanOverwriteContent() {
        val house = requireNotNull(repository.findById("house"))
        val appleWithHouseId = requireNotNull(repository.findById("apple")).copy(id = "house")

        assertThrows(IllegalArgumentException::class.java) {
            LocalVocabularyRepository(listOf(house, appleWithHouseId))
        }
    }

    @Test
    fun emptyCatalogAndUnknownIdsAreHandledWithoutInventingAWord() {
        val empty = LocalVocabularyRepository(emptyList())
        assertTrue(empty.all().isEmpty())
        assertTrue(empty.exercises(VocabularyLanguage.SPANISH).isEmpty())
        assertTrue(empty.exercises(VocabularyLanguage.ENGLISH).isEmpty())
        assertNull(empty.findById("house"))
        assertNull(repository.findById("missing_word"))
        assertEquals("casa", repository.findById("house")?.spanish?.word)
    }

    @Test
    fun externalListChangesDoNotChangeTheRepository() {
        val source = repository.all().toMutableList()
        val isolated = LocalVocabularyRepository(source)
        source.clear()
        assertEquals(30, isolated.all().size)

        val returned = isolated.all()
        (returned as MutableList<VocabularyEntry>).clear()
        assertEquals(30, isolated.all().size)
        assertEquals("house", isolated.findById("house")?.english?.word)
    }
}
