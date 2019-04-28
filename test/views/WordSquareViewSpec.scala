package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.html.WordSquareView

class WordSquareViewSpec extends ViewBaseSpec {

  val injectedView: WordSquareView = injector.instanceOf[WordSquareView]

  object Selectors {
    val title = "h1"
    val formLabel = ".big-form > dl > dt > label"
    val formButton = ".big-form > button"
  }

  "The Word Square page" should {

    lazy val view = injectedView("example")
    lazy implicit val document: Document = Jsoup.parse(view.body)

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
