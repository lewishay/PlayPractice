package common

import models.PixelGrid

object Grids {

  val coordinates: IndexedSeq[(Int, Int)] = {
    (0 to 15).flatMap(
      x => (0 to 15).map(
        y => (x, y)
      )
    )
  }

  private def mapGenerator(name: String, colours: Seq[String]): PixelGrid = {
    val result = coordinates.zip(colours)
    PixelGrid(name, Map(result: _*))
  }

  def checkedGrid: PixelGrid = {
    val row: Seq[String] = Seq.fill(8)("#fcfbef", "#141200").flatMap {
      case (a, b) => Seq(a, b)
    }
    val twoRows: Seq[String] = row ++ row.reverse
    mapGenerator("Checked grid", Seq.fill(256)(twoRows).flatten)
  }

  def scalaClass: PixelGrid = {
    val blankRow = Seq("#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff")
    val rowOne = Seq("#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#86BEE3", "#7AB9DF", "#7AB9DF", "#86BEE3", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff")
    val rowTwo = Seq("#ffffff", "#ffffff", "#ffffff", "#ffffff", "#7AB9DF", "#86BEE3", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#86BEE3", "#7AB9DF", "#ffffff", "#ffffff", "#ffffff", "#ffffff")
    val rowThree = Seq("#ffffff", "#ffffff", "#ffffff", "#7AB9DF", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#7AB9DF", "#ffffff", "#ffffff", "#ffffff")
    val rowFour = Seq("#ffffff", "#ffffff", "#7AB9DF", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#7AB9DF", "#ffffff", "#ffffff")
    val rowFive = Seq("#ffffff", "#ffffff", "#86BEE3", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#000000", "#000000", "#000000", "#000000", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#86BEE3", "#ffffff", "#ffffff")
    val rowSix = Seq("#ffffff", "#86BEE3", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#000000", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#86BEE3", "#ffffff")
    val rowSeven = Seq("#ffffff", "#7AB9DF", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#000000", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#7AB9DF", "#ffffff")
    mapGenerator("Scala class",
      Seq(blankRow, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, rowSeven, rowSeven,
      rowSix, rowFive, rowFour, rowThree, rowTwo, rowOne, blankRow).flatten
    )
  }

  def oneUpMushroom: PixelGrid = {
    mapGenerator("1-up mushroom", Seq(
      Seq("#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#ffffff", "#000000", "#000000", "#45e054", "#45e054", "#45e054", "#45e054", "#ffffff", "#ffffff", "#000000", "#000000", "#ffffff", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#45e054", "#45e054", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#000000", "#ffffff", "#ffffff", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff"),
      Seq("#ffffff", "#000000", "#ffffff", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff"),
      Seq("#000000", "#45e054", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#000000"),
      Seq("#000000", "#45e054", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#45e054", "#45e054", "#ffffff", "#ffffff", "#45e054", "#000000"),
      Seq("#000000", "#ffffff", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000"),
      Seq("#000000", "#ffffff", "#ffffff", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000"),
      Seq("#000000", "#ffffff", "#ffffff", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#ffffff", "#ffffff", "#45e054", "#000000"),
      Seq("#000000", "#ffffff", "#45e054", "#45e054", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#45e054", "#45e054", "#45e054", "#000000"),
      Seq("#ffffff", "#000000", "#000000", "#000000", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#000000", "#000000", "#000000", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#ffffff", "#ffffff", "#ffffff", "#ffffff")
    ).flatten)
  }

  def corruption: PixelGrid = {
    mapGenerator("Corruption", Seq(
      Seq("#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#ffffff", "#000000", "#000000", "#45e054", "#45e054", "#45e054", "#45e054", "#ffffff", "#ffffff", "#000000", "#000000", "#ffffff", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#45e054", "#45e054", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff"),
      Seq("#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200"),
      Seq("#ffffff", "#ffffff", "#7AB9DF", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#7AB9DF", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#86BEE3", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#000000", "#000000", "#000000", "#000000", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#86BEE3", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#86BEE3", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#000000", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#86BEE3", "#ffffff"),
      Seq("#000000", "#ffffff", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000"),
      Seq("#000000", "#ffffff", "#ffffff", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#45e054", "#45e054", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000"),
      Seq("#000000", "#ffffff", "#ffffff", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#45e054", "#ffffff", "#ffffff", "#45e054", "#000000"),
      Seq("#ffffff", "#ffffff", "#86BEE3", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#000000", "#000000", "#000000", "#000000", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#86BEE3", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#7AB9DF", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#A3D6F2", "#7AB9DF", "#ffffff", "#ffffff"),
      Seq("#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200", "#fcfbef", "#141200"),
      Seq("#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#86BEE3", "#7AB9DF", "#7AB9DF", "#86BEE3", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000", "#ffffff", "#ffffff", "#ffffff"),
      Seq("#ffffff", "#ffffff", "#ffffff", "#ffffff", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#000000", "#ffffff", "#ffffff", "#ffffff", "#ffffff")
    ).flatten)
  }
}
