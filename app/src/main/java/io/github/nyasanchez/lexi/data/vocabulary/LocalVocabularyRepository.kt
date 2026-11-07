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

    /** Palabras de un nivel. Sin indicar nivel devuelve el Básico, el que usa hoy la práctica. */
    fun all(level: VocabularyLevel = VocabularyLevel.BASIC): List<VocabularyEntry> =
        entriesById.values.filter { it.level == level }

    /** El catálogo completo, de todos los niveles. */
    fun allLevels(): List<VocabularyEntry> = entriesById.values.toList()

    fun findById(id: String): VocabularyEntry? = entriesById[id]

    fun exercises(
        answerLanguage: VocabularyLanguage,
        level: VocabularyLevel = VocabularyLevel.BASIC,
    ): List<VocabularyExercise> = all(level).map { it.exerciseFor(answerLanguage) }
}
