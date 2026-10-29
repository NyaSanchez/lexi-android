package io.github.nyasanchez.lexi.domain.game

enum class LetterMark {
    CORRECT,
    PRESENT,
    ABSENT,
}

data class EvaluatedLetter(val letter: Char, val mark: LetterMark)

/** Recibe palabras ya validadas y normalizadas por WordGame. */
internal object LetterEvaluator {
    fun evaluate(target: String, guess: String): List<EvaluatedLetter> {
        require(target.isNotEmpty() && target.length == guess.length) {
            "La respuesta y el intento deben tener la misma longitud, mayor que cero."
        }
        val marks = MutableList(guess.length) { LetterMark.ABSENT }
        val remaining = mutableMapOf<Char, Int>()

        // Reservar los aciertos exactos antes de repartir letras en otra posición.
        target.forEachIndexed { index, letter ->
            if (guess[index] == letter) {
                marks[index] = LetterMark.CORRECT
            } else {
                remaining[letter] = (remaining[letter] ?: 0) + 1
            }
        }
        guess.forEachIndexed { index, letter ->
            val available = remaining[letter] ?: 0
            if (marks[index] != LetterMark.CORRECT && available > 0) {
                marks[index] = LetterMark.PRESENT
                remaining[letter] = available - 1
            }
        }
        return guess.mapIndexed { index, letter -> EvaluatedLetter(letter, marks[index]) }
    }
}
