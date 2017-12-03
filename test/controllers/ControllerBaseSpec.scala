package controllers

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, OptionValues, WordSpecLike}
import play.api.mvc._
import play.api.test.Helpers

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

trait ControllerBaseSpec extends WordSpecLike with Matchers with OptionValues with MockFactory {

  val cc: ControllerComponents = Helpers.stubControllerComponents()

  implicit val defaultTimeout: Duration = Duration(5, "seconds")

  def status(of: Result): Int = of.header.status

  def status(of: Future[Result])(implicit timeout: Duration): Int = status(Await.result(of, timeout))
}
