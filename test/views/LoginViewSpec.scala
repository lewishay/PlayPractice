package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class LoginViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
    val usernameLabel = "#username_field > dt:nth-child(1) > label:nth-child(1)"
    val passwordLabel = "#password_field > dt:nth-child(1) > label:nth-child(1)"
    val formButton = ".big-form > button"
  }

  "The Login page" should {

    lazy val view = views.html.login()
    lazy implicit val document: Document = Jsoup.parse(view.body)

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Login"
    }

    "have the correct username form label" in {
      elementText(Selectors.usernameLabel) shouldBe "Username"
    }

    "have the correct password form label" in {
      elementText(Selectors.passwordLabel) shouldBe "Password"
    }

    "have the correct button text" in {
      elementText(Selectors.formButton) shouldBe "Submit"
    }
  }
}
