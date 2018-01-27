package views

import common.Common
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class BossViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
    val formLabel = ".big-form > dl > dt > label"
    val formButton = ".big-form > button"
    val bossName = "p:nth-child(1) > b:nth-child(1)"
    val bossDescription = "p:nth-child(2) > b:nth-child(1)"
    val bossHealth = "p:nth-child(3) > b:nth-child(1)"
    val bossLevel = "p:nth-child(4) > b:nth-child(1)"
    val zoneName = "p:nth-child(5) > b:nth-child(1)"
    val zoneLocation = "p:nth-child(6) > b:nth-child(1)"
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

    "have the correct boss field headings" in {
      val bossFieldSelectors = Array(Selectors.bossName, Selectors.bossDescription, Selectors.bossHealth,
        Selectors.bossLevel, Selectors.zoneName, Selectors.zoneLocation)

      val expectedValues = Array("Name:", "Description:", "Health:", "Level:", "Zone name:", "Zone location:")

      for((fieldValue, expectedValue) <- bossFieldSelectors.zip(expectedValues)) {
        elementText(fieldValue) shouldBe expectedValue
      }
    }
  }
}
