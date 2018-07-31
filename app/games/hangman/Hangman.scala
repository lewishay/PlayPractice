package games.hangman

import models.viewModels.HangmanViewModel

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

  var currentGame: HangmanGameState = newGame

  def generateWord: String = wordList(rand.nextInt(wordList.length))

  def newGame: HangmanGameState = {
    val newWord = Hangman.generateWord
    val newWordVector = Vector.fill(newWord.length)('_')
    HangmanGameState(
      newWord,
      0,
      8,
      Vector[Char](),
      newWordVector,
      Vector[String]()
    )
  }

  def makeMove(gameState: HangmanGameState, guess: Char): HangmanViewModel = {

    val failCheck = if(gameState.guessWord.contains(guess) && !gameState.previousGuesses.contains(guess)) 0 else 1
    val previousGuesses = gameState.previousGuesses :+ guess
    val remainingGuesses = gameState.remainingGuesses - failCheck

    val currentWordStatus = if(failCheck == 0) {
      gameState.guessWord.map { char =>
        if(!previousGuesses.contains(char)) '_' else char
      }.toVector
    } else {
      gameState.currentWordStatus
    }

    HangmanViewModel(
      HangmanGameState(
        gameState.guessWord,
        gameState.turnNumber + 1,
        remainingGuesses,
        previousGuesses,
        currentWordStatus,
        hangmanImage.dropRight(remainingGuesses)
      ),
      gameWinCheck(remainingGuesses, gameState.guessWord, currentWordStatus.mkString)
    )
  }

  def gameWinCheck(remainingGuesses: Int, guessWord: String, currentWordStatus: String): Option[Boolean] = {
    val noGuesses = remainingGuesses == 0
    val foundWord = guessWord == currentWordStatus
    (noGuesses, foundWord) match {
      case (true, _) => Some(false)
      case (false, true) => Some(true)
      case (false, false) => None
    }
  }

  def gameEnd(victory: Boolean): String = {
    if(victory) "You win! Congratulations!" else "Game over! YOU DIED!"
  }
}
