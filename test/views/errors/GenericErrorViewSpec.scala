package views.errors

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.ViewBaseSpec
import views.html.errors.GenericErrorView

class GenericErrorViewSpec extends ViewBaseSpec {

  val injectedView: GenericErrorView = injector.instanceOf[GenericErrorView]

  object Selectors {
    val title = "h1"
    val subheading = "h2"
    val paragraph1 = "p:nth-of-type(1)"
    val paragraph2 = "p:nth-of-type(2)"
  }

  lazy implicit val document: Document = Jsoup.parse(injectedView().body)

  "The Not Found page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Error"
    }

    "have the correct subheading text" in {
      elementText(Selectors.subheading) shouldBe "Something went wrong"
    }

    "have the correct first paragraph text" in {
      elementText(Selectors.paragraph1) shouldBe "There seems to have been a problem with your last request."
    }

    "have the correct second paragraph text" in {
      elementText(Selectors.paragraph2) shouldBe "It might be worth trying again later, or you can just forget it."
    }
  }
}
