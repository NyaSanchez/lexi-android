package io.github.nyasanchez.lexi.ui

import androidx.annotation.StringRes
import io.github.nyasanchez.lexi.R
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage

enum class PracticeMode(
    val id: String,
    @param:StringRes val title: Int,
    @param:StringRes val subtitle: Int,
    @param:StringRes val instruction: Int,
) {
    ENGLISH("english", R.string.mode_spanish_definition, R.string.mode_answer_english, R.string.write_english),
    SPANISH("spanish", R.string.mode_english_definition, R.string.mode_answer_spanish, R.string.write_spanish);

    val answerLanguage: VocabularyLanguage
        get() = when (this) {
            ENGLISH -> VocabularyLanguage.ENGLISH
            SPANISH -> VocabularyLanguage.SPANISH
        }

    companion object {
        fun fromId(id: String?): PracticeMode = entries.firstOrNull { it.id == id } ?: ENGLISH

        /** El modo en el que se responde en ese idioma; lo usa Repaso para reabrir una palabra en su modo original. */
        fun forAnswerLanguage(language: VocabularyLanguage): PracticeMode =
            entries.first { it.answerLanguage == language }
    }
}
