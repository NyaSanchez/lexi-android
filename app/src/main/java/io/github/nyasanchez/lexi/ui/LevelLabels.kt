package io.github.nyasanchez.lexi.ui

import androidx.annotation.StringRes
import io.github.nyasanchez.lexi.R
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLevel

/** Nombre del nivel tal como se muestra en pantalla. */
@get:StringRes
internal val VocabularyLevel.labelRes: Int
    get() = when (this) {
        VocabularyLevel.BASIC -> R.string.level_basic
        VocabularyLevel.INTERMEDIATE -> R.string.level_intermediate
        VocabularyLevel.ADVANCED -> R.string.level_advanced
    }

/** Subniveles CEFR que agrupa el nivel, por ejemplo «A1 y A2». */
@get:StringRes
internal val VocabularyLevel.cefrRangeRes: Int
    get() = when (this) {
        VocabularyLevel.BASIC -> R.string.level_cefr_range_basic
        VocabularyLevel.INTERMEDIATE -> R.string.level_cefr_range_intermediate
        VocabularyLevel.ADVANCED -> R.string.level_cefr_range_advanced
    }
