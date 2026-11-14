package io.github.nyasanchez.lexi.domain.review

import io.github.nyasanchez.lexi.data.progress.ReviewEntry
import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ReviewRulesTest {
    private val catalog = LocalVocabularyRepository().allLevels()

    @Test
    fun mostRecentLossesComeFirst() {
        val items = ReviewRules.items(
            catalog,
            listOf(
                ReviewEntry("house", VocabularyLanguage.ENGLISH.name, lastLostAt = 100),
                ReviewEntry("apple", VocabularyLanguage.ENGLISH.name, lastLostAt = 300),
                ReviewEntry("tree", VocabularyLanguage.SPANISH.name, lastLostAt = 200),
            ),
        )
        assertEquals(listOf("apple", "tree", "house"), items.map { it.entry.id })
    }

    @Test
    fun equalTimesAreOrderedByIdSoTheListIsStable() {
        val items = ReviewRules.items(
            catalog,
            listOf(
                ReviewEntry("tree", VocabularyLanguage.ENGLISH.name, lastLostAt = 100),
                ReviewEntry("apple", VocabularyLanguage.ENGLISH.name, lastLostAt = 100),
            ),
        )
        assertEquals(listOf("apple", "tree"), items.map { it.entry.id })
    }

    @Test
    fun wordsMissingFromTheCatalogOrWithUnknownLanguageAreIgnored() {
        val items = ReviewRules.items(
            catalog,
            listOf(
                ReviewEntry("no-longer-exists", VocabularyLanguage.ENGLISH.name, lastLostAt = 100),
                ReviewEntry("house", "KLINGON", lastLostAt = 100),
                ReviewEntry("house", VocabularyLanguage.ENGLISH.name, lastLostAt = 100),
            ),
        )
        assertEquals(listOf("house"), items.map { it.entry.id })
    }

    @Test
    fun emptyPendingGivesAnEmptyList() {
        assertTrue(ReviewRules.items(catalog, emptyList()).isEmpty())
    }

    @Test
    fun theSameWordInBothModesIsTwoDifferentItems() {
        val items = ReviewRules.items(
            catalog,
            listOf(
                ReviewEntry("house", VocabularyLanguage.ENGLISH.name, lastLostAt = 100),
                ReviewEntry("house", VocabularyLanguage.SPANISH.name, lastLostAt = 100),
            ),
        )
        assertEquals(2, items.size)
        assertNotEquals(items[0].key, items[1].key)
    }

    @Test
    fun theExerciseIsReplayedInTheModeWhereTheWordWasLost() {
        val items = ReviewRules.items(
            catalog,
            listOf(
                ReviewEntry("house", VocabularyLanguage.ENGLISH.name, lastLostAt = 200),
                ReviewEntry("house", VocabularyLanguage.SPANISH.name, lastLostAt = 100),
            ),
        ).associateBy { it.answerLanguage }

        assertEquals("house", items.getValue(VocabularyLanguage.ENGLISH).exercise.answer)
        assertEquals("casa", items.getValue(VocabularyLanguage.SPANISH).exercise.answer)
    }
}
