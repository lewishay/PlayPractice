package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{Format, Json}

case class Boss(name: String, description: String, health: Int, level: Int, zone: Zone)

object Boss {
  implicit val format: Format[Boss] = Json.format[Boss]

  val validBosses = Seq(("52409", "Ragnaros"), ("43324", "Cho'gall"), ("5709", "Shade of Eranikus"),
    ("36597", "The Lich King"), ("76877", "Gruul"), ("28923", "Loken"), ("33288", "Yogg-Saron"))

  val blankBoss = Boss("Example", "Example", 0, 0, Zone("Example", "Example"))
}

case class BossForm(id: Int)

object BossForm {
  val bossForm: Form[BossForm] = Form(
    mapping(
      "bossID:" -> number
    )(BossForm.apply)(BossForm.unapply)
  )
}
