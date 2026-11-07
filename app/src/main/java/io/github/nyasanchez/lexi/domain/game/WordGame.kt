package io.github.nyasanchez.lexi.domain.game

import io.github.nyasanchez.lexi.data.vocabulary.VocabularyExercise
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLevel

/** Una instancia por palabra y modo. No guarda progreso ni modifica la racha. */
class WordGame(private val exercise: VocabularyExercise) {
    private val target: String
    private var history: List<EvaluatedGuess> = emptyList()
    private var status: GameStatus = GameStatus.IN_PROGRESS

    init {
        val composed = GuessInput.composeAndTrim(exercise.answer)
        require(GuessInput.hasValidLetters(composed, exercise.answerLanguage)) {
            "La solución debe contener letras válidas para el idioma de respuesta."
        }
        require(exercise.clueLanguage != exercise.answerLanguage) {
            "La definición y la respuesta deben usar idiomas distintos."
        }
        target = GuessInput.uppercase(composed)
    }

    val state: GameState
        get() = GameState(
            entryId = exercise.entryId,
            clueLanguage = exercise.clueLanguage,
            answerLanguage = exercise.answerLanguage,
            definition = exercise.definition,
            wordLength = target.length,
            guesses = history.map { it.copy(letters = it.letters.toList()) },
            status = status,
            solution = exercise.takeIf { status != GameStatus.IN_PROGRESS },
        )

    fun submit(rawInput: String): SubmissionResult {
        if (status != GameStatus.IN_PROGRESS) return reject(GuessError.GAME_FINISHED)

        val composed = GuessInput.composeAndTrim(rawInput)
        if (composed.isEmpty()) return reject(GuessError.EMPTY)
        if (!GuessInput.hasValidLetters(composed, exercise.answerLanguage)) {
            return reject(GuessError.INVALID_CHARACTERS)
        }
        val normalized = GuessInput.uppercase(composed)
        if (normalized.length != target.length) return reject(GuessError.WRONG_LENGTH)

        val guess = EvaluatedGuess(normalized, LetterEvaluator.evaluate(target, normalized))
        history = history + guess
        status = when {
            normalized == target -> GameStatus.WON
            history.size == MAX_ATTEMPTS -> GameStatus.LOST
            else -> GameStatus.IN_PROGRESS
        }
        val updated = state
        return SubmissionResult.Accepted(guess = updated.guesses.last(), state = updated)
    }

    private fun reject(error: GuessError): SubmissionResult.Rejected =
        SubmissionResult.Rejected(error, state)

    companion object {
        const val MAX_ATTEMPTS = 6

        /** Reconstruye el estado a partir de hasta seis envíos aceptados, sin registrar progreso. */
        fun restore(exercise: VocabularyExercise, acceptedGuesses: List<String>): WordGame {
            require(acceptedGuesses.size <= MAX_ATTEMPTS)
            return WordGame(exercise).also { game ->
                acceptedGuesses.forEach { guess ->
                    require(game.submit(guess) is SubmissionResult.Accepted) {
                        "El historial contiene un envío inválido o posterior al final de la partida."
                    }
                }
            }
        }
    }
}
