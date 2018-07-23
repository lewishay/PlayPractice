package forms

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.data.Forms.mapping

case class GuessForm(guess: Char,
                     guessWord: String,
                     turnNumber: Int,
                     remainingGuesses: Int,
                     previousGuesses: Vector[Char],
                     currentWordStatus: Vector[Char],
                     remainingChars: Vector[Char],
                     hangingOutput: Vector[String])

object GuessForm {
  val makeGuessForm = Form(
    mapping(
      "guess" -> of[Char],
      "guessWord" -> of[String],
      "turnNumber" -> of[Int],
      "remainingGuesses" -> of[Int],
      "previousGuesses" -> of[Vector[Char]],
      "currentWordStatus" -> of[Vector[Char]],
      "remainingChars" -> of[Vector[Char]],
      "hangingOutput" -> of[Vector[String]]
    )(GuessForm.apply)(GuessForm.unapply)
  )
}
