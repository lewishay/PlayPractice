package views.errors

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.ViewBaseSpec

class UnauthorisedViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
    val subheading = "h2"
  }

  lazy val view = views.html.errors.unauthorised()
  lazy implicit val document: Document = Jsoup.parse(view.body)

  "The Unauthorised page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Unauthorised"
    }

    "have the correct subheading" in {
      elementText(Selectors.subheading) shouldBe "Unauthorised access"
    }
  }
}
