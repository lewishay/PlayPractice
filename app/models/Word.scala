package models

import play.api.data.Form
import play.api.data.Forms._

case class Word(word: String)

object Word {
  val wordForm = Form(
    mapping(
      "word" -> text.verifying("Word must not contain special characters.", word => word.matches("^[a-zA-Z0-9 ]*$"))
    )(Word.apply)(Word.unapply)
  )
}
