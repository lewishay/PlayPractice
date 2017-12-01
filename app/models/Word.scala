package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Word(word: String)

object Word {
  val wordForm = Form(
    mapping(
      "Enter word:" -> of[String]
    )(Word.apply)(Word.unapply)
  )
}
