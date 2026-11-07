package io.github.nyasanchez.lexi.data.progress

import android.content.Context
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLevel
import io.github.nyasanchez.lexi.domain.game.WordGame

/**
 * Recuerda dónde se quedó la persona en cada modo y nivel. Los resultados terminados siguen en Room.
 * Cada combinación de modo y nivel guarda su propia palabra.
 */
class PracticeSessionStore(context: Context) {
    companion object {
        /**
         * Clave de guardado de un modo y un nivel. El nivel Básico conserva la clave que ya se usaba
         * antes de que existieran los niveles, para no perder el avance de quien ya estaba practicando.
         */
        fun key(modeId: String, level: VocabularyLevel): String =
            if (level == VocabularyLevel.BASIC) modeId else "$modeId.${level.name.lowercase()}"
    }

    private val prefs = context.applicationContext
        .getSharedPreferences("lexi-practice-session", Context.MODE_PRIVATE)

    data class Session(
        val entryId: String,
        val attempts: List<String>,
        val sessionId: String,
        val completedAt: Long,
        val zoneId: String,
    )

    fun load(modeId: String): Session? = try {
        val entryId = prefs.getString("$modeId.entry", null)
        val sessionId = prefs.getString("$modeId.session", null)
        val attempts = prefs.getString("$modeId.attempts", "").orEmpty()
            .split(",").filter { it.isNotBlank() }
        if (entryId == null || sessionId == null || attempts.size > WordGame.MAX_ATTEMPTS) null
        else Session(
            entryId, attempts, sessionId,
            prefs.getLong("$modeId.completedAt", 0L),
            prefs.getString("$modeId.zone", "").orEmpty(),
        )
    } catch (_: Exception) {
        null // datos corruptos: se empieza desde la primera palabra
    }

    fun save(modeId: String, session: Session) {
        prefs.edit()
            .putString("$modeId.entry", session.entryId)
            .putString("$modeId.session", session.sessionId)
            .putString("$modeId.attempts", session.attempts.joinToString(","))
            .putLong("$modeId.completedAt", session.completedAt)
            .putString("$modeId.zone", session.zoneId)
            .apply()
    }
}