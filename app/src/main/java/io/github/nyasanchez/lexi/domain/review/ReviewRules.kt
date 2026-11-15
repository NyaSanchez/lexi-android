package io.github.nyasanchez.lexi.domain.review

import io.github.nyasanchez.lexi.data.progress.ReviewEntry
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyEntry
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyExercise
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage

/** Una palabra pendiente de repaso en un modo concreto (el idioma en que hay que responder). */
data class ReviewItem(
    val entry: VocabularyEntry,
    val answerLanguage: VocabularyLanguage,
    val lastLostAt: Long,
) {
    /** Identifica la palabra y el modo: la misma palabra puede estar pendiente en un modo y no en el otro. */
    val key: String get() = "${answerLanguage.name}/${entry.id}"

    /** El ejercicio que se vuelve a jugar; la respuesta solo debe mostrarse al terminar la partida. */
    val exercise: VocabularyExercise get() = entry.exerciseFor(answerLanguage)
}

/** Reglas de Repaso. No toca la base de datos ni la interfaz. */
object ReviewRules {
    /**
     * Convierte las palabras perdidas y aún no acertadas en elementos de repaso. Ignora las que ya no existen
     * en el catálogo (por ejemplo, tras una actualización del banco). Las perdidas más recientes van primero.
     */
    fun items(catalog: List<VocabularyEntry>, pending: List<ReviewEntry>): List<ReviewItem> {
        val byId = catalog.associateBy { it.id }
        return pending
            .mapNotNull { row ->
                val entry = byId[row.entryId] ?: return@mapNotNull null
                val language = VocabularyLanguage.entries.firstOrNull { it.name == row.answerLanguage }
                    ?: return@mapNotNull null
                ReviewItem(entry, language, row.lastLostAt)
            }
            .sortedWith(
                compareByDescending<ReviewItem> { it.lastLostAt }
                    .thenBy { it.entry.id }
                    .thenBy { it.answerLanguage.name },
            )
    }
}
