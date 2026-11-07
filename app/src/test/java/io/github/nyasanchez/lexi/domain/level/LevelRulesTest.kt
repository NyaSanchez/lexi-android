package io.github.nyasanchez.lexi.domain.level

import io.github.nyasanchez.lexi.data.vocabulary.CefrLevel
import io.github.nyasanchez.lexi.data.vocabulary.LocalVocabularyRepository
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyEntry
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLevel
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LevelRulesTest {
    /** Catálogo sintético: [perSublevel] palabras en cada subnivel CEFR de los tres niveles. */
    private fun catalog(perSublevel: Int = 50): List<VocabularyEntry> =
        CefrLevel.entries.flatMap { cefr ->
            (1..perSublevel).map { n ->
                VocabularyEntry(
                    id = "${cefr.name.lowercase()}-$n",
                    spanish = VocabularyText("palabra", "definicion", "ejemplo"),
                    english = VocabularyText("word", "definition", "example"),
                    level = cefr.level,
                    cefr = cefr,
                )
            }
        }

    private fun idsOf(entries: List<VocabularyEntry>, level: VocabularyLevel, count: Int): Set<String> =
        entries.filter { it.level == level }.take(count).map { it.id }.toSet()

    private fun statuses(won: Set<String>, entries: List<VocabularyEntry> = catalog()) =
        LevelRules.statuses(entries, won).associateBy { it.level }

    @Test
    fun requiredIsSeventyPercentRoundedUp() {
        assertEquals(70, LevelRules.requiredToAdvance(100))
        assertEquals(35, LevelRules.requiredToAdvance(50))
        assertEquals(71, LevelRules.requiredToAdvance(101))
        assertEquals(1, LevelRules.requiredToAdvance(1))
        assertEquals(0, LevelRules.requiredToAdvance(0))
    }

    @Test
    fun withoutProgressOnlyBasicIsOpen() {
        val result = statuses(emptySet())
        assertTrue(result.getValue(VocabularyLevel.BASIC).unlocked)
        assertFalse(result.getValue(VocabularyLevel.INTERMEDIATE).unlocked)
        assertFalse(result.getValue(VocabularyLevel.ADVANCED).unlocked)
    }

    @Test
    fun seventyWonOutOfAHundredOpensIntermediate() {
        val entries = catalog()
        val almost = statuses(idsOf(entries, VocabularyLevel.BASIC, 69), entries)
        assertFalse(almost.getValue(VocabularyLevel.BASIC).goalReached)
        assertFalse(almost.getValue(VocabularyLevel.INTERMEDIATE).unlocked)
        assertEquals(1, almost.getValue(VocabularyLevel.BASIC).remaining)

        val reached = statuses(idsOf(entries, VocabularyLevel.BASIC, 70), entries)
        assertTrue(reached.getValue(VocabularyLevel.BASIC).goalReached)
        assertFalse(reached.getValue(VocabularyLevel.BASIC).completed)
        assertTrue(reached.getValue(VocabularyLevel.INTERMEDIATE).unlocked)
        assertFalse(reached.getValue(VocabularyLevel.ADVANCED).unlocked)
    }

    @Test
    fun advancedNeedsIntermediateGoalAsWell() {
        val entries = catalog()
        val basic = idsOf(entries, VocabularyLevel.BASIC, 70)
        val intermediate = idsOf(entries, VocabularyLevel.INTERMEDIATE, 70)

        assertTrue(statuses(basic + intermediate, entries).getValue(VocabularyLevel.ADVANCED).unlocked)
        // Aunque Intermedio tenga la meta, si Básico no la alcanza todo lo siguiente sigue cerrado.
        val skipped = statuses(idsOf(entries, VocabularyLevel.BASIC, 10) + intermediate, entries)
        assertFalse(skipped.getValue(VocabularyLevel.INTERMEDIATE).unlocked)
        assertFalse(skipped.getValue(VocabularyLevel.ADVANCED).unlocked)
    }

    @Test
    fun completedMeansEveryWordWon() {
        val entries = catalog()
        val all = statuses(idsOf(entries, VocabularyLevel.BASIC, 100), entries).getValue(VocabularyLevel.BASIC)
        assertTrue(all.completed)
        assertEquals(0, all.remaining)
    }

    @Test
    fun sublevelProgressIsCountedPerCefrLevel() {
        val entries = catalog()
        val won = entries.filter { it.cefr == CefrLevel.A1 }.take(12).map { it.id } +
            entries.filter { it.cefr == CefrLevel.A2 }.take(3).map { it.id }
        val basic = statuses(won.toSet(), entries).getValue(VocabularyLevel.BASIC)

        assertEquals(listOf(CefrLevel.A1, CefrLevel.A2), basic.sublevels.map { it.cefr })
        assertEquals(listOf(12, 3), basic.sublevels.map { it.won })
        assertEquals(listOf(50, 50), basic.sublevels.map { it.total })
        assertEquals(15, basic.won)
    }

    @Test
    fun anEmptyLevelNeverOpensTheNextOne() {
        val onlyBasic = catalog().filter { it.level == VocabularyLevel.BASIC }
        val result = statuses(onlyBasic.map { it.id }.toSet(), onlyBasic)
        assertEquals(0, result.getValue(VocabularyLevel.INTERMEDIATE).total)
        assertFalse(result.getValue(VocabularyLevel.INTERMEDIATE).goalReached)
        assertFalse(result.getValue(VocabularyLevel.ADVANCED).unlocked)
    }

    @Test
    fun realCatalogHasThreeLevelsOfAHundredWordsSplitFiftyFifty() {
        val result = LevelRules.statuses(LocalVocabularyRepository().allLevels(), emptySet())
        assertEquals(VocabularyLevel.entries, result.map { it.level })
        result.forEach { status ->
            assertEquals("${status.level}: palabras", 100, status.total)
            assertEquals("${status.level}: meta de avance", 70, status.required)
            assertEquals("${status.level}: subniveles", listOf(50, 50), status.sublevels.map { it.total })
        }
    }
}
