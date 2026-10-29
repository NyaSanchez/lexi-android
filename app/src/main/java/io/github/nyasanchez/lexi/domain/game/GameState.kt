package io.github.nyasanchez.lexi.domain.game

import io.github.nyasanchez.lexi.data.vocabulary.VocabularyExercise
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLanguage

enum class GameStatus {
    IN_PROGRESS,
    WON,
    LOST,
}

enum class GuessError {
    EMPTY,
    INVALID_CHARACTERS,
    WRONG_LENGTH,
    GAME_FINISHED,
}

data class EvaluatedGuess(
    val word: String,
    val letters: List<EvaluatedLetter>,
)

/** Una instantánea de la partida. La solución solo está disponible al terminar. */
data class GameState(
    val entryId: String,
    val clueLanguage: VocabularyLanguage,
    val answerLanguage: VocabularyLanguage,
    val definition: String,
    val wordLength: Int,
    val guesses: List<EvaluatedGuess>,
    val status: GameStatus,
    val solution: VocabularyExercise?,
) {
    val attemptsUsed: Int
        get() = guesses.size

    val attemptsRemaining: Int
        get() = WordGame.MAX_ATTEMPTS - attemptsUsed
}

sealed interface SubmissionResult {
    val state: GameState

    data class Accepted(
        val guess: EvaluatedGuess,
        override val state: GameState,
    ) : SubmissionResult

    data class Rejected(
        val error: GuessError,
        override val state: GameState,
    ) : SubmissionResult
}
