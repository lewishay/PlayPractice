package models.viewModels

import games.hangman.HangmanGameState
import play.api.mvc.QueryStringBindable

import scala.util.{Failure, Success, Try}

case class HangmanViewModel(gameState: HangmanGameState, gameWin: Option[Boolean])

object HangmanViewModel {

  def parseHangman(input: String): HangmanViewModel = {
    val chunks = input.split("-")
    val gameState = HangmanGameState(
      decode(chunks(0).split(";")),
      chunks(1).toInt,
      chunks(2).toInt,
      chunks(3).toVector,
      decode(chunks(4).split(";")).toVector,
      chunks(5).split(";").toVector
    )
    val gameWin = chunks(6) match {
      case "true" => Some(true)
      case "false" => Some(false)
      case "none" => None
    }
    HangmanViewModel(gameState, gameWin)
  }

  def serializeHangman(model: HangmanViewModel): String = {
    Vector(
      encode(model.gameState.guessWord),
      model.gameState.turnNumber,
      model.gameState.remainingGuesses,
      model.gameState.previousGuesses.mkString,
      encode(model.gameState.currentWordStatus.mkString),
      model.gameState.hangingOutput.mkString(";"),
      model.gameWin.fold("none")(_.toString)
    ).mkString("-")
  }

  def encode(input: String): String = input.map(_.toInt + ";").mkString
  def decode(input: Array[String]): String = {
    val result = input.map(_.toInt.toChar)
    result.mkString
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
