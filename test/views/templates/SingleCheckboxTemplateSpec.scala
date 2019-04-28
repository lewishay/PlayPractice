package views.templates

import forms.FeatureSwitchForm
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import play.api.data.Field
import views.ViewBaseSpec
import views.html.templates.SingleCheckbox

class SingleCheckboxTemplateSpec extends ViewBaseSpec {

  val injectedView: SingleCheckbox = injector.instanceOf[SingleCheckbox]

  object Selectors {
    val input = "input"
    val label = "label"
  }

  "Rendering the single checkbox template" when {

    "the field value is true" should {

      val field = Field(FeatureSwitchForm.form, "MyFeature", Seq(), None, Seq(), Some("true"))
      lazy val view = injectedView(field, "My cool feature")
      lazy implicit val document: Document = Jsoup.parse(view.body)

      "display a checkbox input" in {
        val checkbox = element(Selectors.input).attr("type") shouldBe "checkbox"
        noException shouldBe thrownBy(checkbox)
      }

      "have the 'checked' attribute to indicate the box is checked" in {
        val inputAttributes = element(Selectors.input).attributes()
        inputAttributes.asList().size() shouldBe 6
      }

      "display a label" in {
        elementText(Selectors.label) shouldBe "My cool feature"
      }
    }

    "field value is false" should {

      val field = Field(FeatureSwitchForm.form, "MyFeature", Seq(), None, Seq(), Some("false"))
      lazy val view = injectedView(field, "My cool feature")
      lazy implicit val document: Document = Jsoup.parse(view.body)

      "display a checkbox input" in {
        val checkbox = element(Selectors.input).attr("type") shouldBe "checkbox"
        noException shouldBe thrownBy(checkbox)
      }

      "not have the 'checked' attribute to indicate the box is not checked" in {
        val inputAttributes = element(Selectors.input).attributes()
        inputAttributes.asList().size() shouldBe 5
      }

      "display a label" in {
        elementText(Selectors.label) shouldBe "My cool feature"
      }
    }

    "field value is not supplied" should {

      val field = Field(FeatureSwitchForm.form, "MyFeature", Seq(), None, Seq(), None)
      lazy val view = injectedView(field, "My cool feature")
      lazy implicit val document: Document = Jsoup.parse(view.body)

      "display a checkbox input" in {
        val checkbox = element(Selectors.input).attr("type") shouldBe "checkbox"
        noException shouldBe thrownBy(checkbox)
      }

      "not have the 'checked' attribute to indicate the box is not checked" in {
        val inputAttributes = element(Selectors.input).attributes()
        inputAttributes.asList().size() shouldBe 5
      }

      "display a label" in {
        elementText(Selectors.label) shouldBe "My cool feature"
      }
    }
  }
}
