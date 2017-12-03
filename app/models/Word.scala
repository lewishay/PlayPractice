package models

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

import scala.util.matching.Regex

case class Word(word: String)

object Word {
  val wordForm = Form(
    mapping(
      "Enter word:" -> text // .verifying()
    )(Word.apply)(Word.unapply)
  )

  def noSpecialChars(word: String) = {
    val regex: Regex = "^[a-zA-Z0-9]$".r
//    word match {
      //TODO finish regex
//    }
  }
}
