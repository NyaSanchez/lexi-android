package io.github.nyasanchez.lexi

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.ExternalResource

/** Aísla las sesiones recordadas entre pruebas y las restaura cuando la actividad ya cerró. */
class PracticeSessionRule : ExternalResource() {
    private val preferences get() = InstrumentationRegistry.getInstrumentation().targetContext
        .getSharedPreferences("lexi-practice-session", Context.MODE_PRIVATE)
    private var original: Map<String, *> = emptyMap<String, Any>()

    override fun before() {
        original = preferences.all.toMap()
        check(preferences.edit().clear().commit())
    }

    override fun after() {
        val edit = preferences.edit().clear()
        original.forEach { (key, value) ->
            when (value) {
                is String -> edit.putString(key, value)
                is Long -> edit.putLong(key, value)
                else -> error("Tipo de preferencia inesperado: $key")
            }
        }
        check(edit.commit())
    }
}
