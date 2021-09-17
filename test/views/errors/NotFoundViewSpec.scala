package views.errors

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.ViewBaseSpec
import views.html.errors.NotFoundView

class NotFoundViewSpec extends ViewBaseSpec {

  val injectedView: NotFoundView = injector.instanceOf[NotFoundView]

  object Selectors {
    val title = "h1"
    val subheading = "h2"
    val paragraph = "p"
  }

  lazy implicit val document: Document = Jsoup.parse(injectedView().body)

  "The Not Found page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Not found"
    }

    "have the correct subheading text" in {
      elementText(Selectors.subheading) shouldBe "Page not found"
    }

    "have the correct paragraph text" in {
      elementText(Selectors.paragraph) shouldBe "The page you are looking for does not exist."
    }
  }
}
