package controllers

import org.scalatest.{Matchers, OptionValues, WordSpecLike}
import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

trait ControllerBaseSpec extends WordSpecLike with Matchers with OptionValues {

  implicit val defaultTimeout: Duration = 5 seconds

  def status(of: Result): Int = of.header.status

  def status(of: Future[Result])(implicit timeout: Duration): Int = status(Await.result(of, timeout))
}
