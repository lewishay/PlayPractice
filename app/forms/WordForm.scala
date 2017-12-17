package forms

import play.api.data.Form
import play.api.data.Forms._

case class WordForm(word: String)

object WordForm {
  val wordForm = Form(
    mapping(
      "word" -> text.verifying("Word must not contain special characters.", word => word.matches("^[a-zA-Z0-9 ]*$"))
    )(WordForm.apply)(WordForm.unapply)
  )
}
