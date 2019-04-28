package views

import common.{Common, Grids}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.html.PixelGridView

class PixelGridViewSpec extends ViewBaseSpec {

  val injectedView: PixelGridView = injector.instanceOf[PixelGridView]

  object Selectors {
    val title = "h1"
    val checkedGridButton = ".big-form:nth-child(2) > button:nth-child(1)"
    val scalaGridButton = ".big-form:nth-child(4) > button:nth-child(1)"
    val oneUpGridButton = ".big-form:nth-child(6) > button:nth-child(1)"
    val corruptionGridButton = ".big-form:nth-child(8) > button:nth-child(1)"
    val exampleGridPixel = ".fixed > tbody:nth-child(1) > tr:nth-child(4) > th:nth-child(8)"
  }


  "The Pixel Grid page" should {

    lazy val view = injectedView(Grids.scalaClass.name)
    lazy implicit val document: Document = Jsoup.parse(view.body)

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Pixel grid"
    }

    "have the correct text on the checked grid button" in {
      elementText(Selectors.checkedGridButton) shouldBe "Checked grid"
    }

    "have the correct text on the scala grid button" in {
      elementText(Selectors.scalaGridButton) shouldBe "Scala class"
    }

    "have the correct text on the 1-up mushroom grid button" in {
      elementText(Selectors.oneUpGridButton) shouldBe "1-up mushroom"
    }

    "have the correct text on the corruption grid button" in {
      elementText(Selectors.corruptionGridButton) shouldBe "Corruption"
    }

    "have the correct image in the grid" in {
      element(Selectors.exampleGridPixel).attr("bgcolor") shouldBe "#A3D6F2"
    }
  }
}
