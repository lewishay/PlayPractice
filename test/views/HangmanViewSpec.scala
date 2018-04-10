package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HangmanViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
    val formLabel = "#guess_field > dt > label"
    val formButton = ".big-form > button"
    val startGameInstructions = "body > section > div > div > div.col-lg-6.col-xl-6 > p"
  }

  lazy val view = views.html.hangman()
  lazy implicit val document: Document = Jsoup.parse(view.body)

  "The Hangman page" should {

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
      elementText(Selectors.startGameInstructions) shouldBe "Please enter a letter to start the game."
    }
  }
}
