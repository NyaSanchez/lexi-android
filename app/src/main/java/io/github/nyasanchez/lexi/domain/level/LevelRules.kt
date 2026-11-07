package io.github.nyasanchez.lexi.domain.level

import io.github.nyasanchez.lexi.data.vocabulary.CefrLevel
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyEntry
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLevel

/** Avance dentro de un subnivel CEFR: cuántas de sus palabras ya se acertaron. */
data class SublevelProgress(val cefr: CefrLevel, val total: Int, val won: Int)

/** Estado de un nivel para una persona: palabras acertadas, meta para avanzar y si está disponible. */
data class LevelStatus(
    val level: VocabularyLevel,
    val total: Int,
    val won: Int,
    val required: Int,
    val unlocked: Boolean,
    val sublevels: List<SublevelProgress>,
) {
    /** Se alcanzó la meta para desbloquear el siguiente nivel (sin obligar a terminar este). */
    val goalReached: Boolean get() = total > 0 && won >= required

    /** Se acertaron todas las palabras del nivel. */
    val completed: Boolean get() = total > 0 && won >= total

    /** Aciertos que faltan para alcanzar la meta. */
    val remaining: Int get() = (required - won).coerceAtLeast(0)
}

/** Reglas para avanzar de nivel. No toca la base de datos ni la interfaz. */
object LevelRules {
    /** Porcentaje de palabras distintas que hay que acertar para desbloquear el siguiente nivel. */
    const val UNLOCK_PERCENT = 70

    /** Palabras distintas que hay que acertar en un nivel de este tamaño (redondeo hacia arriba). */
    fun requiredToAdvance(total: Int): Int = (total * UNLOCK_PERCENT + 99) / 100

    /**
     * Estado de cada nivel a partir de las palabras acertadas. El primer nivel siempre está
     * disponible; cada uno de los siguientes se abre cuando se alcanza la meta del anterior.
     * Recibe solo las palabras acertadas en un modo: cada modo (idioma de respuesta) lleva su propio avance.
     */
    fun statuses(catalog: List<VocabularyEntry>, wonIds: Set<String>): List<LevelStatus> {
        var previousReached = true
        return VocabularyLevel.entries.map { level ->
            val entries = catalog.filter { it.level == level }
            val sublevels = level.sublevels.map { cefr ->
                val inSublevel = entries.filter { it.cefr == cefr }
                SublevelProgress(cefr, inSublevel.size, inSublevel.count { it.id in wonIds })
            }
            val status = LevelStatus(
                level = level,
                total = entries.size,
                won = entries.count { it.id in wonIds },
                required = requiredToAdvance(entries.size),
                unlocked = previousReached,
                sublevels = sublevels,
            )
            previousReached = previousReached && status.goalReached
            status
        }
    }
}
