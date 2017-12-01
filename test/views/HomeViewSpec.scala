package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HomeViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
  }

  lazy val view = views.html.home()
  lazy implicit val document: Document = Jsoup.parse(view.body)

  "The Home page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Home"
    }
  }
}
