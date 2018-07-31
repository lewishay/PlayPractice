package games.hangman

case class HangmanGameState(guessWord: String,
                            turnNumber: Int,
                            remainingGuesses: Int,
                            previousGuesses: Vector[Char],
                            currentWordStatus: Vector[Char],
                            hangingOutput: Vector[String])
