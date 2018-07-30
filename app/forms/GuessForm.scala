package forms

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.data.Forms.mapping

case class GuessForm(guess: Char)

object GuessForm {
  val makeGuessForm = Form(
    mapping(
      "guess" -> of[Char]
    )(GuessForm.apply)(GuessForm.unapply)
  )
}
