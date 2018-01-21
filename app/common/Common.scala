package common

import common.Grids._
import models.{Boss, PixelGrid, Zone}

object Common {

  val exampleZone: Zone = Zone("Example", "Example")
  val exampleBoss: Boss = Boss("Example", "Example", 0, 0, exampleZone)
  val gridList: Seq[PixelGrid] = Seq(checkedGrid, scalaClass, oneUpMushroom, corruption)
}
