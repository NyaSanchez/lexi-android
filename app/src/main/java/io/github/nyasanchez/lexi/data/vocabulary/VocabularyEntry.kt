package io.github.nyasanchez.lexi.data.vocabulary

enum class VocabularyLanguage {
    SPANISH,
    ENGLISH,
}

enum class VocabularyLevel {
    INITIAL,
}

/** Contenido editorial de una palabra en un idioma; no representa un intento del usuario. */
data class VocabularyText(
    val word: String,
    val definition: String,
    val example: String,
) {
    init {
        require(word.isNotBlank() && word == word.trim()) { "La palabra debe tener contenido y no llevar espacios exteriores." }
        require(definition.isNotBlank()) { "La definición no puede estar vacía." }
        require(example.isNotBlank()) { "La frase de ejemplo no puede estar vacía." }
    }
}

/** Un concepto con sus dos traducciones. El ID permanece estable al corregir los textos. */
data class VocabularyEntry(
    val id: String,
    val spanish: VocabularyText,
    val english: VocabularyText,
    val level: VocabularyLevel = VocabularyLevel.INITIAL,
) {
    init {
        require(id.matches(Regex("[a-z][a-z0-9_-]*"))) { "El ID debe ser estable, legible y sin espacios." }
        require(spanish.word.matches(Regex("[a-záéíóúüñ]+"))) {
            "La palabra española debe ser una sola palabra en minúsculas, con sus tildes y ñ."
        }
        require(english.word.matches(Regex("[a-z]+"))) {
            "La palabra inglesa debe ser una sola palabra en minúsculas."
        }
    }

    fun exerciseFor(answerLanguage: VocabularyLanguage): VocabularyExercise {
        val answer = if (answerLanguage == VocabularyLanguage.ENGLISH) english else spanish
        val clue = if (answerLanguage == VocabularyLanguage.ENGLISH) spanish else english
        val clueLanguage = if (answerLanguage == VocabularyLanguage.ENGLISH) {
            VocabularyLanguage.SPANISH
        } else {
            VocabularyLanguage.ENGLISH
        }
        return VocabularyExercise(
            entryId = id,
            level = level,
            clueLanguage = clueLanguage,
            answerLanguage = answerLanguage,
            definition = clue.definition,
            answer = answer.word,
            translation = clue.word,
            example = answer.example,
            translatedExample = clue.example,
        )
    }
}

/** La interfaz deberá reservar answer, translation y los ejemplos para el resultado. */
data class VocabularyExercise(
    val entryId: String,
    val level: VocabularyLevel,
    val clueLanguage: VocabularyLanguage,
    val answerLanguage: VocabularyLanguage,
    val definition: String,
    val answer: String,
    val translation: String,
    val example: String,
    val translatedExample: String,
) {
    val letterCount: Int
        get() = answer.codePointCount(0, answer.length)
}
