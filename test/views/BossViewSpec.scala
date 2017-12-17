package views

import common.Common
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class BossViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
    val formLabel = ".big-form > dl > dt > label"
    val formButton = ".big-form > button"
  }

  lazy val view = views.html.boss(Common.exampleBoss)
  lazy implicit val document: Document = Jsoup.parse(view.body)

  "The Boss page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Boss API call"
    }

    "have the correct form label text" in {
      elementText(Selectors.formLabel) shouldBe "Select boss:"
    }

    "have the correct button text" in {
      elementText(Selectors.formButton) shouldBe "Get boss!"
    }
  }
}
