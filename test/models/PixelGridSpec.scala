package models

import common.Grids
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import play.twirl.api.Html

class PixelGridSpec extends AnyWordSpecLike with Matchers {

  "A PixelGrid" should {

    "populate its HTML correctly" in {
      val oneColourRepeated = Seq.fill(258)("#FFFFFF")
      val exampleGrid = Grids.coordinates.zip(oneColourRepeated)

      val row: String =
      """
        |<tr>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |  <th bgcolor="#FFFFFF"></th>
        |</tr>""".stripMargin

      val table: String = row * 16

      val expectedHtml: Html = Html(
        s"""<table class="fixed" align="center">$table</table>""".stripMargin
      )

      val pixelGrid = PixelGrid("Example", Map(exampleGrid: _*))
      pixelGrid.gridHtml shouldBe expectedHtml
    }
  }
}
