package io.github.nyasanchez.lexi.data.vocabulary

/** Catálogo completo: los tres niveles juntos, en orden de dificultad. */
internal object InitialVocabulary {
    val entries: List<VocabularyEntry> =
        group(BasicVocabulary.entries, CefrLevel.A1, CefrLevel.A2) +
            group(IntermediateVocabulary.entries, CefrLevel.B1, CefrLevel.B2) +
            group(AdvancedVocabulary.entries, CefrLevel.C1, CefrLevel.C2)

    /** Etiqueta cada palabra con su subnivel y deja primero las del subnivel más sencillo. */
    private fun group(
        entries: List<VocabularyEntry>,
        first: CefrLevel,
        second: CefrLevel,
    ): List<VocabularyEntry> = entries
        .map { entry ->
            entry.copy(
                level = first.level,
                cefr = if (entry.id in CefrAssignment.firstSublevelIds) first else second,
            )
        }
        .sortedBy { it.cefr }
}
