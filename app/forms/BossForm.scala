package forms

import play.api.data.Form
import play.api.data.Forms._

case class BossForm(id: Int)

object BossForm {
  val bossForm: Form[BossForm] = Form(
    mapping(
      "bossID:" -> number
    )(BossForm.apply)(BossForm.unapply)
  )
}
