package games.hangman

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Random

object Hangman {

  val hangmanImage = Vector(
    "  _______ ",
    "  |     | ",
    "  |     O ",
    "  |    /|\\",
    "  |    / \\",
    "  |       ",
    "__|___    ",
    "|    |    "
  )
  val wordList: Vector[String] = Source.fromFile("app/games/hangman/wordlist.txt").getLines().toVector
  val rand = new Random()

  def generateWord: String = wordList(rand.nextInt(wordList.length))

  def newGame: HangmanGameState = {
    val newWord = Hangman.generateWord
    val newWordVector = newWord.toVector
    HangmanGameState(
      newWord,
      1,
      7,
      Vector[Char](),
      newWordVector,
      newWordVector,
      Vector[String]()
    )
  }

  def makeMove(gameState: HangmanGameState, guess: Char): HangmanGameState = {
    val remainingGuesses = gameState.remainingGuesses - (if(true) 1 else 0)
    val currentWordStatus = Vector[Char]('j', 'k', 'l')
    val remainingChars = Vector[Char]('o', 'p', 'q')
    HangmanGameState(
      gameState.guessWord,
      gameState.turnNumber + 1,
      remainingGuesses,
      gameState.previousGuesses :+ guess,
      currentWordStatus,
      remainingChars,
      hangmanImage.take(7 - remainingGuesses)
    )
  }

  def gameEnd(victory: Boolean, answer: String): ArrayBuffer[String] = {
    val message = if(victory) "You win! Congratulations!" else "Game over! YOU DIED!"
    ArrayBuffer(message, s"The word was: $answer", "Enter a letter to start a new game.")
  }
}
