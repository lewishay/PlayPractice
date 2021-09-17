package views.admin

import forms.LoginFormImpl
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.ViewBaseSpec
import views.html.admin.LoginView

class LoginViewSpec extends ViewBaseSpec {

  val injectedView: LoginView = injector.instanceOf[LoginView]

  object Selectors {
    val title = "h1"
    val usernameLabel = "#username_field > dt:nth-child(1) > label:nth-child(1)"
    val passwordLabel = "#password_field > dt:nth-child(1) > label:nth-child(1)"
    val formButton = ".big-form > button"
    val usernameError = "#username_field > dd:nth-child(3)"
    val passwordError = "#password_field > dd:nth-child(3)"
  }

  "The Login page" when {

    val loginForm = new LoginFormImpl(mockAppConfig)

    "there are no errors in the form" should {

      lazy val view = injectedView(loginForm.form)
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

    "there are errors in the form" should {
      lazy val view = injectedView(loginForm.form.bind(Map(
        "username" -> "no",
        "password" -> "no"
      )))
      lazy implicit val document: Document = Jsoup.parse(view.body)

      "contain the correct username form validation message" in {
        elementText(Selectors.usernameError) shouldBe "Invalid username"
      }

      "contain the correct password form validation message" in {
        elementText(Selectors.passwordError) shouldBe "Invalid password"
      }
    }
  }
}
