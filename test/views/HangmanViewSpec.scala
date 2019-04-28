package views

import games.hangman.HangmanGameState
import models.viewModels.HangmanViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.html.HangmanView

class HangmanViewSpec extends ViewBaseSpec {

  val injectedView: HangmanView = injector.instanceOf[HangmanView]

  object Selectors {
    val title = "h1"
    val formLabel = "#guess_field > dt > label"
    val formButton = ".big-form > button"
    def gameInformation(row: Int): String = s"body > section > div > div > div.col-lg-6.col-xl-6 > p:nth-child($row)"
  }

  "The Hangman page" when {

    "a user has not started a game" should {

      val newGameState = HangmanGameState(
        "water",
        0,
        8,
        Vector[Char](),
        Vector.fill(5)('_'),
        Vector[String]()
      )

      lazy val view = injectedView(HangmanViewModel(newGameState, None))
      lazy implicit val document: Document = Jsoup.parse(view.body)

      "have the correct title" in {
        elementText(Selectors.title) shouldBe "Hangman"
      }

      "have the correct form label" in {
        elementText(Selectors.formLabel) shouldBe "Your guess:"
      }

      "have the correct form button text" in {
        elementText(Selectors.formButton) shouldBe "Submit"
      }

      "have the correct instructions to start the game" in {
        elementText(Selectors.gameInformation(1)) shouldBe "Please enter a letter to start the game."
      }
    }

    "a user has a game in progress" should {

      val inProgressGameState = HangmanGameState(
        "water",
        4,
        6,
        Vector('x', 'p', 'w'),
        Vector('w', '_', '_', '_', '_'),
        Vector("  _______ ", "  |     | ")
      )

      lazy val view = injectedView(HangmanViewModel(inProgressGameState, None))
      lazy implicit val document: Document = Jsoup.parse(view.body)

      "have the correct title" in {
        elementText(Selectors.title) shouldBe "Hangman"
      }

      "have the correct form label" in {
        elementText(Selectors.formLabel) shouldBe "Your guess:"
      }

      "have the correct form button text" in {
        elementText(Selectors.formButton) shouldBe "Submit"
      }

      "display the player's turn number" in {
        elementText(Selectors.gameInformation(1)) shouldBe "Turn number: 4"
      }

      "display the player's remaining guesses" in {
        elementText(Selectors.gameInformation(2)) shouldBe "Remaining guesses: 6"
      }

      "display the player's previous guesses" in {
        elementText(Selectors.gameInformation(3)) shouldBe "Previous guesses: x, p, w"
      }

      "display the current word status" in {
        elementText(Selectors.gameInformation(4)) shouldBe "Current status: w _ _ _ _"
      }
    }

    "a user has finished a game with a win" should {

      val winGameState = HangmanGameState(
        "water",
        6,
        8,
        Vector[Char]('t', 'a', 'r', 'e', 'w'),
        Vector[Char]('w', 'a', 't', 'e', 'r'),
        Vector[String]()
      )

      lazy val view = injectedView(HangmanViewModel(winGameState, Some(true)))
      lazy implicit val document: Document = Jsoup.parse(view.body)

      "have the correct title" in {
        elementText(Selectors.title) shouldBe "Hangman"
      }

      "have the correct form button text" in {
        elementText(Selectors.formButton) shouldBe "New game"
      }

      "display the victory message" in {
        elementText(Selectors.gameInformation(1)) shouldBe "You win! Congratulations!"
      }

      "display the guess word" in {
        elementText(Selectors.gameInformation(2)) shouldBe "The word was: water"
      }
    }

    "a user has finished a game with a loss" should {

      val loseGameState = HangmanGameState(
        "water",
        9,
        0,
        Vector('x', 'p', 'q', 'd', 'f', 'g', 'h', 'j'),
        Vector('_', '_', '_', '_', '_'),
        Vector(
          "  _______ ",
          "  |     | ",
          "  |     O ",
          "  |    /|\\",
          "  |    / \\",
          "  |       ",
          "__|___    ",
          "|    |    "
        )
      )

      lazy val view = injectedView(HangmanViewModel(loseGameState, Some(false)))
      lazy implicit val document: Document = Jsoup.parse(view.body)

      "have the correct title" in {
        elementText(Selectors.title) shouldBe "Hangman"
      }

      "have the correct form button text" in {
        elementText(Selectors.formButton) shouldBe "New game"
      }

      "display the failure message" in {
        elementText(Selectors.gameInformation(1)) shouldBe "Game over! YOU DIED!"
      }

      "display the guess word" in {
        elementText(Selectors.gameInformation(2)) shouldBe "The word was: water"
      }
    }
  }
}
