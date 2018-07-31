package games

import games.hangman.{Hangman, HangmanGameState}
import org.scalatest.{Matchers, WordSpecLike}

class HangmanSpec extends WordSpecLike with Matchers {

  "Calling the generateWord function" should {

    "generate a random word of length greater than 0" in {
      assert(Hangman.generateWord.length > 0)
    }
  }

  "Calling the newGame function" should {

    val result = Hangman.newGame

    "initialise a new hangman game" in {
      result.turnNumber shouldBe 0
      result.remainingGuesses shouldBe 8
      result.previousGuesses shouldBe Vector[Char]()
      result.hangingOutput shouldBe Vector[String]()
    }
  }

  "Calling the makeMove function" when {

    val gameState = HangmanGameState(
      guessWord = "water",
      turnNumber = 3,
      remainingGuesses = 7,
      previousGuesses = Vector('x', 'w'),
      currentWordStatus = Vector('w', '_', '_', '_', '_'),
      hangingOutput = Vector("  _______ ")
    )

    "the player has guessed a letter correctly" should {

      val result = Hangman.makeMove(gameState, 't')

      "return the expected game state" in {
        result.gameState.turnNumber shouldBe gameState.turnNumber + 1
        result.gameState.remainingGuesses shouldBe gameState.remainingGuesses
        result.gameState.previousGuesses shouldBe Vector('x', 'w', 't')
        result.gameState.currentWordStatus shouldBe Vector('w', '_', 't', '_', '_')
        result.gameState.hangingOutput shouldBe gameState.hangingOutput
        result.gameWin shouldBe None
      }
    }

    "the player has guessed a letter incorrectly" should {

      val result = Hangman.makeMove(gameState, 'k')
      val newHangingOutput = Vector(
        "  _______ ",
        "  |     | "
      )

      "return the expected game state" in {
        result.gameState.turnNumber shouldBe gameState.turnNumber + 1
        result.gameState.remainingGuesses shouldBe gameState.remainingGuesses - 1
        result.gameState.previousGuesses shouldBe Vector('x', 'w', 'k')
        result.gameState.currentWordStatus shouldBe Vector('w', '_', '_', '_', '_')
        result.gameState.hangingOutput shouldBe newHangingOutput
        result.gameWin shouldBe None
      }
    }
  }

  "Calling the gameWinCheck function" when {

    "the player has no remaining guesses" should {

      "return Some(false)" in {
        Hangman.gameWinCheck(0, "", "") shouldBe Some(false)
      }
    }

    "the player has remaining guesses and has guessed the correct word" should {

      "return Some(true)" in {
        Hangman.gameWinCheck(1, "water", "water") shouldBe Some(true)
      }
    }

    "the player has remaining guesses but has not guessed the correct word" should {

      "return None" in {
        Hangman.gameWinCheck(1, "water", "fire") shouldBe None
      }
    }
  }

  "Calling the gameEnd function" when {

    "the player has won" should {

      "return the victory message" in {
        Hangman.gameEnd(true) shouldBe "You win! Congratulations!"
      }
    }

    "the player has lost" should {

      "return the failure message" in {
        Hangman.gameEnd(false) shouldBe "Game over! YOU DIED!"
      }
    }
  }
}
