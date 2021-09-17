package views.errors

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.ViewBaseSpec
import views.html.errors.UnauthorisedView

class UnauthorisedViewSpec extends ViewBaseSpec {

  val injectedView: UnauthorisedView = injector.instanceOf[UnauthorisedView]

  object Selectors {
    val title = "h1"
    val subheading = "h2"
    val paragraph1 = "p:nth-of-type(1)"
    val paragraph2 = "p:nth-of-type(2)"
    val paragraph3 = "p:nth-of-type(3)"
    val homepageLink = "p > a"
  }

  lazy implicit val document: Document = Jsoup.parse(injectedView().body)

  "The Unauthorised page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Unauthorised"
    }

    "have the correct subheading" in {
      elementText(Selectors.subheading) shouldBe "Unauthorised access"
    }

    "have the correct first paragraph" in {
      elementText(Selectors.paragraph1) shouldBe
        "You are not authorised to view the page you are trying to access."
    }

    "have the correct second paragraph" in {
      elementText(Selectors.paragraph2) shouldBe
        "If you were attempting to log in, please take care to check your credentials and enter them correctly."
    }

    "have the correct third paragraph" in {
      elementText(Selectors.paragraph3) shouldBe "Otherwise, please go back to the homepage."
    }

    "have a link back to the homepage" in {
      element(Selectors.homepageLink).attr("href") shouldBe controllers.routes.HomeController.home.url
    }
  }
}
