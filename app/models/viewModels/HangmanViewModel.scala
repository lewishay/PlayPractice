package models.viewModels

import games.hangman.HangmanGameState
import play.api.mvc.QueryStringBindable

import scala.util.{Failure, Success, Try}

case class HangmanViewModel(gameState: HangmanGameState, gameWin: Option[Boolean])

object HangmanViewModel {

  def parseHangman(input: String): HangmanViewModel = {
    val chunks = input.split("-")
    val gameState = HangmanGameState(
      chunks(0),
      chunks(1).toInt,
      chunks(2).toInt,
      chunks(3).toVector,
      chunks(4).toVector,
      chunks(5).toVector,
      chunks(6).split("sep").toVector
    )
    val gameWin = Some(chunks(7).toBoolean)
    HangmanViewModel(gameState, gameWin)
  }

  def serializeHangman(model: HangmanViewModel): String = {
    Vector(
      model.gameState.guessWord,
      model.gameState.turnNumber,
      model.gameState.remainingGuesses,
      model.gameState.previousGuesses.mkString,
      model.gameState.currentWordStatus.mkString,
      model.gameState.remainingChars.mkString,
      model.gameState.hangingOutput.mkString("sep"),
      model.gameWin.getOrElse(false)
    ).mkString("-")
  }

  implicit def queryStringBindable: QueryStringBindable[HangmanViewModel] = new QueryStringBindable[HangmanViewModel] {

    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String,models.viewModels.HangmanViewModel]] = {
      val result = Try(parseHangman(params.head._1))
      result match {
        case Success(model) => Some(Right(model))
        case Failure(_) => Some(Left("Unable to bind HangmanViewModel"))
      }
    }

    override def unbind(key: String, model: HangmanViewModel): String = {
      serializeHangman(model)
    }
  }
}