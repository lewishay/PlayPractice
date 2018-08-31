package views.admin

import forms.FeatureSwitchForm
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.ViewBaseSpec

class FeatureSwitchViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
    val infoBannerFeature = ".big-form > div:nth-child(1)"
    val input = " > input"
    val label = " > label"
    val submitButton = ".big-form > button"
  }

  lazy val view = views.html.admin.featureSwitch(FeatureSwitchForm.form)
  lazy implicit val document: Document = Jsoup.parse(view.body)

  "The Feature Switch page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Features"
    }

    "have a checkbox input for the info banner feature" in {
      val checkbox = element(Selectors.infoBannerFeature + Selectors.input)
      noException shouldBe thrownBy(checkbox)
    }

    "have a label for the info banner feature" in {
      elementText(Selectors.infoBannerFeature + Selectors.label) shouldBe "Info banner enabled"
    }

    "have a submit button" in {
      elementText(Selectors.submitButton) shouldBe "Submit"
    }
  }
}
