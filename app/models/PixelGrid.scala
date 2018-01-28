package models

import play.twirl.api.Html

case class PixelGrid(name: String, grid: Map[(Int, Int), String]) {

  val gridHtml: Html = Html(
    s"""<table class="fixed" align="center">$generateTable</table>""".stripMargin
  )

  private def generateTableRow(rowNumber: Int): String = {
    s"""
      |<tr>
      |  <th bgcolor="${grid.get(rowNumber, 0).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 1).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 2).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 3).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 4).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 5).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 6).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 7).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 8).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 9).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 10).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 11).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 12).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 13).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 14).get}"></th>
      |  <th bgcolor="${grid.get(rowNumber, 15).get}"></th>
      |</tr>""".stripMargin
  }

  private def generateTable: String = {
    def recFunc(n: Int): String = n match {
      case 16 => ""
      case _ => generateTableRow(n) + recFunc(n + 1)
    }
    recFunc(0)
  }
}