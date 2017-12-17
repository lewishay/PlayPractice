package common

import models.{Boss, Zone}

object Common {

  val exampleZone: Zone = Zone("Example", "Example")
  val exampleBoss: Boss = Boss("Example", "Example", 0, 0, exampleZone)
}
