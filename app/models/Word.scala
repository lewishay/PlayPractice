package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

import scala.util.matching.Regex

case class Word(word: String)

object Word {
  val wordForm = Form(
    mapping(
      "Enter word:" -> text.verifying("Word must not contain special characters.", word => word.matches("^[a-zA-Z0-9]*$"))
    )(Word.apply)(Word.unapply)
  )
}
