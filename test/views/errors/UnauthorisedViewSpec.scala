package views.errors

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.ViewBaseSpec

class UnauthorisedViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "#unauthorised h1"
  }

  lazy val view = views.html.errors.unauthorised()
  lazy implicit val document: Document = Jsoup.parse(view.body)

  "The Home page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Unauthorised access"
    }
  }
}
