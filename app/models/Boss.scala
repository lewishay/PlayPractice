package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{Format, Json}

case class Boss(name: String, description: String, health: String, level: String, zone: Zone)

object Boss {
  implicit val format: Format[Boss] = Json.format[Boss]
}

case class BossForm(id: Int)

object BossForm {
  val bossForm = Form(
    mapping(
      "Enter boss ID:" -> number
    )(BossForm.apply)(BossForm.unapply)
  )
}
