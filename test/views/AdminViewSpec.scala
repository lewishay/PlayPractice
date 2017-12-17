package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class AdminViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
    val subheading = "h2"
  }

  lazy val view = views.html.admin()
  lazy implicit val document: Document = Jsoup.parse(view.body)

  "The Admin page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Admin"
    }

    "have the correct subheading" in {
      elementText(Selectors.subheading) shouldBe "Configure settings"
    }
  }
}
