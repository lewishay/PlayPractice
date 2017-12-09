package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{Format, Json}

case class Boss(name: String, description: String, health: Int, level: Int, zone: Zone)

object Boss {
  implicit val format: Format[Boss] = Json.format[Boss]

  val validBosses = Seq(("52409", "Ragnaros"), ("15727", "C'Thun"), ("15990", "Kel'Thuzad"),
    ("76877", "Gruul"), ("22917", "Illidan"), ("33288", "Yogg-Saron"), ("36597", "The Lich King"))

  val blankBoss = Boss("Example", "Example", 0, 0, Zone("Example", "Example"))
}

case class BossForm(id: Int)

object BossForm {
  val bossForm = Form(
    mapping(
      "bossID:" -> number
    )(BossForm.apply)(BossForm.unapply)
  )
}
