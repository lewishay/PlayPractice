package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class WordSquareViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
    val formLabel = ".big-form > dl > dt > label"
    val formButton = ".big-form > button"
  }

  lazy val view = views.html.wordSquare("example")
  lazy implicit val document: Document = Jsoup.parse(view.body)

  "The Word Square page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Word square"
    }

    "have the correct form label text" in {
      elementText(Selectors.formLabel) shouldBe "Enter word:"
    }

    "have the correct button text" in {
      elementText(Selectors.formButton) shouldBe "Make square!"
    }
  }
}
