package games

import games.hangman.Hangman
import org.scalatest.{Matchers, WordSpecLike}

import scala.collection.mutable.ArrayBuffer

class HangmanSpec extends WordSpecLike with Matchers {

  def setupGame(): Unit = {
    Hangman.newGame()
    Hangman.guessWord = "expert"
    Hangman.remainingChars = "expert"
    Hangman.currentStatus = ArrayBuffer('_', '_', '_', '_', '_', '_')
  }

  "Calling the newGame() function" should {

    Hangman.newGame()

    "set up the game's variables to their initial state" in {
      Hangman.guessWord.length > 0 shouldBe true
      Hangman.remainingChars shouldBe Hangman.guessWord
      Hangman.currentStatus.distinct shouldBe ArrayBuffer('_')
      Hangman.prevGuesses shouldBe ArrayBuffer()
      Hangman.turnCounter shouldBe 0
      Hangman.failCounter shouldBe 0
      Hangman.messageOutput shouldBe ArrayBuffer("Please enter a letter to start the game.")
      Hangman.hangingOutput shouldBe ArrayBuffer()
      Hangman.gameStarted shouldBe true
      Hangman.gameFinished shouldBe false
    }
  }

  "Calling the makeMove() function" when {

    "a letter is guessed correctly" should {

      "progress the game as expected" in {
        setupGame()
        Hangman.makeMove('x')

        Hangman.remainingChars shouldBe "e pert"
        Hangman.prevGuesses shouldBe ArrayBuffer('x')
        Hangman.turnCounter shouldBe 1
        Hangman.failCounter shouldBe 0
        Hangman.messageOutput shouldBe ArrayBuffer(
          "Turn number: 1", "Remaining incorrect guesses: 7", "Previous guesses: x", "Correct guess.", "_ x _ _ _ _"
        )
        Hangman.hangingOutput shouldBe ArrayBuffer()
        Hangman.gameFinished shouldBe false
      }
    }

    "a letter is guessed incorrectly" should {

      "progress the game as expected" in {
        setupGame()
        Hangman.makeMove('z')

        Hangman.remainingChars shouldBe "expert"
        Hangman.prevGuesses shouldBe ArrayBuffer('z')
        Hangman.turnCounter shouldBe 1
        Hangman.failCounter shouldBe 1
        Hangman.messageOutput shouldBe ArrayBuffer(
          "Turn number: 1", "Remaining incorrect guesses: 7", "Previous guesses: z", "Incorrect guess.", "_ _ _ _ _ _"
        )
        Hangman.hangingOutput shouldBe ArrayBuffer("  _______ ")
        Hangman.gameFinished shouldBe false
      }
    }

    "the game is won" should {

      "end the game as expected" in {
        setupGame()
        Hangman.makeMove('e')
        Hangman.makeMove('x')
        Hangman.makeMove('p')
        Hangman.makeMove('r')
        Hangman.makeMove('t')

        Hangman.remainingChars shouldBe "      "
        Hangman.prevGuesses shouldBe ArrayBuffer('e', 'x', 'p', 'r', 't')
        Hangman.turnCounter shouldBe 5
        Hangman.failCounter shouldBe 0
        Hangman.messageOutput shouldBe ArrayBuffer(
          "You win! Congratulations!", "The word was: expert", "Enter a letter to start a new game."
        )
        Hangman.hangingOutput shouldBe ArrayBuffer()
        Hangman.gameFinished shouldBe true
      }
    }

    "the game is lost" should {

      "end the game as expected" in {
        setupGame()
        for(_ <- 1 to 8) Hangman.makeMove('z')

        Hangman.remainingChars shouldBe "expert"
        Hangman.prevGuesses shouldBe ArrayBuffer('z', 'z', 'z', 'z', 'z', 'z', 'z', 'z')
        Hangman.turnCounter shouldBe 8
        Hangman.failCounter shouldBe 8
        Hangman.messageOutput shouldBe ArrayBuffer(
          "Game over! YOU DIED!", "The word was: expert", "Enter a letter to start a new game."
        )
        Hangman.hangingOutput shouldBe ArrayBuffer(
          "  _______ ",
          "  |     | ",
          "  |     O ",
          "  |    /|\\",
          "  |    / \\",
          "  |       ",
          "__|___    ",
          "|    |    "
        )
        Hangman.gameFinished shouldBe true
      }
    }

    "a game is not being played" should {

      "start a new game and make a move" in {
        setupGame()
        Hangman.gameFinished = true
        Hangman.makeMove('x')

        Hangman.turnCounter shouldBe 1
        Hangman.gameFinished shouldBe false
      }
    }
  }
}
