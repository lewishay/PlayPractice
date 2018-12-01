package services

import connectors.BattleNetConnector
import controllers.ControllerBaseSpec
import models.{Boss, ErrorModel, Zone}
import play.api.http.Status

import scala.concurrent.{ExecutionContext, Future}

class BattleNetServiceSpec extends ControllerBaseSpec {

  val mockConnector: BattleNetConnector = mock[BattleNetConnector]
  val service: BattleNetService = new BattleNetService(mockConnector)

  "Calling getBoss()" when {

    "the connector returns a successful response body" should {

      val bossReturnString: String =
        """{
          |"name":"Fuck",
          |"description":"Fucking hell",
          |"health":999,
          |"level":9,
          |"zoneId":9
          |}"""
          .stripMargin.replace("\n", "")

      val zoneReturnString: String =
        """{
          |"name":"Fuck",
          |"location":{"name":"Fuckland"}
          |}"""
          .stripMargin.replace("\n", "")

      val successBoss: Boss = Boss("Fuck", "Fucking hell", 999, 9, Zone("Fuck", "Fuckland"))

      "return a boss" in {
        (mockConnector.getBoss(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.successful(Right(bossReturnString)))

        (mockConnector.getZone(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.successful(Right(zoneReturnString)))

        val result: Either[ErrorModel, Boss] = await(service.getBoss(1234))
        result shouldBe Right(successBoss)
      }
    }

    "the connector returns an unsuccessful response" should {

      val errorModel = ErrorModel(Status.NOT_FOUND, "Request failed!")

      "return an error model" in {
        (mockConnector.getBoss(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.successful(Left(errorModel)))

        val result: Either[ErrorModel, Boss] = await(service.getBoss(1234))
        result shouldBe Left(errorModel)
      }
    }
  }
}
