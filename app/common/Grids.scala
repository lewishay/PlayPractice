package common

import models.PixelGrid

object Grids {

  private val coordinates: IndexedSeq[(Int, Int)] = {
    (0 to 15).flatMap(
      x => (0 to 15).map(
        y => (x, y)
      )
    )
  }

  private def mapGenerator(colours: Seq[String]): PixelGrid = {
    val result = coordinates.zip(colours)
    new PixelGrid(Map(result: _*))
  }

  def checkedGrid: PixelGrid = {
    val row: Seq[String] = Seq.fill(8)("#fcfbef", "#141200").flatMap {
      case (a, b) => Seq(a, b)
    }
    val twoRows: Seq[String] = row ++ row.reverse
    mapGenerator(Seq.fill(256)(twoRows).flatten)
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
    mapGenerator(Seq(blankRow, rowOne, rowTwo, rowThree, rowFour, rowFive, rowSix, rowSeven, rowSeven,
      rowSix, rowFive, rowFour, rowThree, rowTwo, rowOne, blankRow).flatten)
  }
}
