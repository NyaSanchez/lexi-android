package io.github.nyasanchez.lexi.data.vocabulary

/** Catálogo incluido en la app: no necesita Internet, cuenta ni base de datos. */
class LocalVocabularyRepository(catalog: List<VocabularyEntry> = InitialVocabulary.entries) {
    private val entriesById: Map<String, VocabularyEntry>

    init {
        require(catalog.map { it.id }.distinct().size == catalog.size) {
            "El banco de vocabulario contiene IDs duplicados."
        }
        entriesById = catalog.associateBy { it.id }
    }

    fun all(level: VocabularyLevel = VocabularyLevel.INITIAL): List<VocabularyEntry> =
        entriesById.values.filter { it.level == level }

    fun findById(id: String): VocabularyEntry? = entriesById[id]

    fun exercises(
        answerLanguage: VocabularyLanguage,
        level: VocabularyLevel = VocabularyLevel.INITIAL,
    ): List<VocabularyExercise> = all(level).map { it.exerciseFor(answerLanguage) }
}
